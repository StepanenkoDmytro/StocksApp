package com.stock.api;

import com.stock.api.entity.alphaVantage.stock.Company;
import com.stock.api.entity.alphaVantage.stock.OverviewCompany;
import com.stock.dto.forCharts.CandlesDto;

import java.math.BigDecimal;
import java.util.List;

public interface AlphaVantageMarket {
    List<CandlesDto> findCandlesById(String coinID);
    List<Company> findAllCompanies();
    BigDecimal findStockPriceByTicker(String ticker);
    OverviewCompany findCompanyByTicker(String ticker);
}
