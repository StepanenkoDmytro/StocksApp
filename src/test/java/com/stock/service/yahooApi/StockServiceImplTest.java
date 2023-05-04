package com.stock.service.yahooApi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class StockServiceImplTest {

    @Autowired
    private StockService stockService;

//    @Test
//    void invoke() throws IOException {
//        final StockWrapper stock = stockService.findStock("UU.L");
//        System.out.println(stock.getStock());
//
//        final BigDecimal decimal = stockService.findPrice(stock);
//        System.out.println(decimal);
//
//        final BigDecimal change = stockService.findLastChangePercent(stock);
//        System.out.println(change);
//
//        final BigDecimal mean200DayMean = stockService.findChange200MeanPercent(stock);
//        System.out.println(mean200DayMean);
//    }

    @Test
    void multiple() throws IOException, InterruptedException {
        final List<StockWrapper> stocks = stockService.findAllStock(Arrays.asList("GOOG", "AMZN"));
        findPrices(stocks);

        Thread.sleep(16000);

        final StockWrapper stock = stockService.findStock("UU.L");
        stocks.add(stock);

        System.out.println(stockService.findPrice(stock));
        findPrices(stocks);



        System.out.println(stockService.findPrice(stock));
        findPrices(stocks);
    }

    private void findPrices(List<StockWrapper> stocks) {
        stocks.forEach(stock -> {
            try{
                System.out.println(stockService.findPrice(stock) + stock.getStock().getName());
            } catch (IOException e) {
                //Ignore
            }
        });
    }
}
