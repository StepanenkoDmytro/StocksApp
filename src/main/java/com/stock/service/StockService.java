package com.stock.service;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.forCharts.PricesData;
import com.stock.dto.stocks.CompanyDto;
import com.stock.dto.stocks.CompanyDtoWithPrice;
import com.stock.dto.stocks.OverviewCompanyDto;
import com.stock.model.account.AccountStock;

import java.math.BigDecimal;
import java.util.List;

public interface StockService {
    BigDecimal getPriceBySymbol(String symbol);
    CompanyDto getCompanyBySymbol(String symbol);
    List<PricesData> getPriceAccountStocksByListWithoutUSD(List<AccountStock> stocks);
    List<PricesData> getPriceAccountStocksByListWithUSD(AccountDto account);
    List<PricesData> getPriceStocksByList(AccountDto account);
    List<CompanyDto> getMovers(String typeMover);
    OverviewCompanyDto getOverviewCompanyBySymbol(String symbol);
}
