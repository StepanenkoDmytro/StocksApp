package com.stock.model.coin.api.producers;

import java.math.BigDecimal;

public interface FreePriceStock {
    BigDecimal findPriceStockByTicker(String ticker);
}
