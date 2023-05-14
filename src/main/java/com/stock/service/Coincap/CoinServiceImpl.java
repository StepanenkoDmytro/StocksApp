package com.stock.service.Coincap;

import com.stock.api.CoinMarket;
import com.stock.api.entity.Coin;
import com.stock.dto.CoinDto;
import com.stock.repository.account.AccountRepository;
import com.stock.service.CoinService;
import com.stock.service.TransactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoinServiceImpl implements CoinService {
    private final CoinMarket coinMarket;
    private final TransactService transactService;
    private final AccountRepository accountRepository;

    @Autowired
    public CoinServiceImpl(CoinMarket coinMarket, TransactService transactService, AccountRepository accountRepository) {
        this.coinMarket = coinMarket;
        this.transactService = transactService;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<CoinDto> getAllCoins(int page) {
        List<Coin> list = coinMarket.findAll(page);
        return list.stream()
                .map(CoinDto::mapCoinToDto)
                .collect(Collectors.toList());
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
