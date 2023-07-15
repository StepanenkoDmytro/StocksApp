package com.stock.service;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.dto.forCharts.PricesData;
import com.stock.dto.stocks.CompanyDto;
import com.stock.dto.stocks.OverviewCompanyDto;

import java.util.List;

public interface StockService {
    CompanyDto getCompanyBySymbol(String symbol);
    List<PricesData> getPriceAccountStocksByList(AccountDto account);
    List<PricesData> getPriceStocksByList(AccountDto account);
    List<CompanyDto> getMovers(String typeMover);
    OverviewCompanyDto getOverviewCompanyBySymbol(String symbol);
}
