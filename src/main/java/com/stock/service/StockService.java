package com.stock.service;

import com.stock.dto.stocks.CompaniesForClient;
import com.stock.dto.stocks.OverviewCompanyDto;

public interface StockService {
    CompaniesForClient getAll(int page);
    OverviewCompanyDto getCompanyBySymbol(String symbol);
}
