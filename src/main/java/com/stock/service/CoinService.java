package com.stock.service;

import com.stock.api.entity.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getAllCoins();
    Coin getByTicker(String ticker);
}
