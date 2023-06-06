package com.stock.service.impl;

import com.stock.api.CoinMarket;
import com.stock.api.impl.RequestManager;
import com.stock.api.entity.Coin;
import com.stock.dto.coins.CoinsForClient;
import com.stock.dto.coins.CoinDto;
import com.stock.dto.forCharts.PieCoinPrice;
import com.stock.dto.accountDtos.AccountCoinDto;
import com.stock.model.account.AccountCoin;
import com.stock.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {
    private final CoinMarket coinMarket;

    @Autowired
    public CoinServiceImpl(CoinMarket coinMarket) {
        this.coinMarket = coinMarket;
    }

    @Override
    public CoinsForClient getAllCoins(int page) {
        List<Coin> coinList = coinMarket.findAll(page);
        List<CoinDto> data = coinList.stream()
                .map(CoinDto::mapCoinToDto)
                .toList();

        return new CoinsForClient(data, RequestManager.MAX_PAGES, RequestManager.MAX_ELEMENTS, page);
    }

    @Override
    public CoinsForClient getCoinsByFilter(int page, String filter) {
        List<Coin> coinList = coinMarket.findByFilter(filter);
        int totalPages = (int) Math.ceil((double) coinList.size() / RequestManager.PAGE_LIMIT);

        int startIndex = (page - 1) * RequestManager.PAGE_LIMIT;
        int endIndex = Math.min(RequestManager.PAGE_LIMIT, coinList.size() - startIndex);

        List<CoinDto> data = coinList.stream()
                .skip(startIndex)
                .limit(endIndex)
                .map(CoinDto::mapCoinToDto)
                .toList();

        return new CoinsForClient(data, totalPages, coinList.size(), page);
    }

    public List<PieCoinPrice> getPriceCoinsByList(List<AccountCoinDto> coins) {
        return coins.stream()
                .map(coin -> {
                    BigDecimal actualPrice = getPriceByTicker(coin.getIdCoin()).multiply(coin.getAmountCOIN());
                    return new PieCoinPrice(coin.getSymbol(), actualPrice);
                })
                .toList();
    }

    @Override
    public CoinDto getByTicker(String ticker) {
        Coin coin = coinMarket.findByTicker(ticker);
        return CoinDto.mapCoinToDto(coin);
    }

    @Override
    public BigDecimal getPriceByTicker(String ticker) {
        Coin coin = coinMarket.findByTicker(ticker);
        double price = Double.parseDouble(coin.getPriceUsd());
        return BigDecimal.valueOf(price);
    }
}
