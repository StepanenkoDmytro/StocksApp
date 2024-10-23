package com.stock.model.coin.api.producers;

import com.stock.dto.analytic.DataPriceShort;

import java.util.List;

public interface GlobalStocksIndexMarket {
    List<DataPriceShort> getSP500Data();
}
