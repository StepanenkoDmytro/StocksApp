package com.stock.model.coin.api.producers;

import com.stock.model.stock.dto.CompanyDto;

import java.math.BigDecimal;

public interface FinnhubMarket {
    BigDecimal findPriceStockByTicker(String ticker);
    CompanyDto findCompanyByTicker(String ticker);
}
