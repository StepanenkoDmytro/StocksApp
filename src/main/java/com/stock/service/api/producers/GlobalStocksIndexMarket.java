package com.stock.service.api.producers;

import com.stock.dto.analytic.DataPriceShort;

import java.util.List;

public interface GlobalStocksIndexMarket {
    List<DataPriceShort> getSP500Data();
}
