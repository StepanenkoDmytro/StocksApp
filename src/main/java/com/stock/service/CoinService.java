package com.stock.service;


import com.stock.dto.CoinDto;

import java.math.BigDecimal;
import java.util.List;

public interface CoinService {
    List<CoinDto> getAllCoins(int page, String filter);
    CoinDto getByTicker(String ticker);
    BigDecimal getPriceByTicker(String ticker);
}
