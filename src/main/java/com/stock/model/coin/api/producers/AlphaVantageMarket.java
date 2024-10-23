package com.stock.model.coin.api.producers;

import com.stock.model.coin.api.producers.entity.alphaVantage.stock.AVCompany;
import com.stock.model.coin.api.producers.entity.alphaVantage.stock.OverviewCompany;
import com.stock.dto.analytic.DataPriceShort;
import com.stock.dto.forCharts.CandlesDto;

import java.math.BigDecimal;
import java.util.List;

public interface AlphaVantageMarket {
    List<CandlesDto> findCandlesById(String coinID);
    List<DataPriceShort> findWeeklyPricesById(String ticker);
    List<DataPriceShort> findMonthlyPricesById(String ticker);
    List<AVCompany> findAllCompanies();
    BigDecimal findStockPriceByTicker(String ticker);
    OverviewCompany findCompanyByTicker(String ticker);
}
