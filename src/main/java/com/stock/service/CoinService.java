package com.stock.service;


import com.stock.dto.CoinDto;
import com.stock.dto.CoinsByFilter;

import java.math.BigDecimal;
import java.util.List;

public interface CoinService {
    List<CoinDto> getAllCoins(int page);
    CoinsByFilter getCoinsByFilter(int page, String filter);
    CoinDto getByTicker(String ticker);
    BigDecimal getPriceByTicker(String ticker);
}
