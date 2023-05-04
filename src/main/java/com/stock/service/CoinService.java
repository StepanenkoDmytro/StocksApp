package com.stock.service;

import com.stock.model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getAllCoins();
    Coin getByTicker(String ticker);
}
