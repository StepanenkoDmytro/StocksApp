package com.stock.service.Coincap;

import com.stock.api.CoinMarket;
import com.stock.api.RequestHelper;
import com.stock.api.entity.Coin;
import com.stock.dto.CoinsForClient;
import com.stock.dto.CoinDto;
import com.stock.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());

        return new CoinsForClient(data, RequestHelper.MAX_PAGES, RequestHelper.MAX_ELEMENTS, page);
    }

    @Override
    public CoinsForClient getCoinsByFilter(int page, String filter) {
        List<Coin> coinList = coinMarket.findByFilter(filter);
        int totalPages = (int) Math.ceil((double) coinList.size() / RequestHelper.PAGE_LIMIT);

        int startIndex = (page - 1) * RequestHelper.PAGE_LIMIT;
        int endIndex = Math.min(RequestHelper.PAGE_LIMIT, coinList.size() - startIndex);

        List<CoinDto> data = coinList.stream()
                .skip(startIndex)
                .limit(endIndex)
                .map(CoinDto::mapCoinToDto)
                .collect(Collectors.toList());

        return new CoinsForClient(data, totalPages, coinList.size(), page);
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
