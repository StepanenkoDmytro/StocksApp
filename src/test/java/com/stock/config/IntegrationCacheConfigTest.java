package com.stock.config;

import com.stock.service.api.producers.FinnhubMarket;
import com.stock.service.api.producers.YHFinanceMarket;
import com.stock.service.api.producers.entity.yahooFinance.Mover;
import com.stock.dto.stocks.CompanyDto;
import com.stock.dto.stocks.OverviewCompanyDto;
import com.stock.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class IntegrationCacheConfigTest {
    @Autowired
    private StockService stockService;
    @Autowired
    private YHFinanceMarket yhFinanceMarket;
    @Autowired
    private FinnhubMarket finnhubMarket;

    @Test
    void testTimeCaching() {
        long startTest = System.currentTimeMillis();

        long startTimeMostActivesFirst = System.currentTimeMillis();
        List<CompanyDto> mostActives = stockService.getMovers("MOST_ACTIVES");
        long endTimeMostActivesFirst = System.currentTimeMillis();
        long executionTimeMostActivesFirst = endTimeMostActivesFirst - startTimeMostActivesFirst;
        System.out.println("Execution time to get MOST_ACTIVES first time: " + executionTimeMostActivesFirst + " ms");

        long startTimePricesFirst = System.currentTimeMillis();
        List<BigDecimal> bigDecimals = mostActives.stream()
                .map(company -> finnhubMarket.findPriceStockByTicker(company.getSymbol()))
                .toList();
        long endTimePricesFirst = System.currentTimeMillis();
        long executionTimePricesFirst = endTimePricesFirst - startTimePricesFirst;
        System.out.println("Execution time to get prices for MOST_ACTIVES first time: " + executionTimePricesFirst + " ms");

        long startTimeCOFirst = System.currentTimeMillis();
        OverviewCompanyDto company = stockService.getOverviewCompanyBySymbol("GOOG");
        long endTimeCOFirst = System.currentTimeMillis();
        long executionTimeCOFirst = endTimeCOFirst - startTimeCOFirst;
        System.out.println("Execution time to get overview company first time: " + executionTimeCOFirst + " ms");


        long startTimePricesSecond = System.currentTimeMillis();
        bigDecimals = mostActives.stream()
                .map(com -> finnhubMarket.findPriceStockByTicker(com.getSymbol()))
                .toList();
        long endTimePricesSecond = System.currentTimeMillis();
        long executionTimePricesSecond = endTimePricesSecond - startTimePricesSecond;
        System.out.println("Execution time to get prices for MOST_ACTIVES second time: " + executionTimePricesSecond + " ms");

        System.out.println("---------------------------------");

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long startTimePricesThird = System.currentTimeMillis();
        bigDecimals = mostActives.stream()
                .map(com -> finnhubMarket.findPriceStockByTicker(com.getSymbol()))
                .toList();
        long endTimePricesThird = System.currentTimeMillis();
        long executionTimePricesThird = endTimePricesThird - startTimePricesThird;
        System.out.println("Execution time to get prices for MOST_ACTIVES third time: " + executionTimePricesThird + " ms");


        long startTimeGetMoversSecond = System.currentTimeMillis();
        List<Mover> movers = yhFinanceMarket.getMovers();
        long endTimeGetMoversSecond = System.currentTimeMillis();
        long executionTimeGetMoversSecond = endTimeGetMoversSecond - startTimeGetMoversSecond;
        System.out.println("Execution time to get movers list company first time: " + executionTimeGetMoversSecond + " ms");


        long startTimeMostActivesSecond = System.currentTimeMillis();
        mostActives = stockService.getMovers("MOST_ACTIVES");
        long endTimeMostActivesSecond = System.currentTimeMillis();
        long executionTimeMostActivesSecond = endTimeMostActivesSecond - startTimeMostActivesSecond;
        System.out.println("Execution time to get MOST_ACTIVES second time: " + executionTimeMostActivesSecond + " ms");

        long startTimeCOSecond = System.currentTimeMillis();
        company = stockService.getOverviewCompanyBySymbol("GOOG");
        long endTimeCOSecond = System.currentTimeMillis();
        long executionTimeCOSecond = endTimeCOSecond - startTimeCOSecond;
        System.out.println("Execution time to get overview company second time: " + executionTimeCOSecond + " ms");

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("---------------------------------");
        long startTimeGetMoversThird = System.currentTimeMillis();
        movers = yhFinanceMarket.getMovers();
        long endTimeGetMoversThird = System.currentTimeMillis();
        long executionTimeGetMoversThird = endTimeGetMoversThird - startTimeGetMoversThird;
        System.out.println("Execution time to get movers list company third time: " + executionTimeGetMoversThird + " ms");


        long startTimeMostActivesThird = System.currentTimeMillis();
        mostActives = stockService.getMovers("MOST_ACTIVES");
        long endTimeMostActivesThird = System.currentTimeMillis();
        long executionTimeMostActivesThird = endTimeMostActivesThird - startTimeMostActivesThird;
        System.out.println("Execution time to get MOST_ACTIVES third time: " + executionTimeMostActivesThird + " ms");

        long startTimeCOThird = System.currentTimeMillis();
        company = stockService.getOverviewCompanyBySymbol("GOOG");
        long endTimeCOThird = System.currentTimeMillis();
        long executionTimeCOThird = endTimeCOThird - startTimeCOThird;
        System.out.println("Execution time to get overview company third time: " + executionTimeCOThird + " ms");

        System.out.println("--------------------------------");
        long endTest = System.currentTimeMillis();
        long executionTimeTest = endTest - startTest;
        System.out.println("Execution test time: " + executionTimeTest + " ms");

    }
}
