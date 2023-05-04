package com.stock.service.Coincap;

import com.stock.api.CoinMarket;
import com.stock.api.entity.Coin;
import com.stock.service.CoinService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinCapServiceImpl implements CoinService {
    private final CoinMarket coinMarket;

    public CoinCapServiceImpl(CoinMarket coinMarket) {
        this.coinMarket = coinMarket;
    }

    @Override
    public List<Coin> getAllCoins() {
        List<Coin> coins = coinMarket.findAll();
        return coins;
    }

    @Override
    public Coin getByTicker(String ticker) {
        Coin coin = coinMarket.findByTicker(ticker);
        return coin;
    }
}
