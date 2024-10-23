package com.stock.model.coin.api.producers.wrappers;

import com.stock.model.stock.dto.CompanyDto;

public interface TwelveDataMarket {
    CompanyDto findCompanyByTicker(String ticker);
}
