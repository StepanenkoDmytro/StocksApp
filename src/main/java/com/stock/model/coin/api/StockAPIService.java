package com.stock.model.coin.api;

import com.stock.model.coin.api.producers.entity.alphaVantage.stock.OverviewCompany;
import com.stock.model.coin.api.producers.entity.yahooFinance.Mover;

import java.math.BigDecimal;
import java.util.List;

public interface StockAPIService {
    OverviewCompany findCompanyByTicker(String ticker);
    BigDecimal findPriceStockByTicker(String ticker);
    List<Mover> getMovers();
}
