package com.stock.api;

import java.math.BigDecimal;

public interface FinnhubMarket {
    BigDecimal findPriceStockByTicker(String ticker);
}
