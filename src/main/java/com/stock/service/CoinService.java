package com.stock.service;


import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.coins.CoinsForClient;
import com.stock.dto.coins.CoinDto;
import com.stock.dto.coins.FearGreedIndexDto;
import com.stock.dto.forCharts.CandlesDto;
import com.stock.dto.forCharts.PricesData;

import java.math.BigDecimal;
import java.util.List;

public interface CoinService {
    CoinsForClient getAllCoins(int page);
    CoinsForClient getCoinsByFilter(int page, String filter);
    CoinDto getByTicker(String ticker);
    BigDecimal getPriceByTicker(String ticker);
    List<PricesData> getPriceAccountCoinsByList(AccountDto account);
    List<PricesData> getPriceCoinsByList(AccountDto account);
    List<CandlesDto> getCandles(String baseID, String quoteID);
    FearGreedIndexDto getFearGreedIndex();
}
