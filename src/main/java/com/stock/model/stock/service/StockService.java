package com.stock.model.stock.service;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.forCharts.PricesData;
import com.stock.model.stock.dto.CompaniesForClient;
import com.stock.model.stock.dto.CompanyDto;
import com.stock.model.stock.dto.OverviewCompanyDto;
import com.stock.model.user.account.entities.AccountStock;

import java.math.BigDecimal;
import java.util.List;

public interface StockService {
    CompaniesForClient getCompaniesList(int page);
    BigDecimal getPriceBySymbol(String symbol);
    CompanyDto getCompanyBySymbol(String symbol);
    List<PricesData> getPriceAccountStocksByListWithoutUSD(List<AccountStock> stocks);
    List<PricesData> getPriceAccountStocksByListWithUSD(AccountDto account);
    List<PricesData> getPriceStocksByList(AccountDto account);
    List<CompanyDto> getMovers(String typeMover);
    OverviewCompanyDto getOverviewCompanyBySymbol(String symbol);
}
