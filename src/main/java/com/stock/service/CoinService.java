package com.stock.service;


import com.stock.dto.CoinsForClient;
import com.stock.dto.CoinDto;
import com.stock.dto.PieCoinsPrice;
import com.stock.dto.accountDtos.AccountCoinDto;

import java.math.BigDecimal;
import java.util.List;

public interface CoinService {
    CoinsForClient getAllCoins(int page);
    CoinsForClient getCoinsByFilter(int page, String filter);
    CoinDto getByTicker(String ticker);
    BigDecimal getPriceByTicker(String ticker);
    List<PieCoinsPrice> getPriceCoinsByList(List<AccountCoinDto> coins);
}
