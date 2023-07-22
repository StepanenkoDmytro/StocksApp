package com.stock.service.api.producers.wrappers;

import com.stock.dto.stocks.CompanyDto;

public interface TwelveDataMarket {
    CompanyDto findCompanyByTicker(String ticker);
}
