package com.stock.service.api.producers;

import com.stock.service.api.producers.entity.coinCap.Coin;
import com.stock.dto.coins.CoinDto;
import com.stock.dto.forCharts.CandlesDto;

import java.util.List;

public interface CoinMarket {
    List<Coin> findAll(int page);
    List<Coin> findByFilter(String filter);
    Coin findByTicker(String ticker);
    List<CoinDto> findByTikersList(List<String> coinsList);
    List<CandlesDto> findCandlesDataByBaseAndQuoteCoins(String baseID, String quoteID);
}
