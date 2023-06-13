package com.stock.service;

import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.dto.forCharts.PiePrice;
import com.stock.dto.stocks.CompaniesForClient;
import com.stock.dto.stocks.OverviewCompanyDto;

import java.util.List;

public interface StockService {
    CompaniesForClient getAll(int page);
    OverviewCompanyDto getCompanyBySymbol(String symbol);
    List<PiePrice> getPriceStocksByList(List<AccountStockDto> stocks);
    CompaniesForClient getGainersMovers();
    CompaniesForClient getLosersMovers();
    CompaniesForClient getActivesMovers();
}
