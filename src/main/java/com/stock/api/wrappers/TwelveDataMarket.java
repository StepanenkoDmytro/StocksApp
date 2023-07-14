package com.stock.api.wrappers;

import com.stock.dto.stocks.CompanyDto;

public interface TwelveDataMarket {
    CompanyDto findCompanyByTicker(String ticker);
}
