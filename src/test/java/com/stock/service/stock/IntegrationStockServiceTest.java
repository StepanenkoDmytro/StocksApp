package com.stock.service.stock;

import com.stock.dto.stocks.CompanyDto;
import com.stock.dto.stocks.OverviewCompanyDto;
import com.stock.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class IntegrationStockServiceTest {
    @Autowired
    private StockService stockService;

    @Test
    void testGetMoversCaching() {
        long startTimeMostActivesFirst = System.currentTimeMillis();
        List<CompanyDto> mostActives = stockService.getMovers("MOST_ACTIVES");
        long endTimeMostActivesFirst = System.currentTimeMillis();
        long executionTimeMostActivesFirst = endTimeMostActivesFirst - startTimeMostActivesFirst;
        System.out.println("Execution time to get MOST_ACTIVES first time: " + executionTimeMostActivesFirst + " ms");

        long startTimeMostActivesSecond = System.currentTimeMillis();
        mostActives = stockService.getMovers("MOST_ACTIVES");
        long endTimeMostActivesSecond = System.currentTimeMillis();
        long executionTimeMostActivesSecond = endTimeMostActivesSecond - startTimeMostActivesSecond;
        System.out.println("Execution time to get MOST_ACTIVES second time: " + executionTimeMostActivesSecond + " ms");

        System.out.println("-------------------------------------");

        long startTimeGainersFirst = System.currentTimeMillis();
        List<CompanyDto> gainers = stockService.getMovers("DAY_GAINERS");
        long endTimeGainersFirst = System.currentTimeMillis();
        long executionTimeGainersFirst = endTimeGainersFirst - startTimeGainersFirst;
        System.out.println("Execution time to get DAY_GAINERS first time: " + executionTimeGainersFirst + " ms");

        long startTimeGainersSecond = System.currentTimeMillis();
        gainers = stockService.getMovers("DAY_GAINERS");
        long endTimeGainersSecond = System.currentTimeMillis();
        long executionTimeGainersSecond = endTimeGainersSecond - startTimeGainersSecond;
        System.out.println("Execution time to get DAY_GAINERS second time: " + executionTimeGainersSecond + " ms");

        System.out.println("-------------------------------------");

        long startTimeLosersFirst = System.currentTimeMillis();
        List<CompanyDto> losers = stockService.getMovers("DAY_LOSERS");
        long endTimeLosersFirst = System.currentTimeMillis();
        long executionTimeLosersFirst = endTimeLosersFirst - startTimeLosersFirst;
        System.out.println("Execution time to get DAY_LOSERS first time: " + executionTimeLosersFirst + " ms");


        long startTimeLosersSecond = System.currentTimeMillis();
        losers = stockService.getMovers("DAY_LOSERS");
        long endTimeLosersSecond = System.currentTimeMillis();
        long executionTimeLosersSecond = endTimeLosersSecond - startTimeLosersSecond;
        System.out.println("Execution time to get DAY_LOSERS first time: " + executionTimeLosersSecond + " ms");
    }

    @Test
    void testGetOverviewCompanyByCache() {
        long startTimeFirst = System.currentTimeMillis();
        OverviewCompanyDto company = stockService.getOverviewCompanyBySymbol("GOOG");
        long endTimeFirst = System.currentTimeMillis();
        long executionTimeFirst = endTimeFirst - startTimeFirst;
        System.out.println("Execution time to get overview company first time: " + executionTimeFirst + " ms");

        long startTimeSecond = System.currentTimeMillis();
        company = stockService.getOverviewCompanyBySymbol("GOOG");
        long endTimeSecond = System.currentTimeMillis();
        long executionTimeSecond = endTimeSecond - startTimeSecond;
        System.out.println("Execution time to get overview company second time: " + executionTimeSecond + " ms");
    }
}
