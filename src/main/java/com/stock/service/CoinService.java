package com.stock.service;


import com.stock.dto.CoinDto;
import com.stock.model.account.Account;

import java.math.BigDecimal;
import java.util.List;

public interface CoinService {
    List<CoinDto> getAllCoins(int page);
    CoinDto getByTicker(String ticker);
    BigDecimal getPriceByTicker(String ticker);
}
