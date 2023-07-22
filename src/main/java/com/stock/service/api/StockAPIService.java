package com.stock.service.api;

import com.stock.service.api.producers.entity.alphaVantage.stock.OverviewCompany;
import com.stock.service.api.producers.entity.yahooFinance.Mover;

import java.math.BigDecimal;
import java.util.List;

public interface StockAPIService {
    OverviewCompany findCompanyByTicker(String ticker);
    BigDecimal findPriceStockByTicker(String ticker);
    List<Mover> getMovers();
}
