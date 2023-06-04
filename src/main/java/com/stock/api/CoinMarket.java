package com.stock.api;

import com.stock.api.entity.Coin;
import com.stock.dto.coins.CoinDto;

import java.util.List;

public interface CoinMarket {
    List<Coin> findAll(int page);
    List<Coin> findByFilter(String filter);
    Coin findByTicker(String ticker);
    List<CoinDto> findByTikersList(List<String> coinsList);
}
