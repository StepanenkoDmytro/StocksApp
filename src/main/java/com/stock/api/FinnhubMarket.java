package com.stock.api;

import com.stock.dto.stocks.CompanyDto;

import java.math.BigDecimal;

public interface FinnhubMarket {
    BigDecimal findPriceStockByTicker(String ticker);
    CompanyDto findCompanyByTicker(String ticker);
}
