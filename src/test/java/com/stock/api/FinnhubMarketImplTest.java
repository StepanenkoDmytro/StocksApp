package com.stock.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class FinnhubMarketImplTest {
    private final FinnhubMarket finnhubMarket;

    @Autowired
    public FinnhubMarketImplTest(FinnhubMarket finnhubMarket) {
        this.finnhubMarket = finnhubMarket;
    }

    @Test
    void testRequst() {
        BigDecimal ibm = finnhubMarket.findPriceStockByTicker("IBM");
        System.out.println(ibm);
    }
}
