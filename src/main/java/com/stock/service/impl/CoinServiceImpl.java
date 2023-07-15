package com.stock.service.impl;

import com.stock.api.CoinMarket;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.helper.RequestManager;
import com.stock.api.entity.coinCap.Coin;
import com.stock.dto.coins.CoinsForClient;
import com.stock.dto.coins.CoinDto;
import com.stock.dto.forCharts.CandlesDto;
import com.stock.dto.forCharts.PricesData;
import com.stock.dto.accountDtos.AccountCoinDto;
import com.stock.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Override
    public List<PricesData> getPriceAccountCoinsByList(AccountDto account) {
        if (account == null || account.getStocks() == null) {
            return new ArrayList<>();
        }

        List<AccountCoinDto> coins = account.getCoins();
        List<PricesData> prices = coins.stream()
                .map(coin -> {
                    BigDecimal actualPrice = getPriceByTicker(coin.getIdCoin()).multiply(coin.getCountCoin());
                    return new PricesData(coin.getSymbol(), actualPrice);
                })
                .collect(Collectors.toList());

        PricesData freeUSD = new PricesData("USD", account.getBalance());
        prices.add(freeUSD);
        return prices;
    }
    public List<PricesData> getPriceCoinsByList(AccountDto account) {
        if (account == null || account.getStocks() == null) {
            return new ArrayList<>();
        }

        List<AccountCoinDto> coins = account.getCoins();
        return coins.stream()
                .map(coin -> {
                    BigDecimal actualPrice = getPriceByTicker(coin.getIdCoin());
                    return new PricesData(coin.getSymbol(), actualPrice);
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

    public List<CandlesDto> getCandles(String baseID, String quoteID) {
        return coinMarket.findCandlesDataByBaseAndQuoteCoins(baseID, quoteID);
    }
}
