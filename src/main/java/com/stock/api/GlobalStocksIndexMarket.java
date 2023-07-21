package com.stock.api;

import com.stock.api.entity.globalIndex.IndexData;

import java.util.List;

public interface GlobalStocksIndexMarket {
    List<IndexData> getSP500Data();
}
