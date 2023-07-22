package com.stock.service.api.producers;

import java.math.BigDecimal;

public interface FreePriceStock {
    BigDecimal findPriceStockByTicker(String ticker);
}
