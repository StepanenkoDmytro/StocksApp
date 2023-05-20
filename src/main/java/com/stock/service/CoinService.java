package com.stock.service;


import com.stock.dto.CoinsForClient;
import com.stock.dto.CoinDto;

import java.math.BigDecimal;

public interface CoinService {
    CoinsForClient getAllCoins(int page);
    CoinsForClient getCoinsByFilter(int page, String filter);
    CoinDto getByTicker(String ticker);
    BigDecimal getPriceByTicker(String ticker);
}
