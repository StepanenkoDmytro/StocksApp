package com.stock.api;

import java.math.BigDecimal;

public interface FreePriceStock {
    BigDecimal findPriceStockByTicker(String ticker);
}
