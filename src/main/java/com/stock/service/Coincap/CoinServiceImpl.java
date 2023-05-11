package com.stock.service.Coincap;

import com.stock.api.CoinMarket;
import com.stock.api.entity.Coin;
import com.stock.dto.CoinDto;
import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.repository.account.AccountCoinRepository;
import com.stock.repository.account.AccountRepository;
import com.stock.service.CoinService;
import com.stock.service.TransactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoinServiceImpl implements CoinService {
    private final CoinMarket coinMarket;
    private final AccountCoinRepository accountCoinRepository;
    private final TransactService transactService;
    private final AccountRepository accountRepository;

    @Autowired
    public CoinServiceImpl(CoinMarket coinMarket, AccountCoinRepository accountCoinRepository, TransactService transactService, AccountRepository accountRepository) {
        this.coinMarket = coinMarket;
        this.accountCoinRepository = accountCoinRepository;
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

    @Override
    @Transactional
    public void updateCoinUser(BigDecimal amount, CoinDto coin, Account account) {
        BigDecimal newBalance = account.getBalance().subtract(amount);
        AccountCoin accountCoin = AccountCoin.fromCoin(coin, amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            transactService.logRejected(amount, accountCoin, account);
            return;
        }

        List<AccountCoin> coinsUser = account.getCoins();

        if (isContainsCoin(coinsUser, accountCoin)) {
            updateExistingCoin(coinsUser, accountCoin);
        } else {
            addNewCoin(account, accountCoin);
        }

        accountRepository.changeAccountBalanceById(newBalance, account.getId());
        transactService.logSuccess(amount, accountCoin, account);
    }

    private void updateExistingCoin(List<AccountCoin> coinsUser, AccountCoin accountCoin) {
        AccountCoin existingCoin = getCoinFromUser(coinsUser, accountCoin);

        BigDecimal newAmountCoin = existingCoin.getAmountCOIN().add(accountCoin.getAmountCOIN());
        BigDecimal newAmountUSD = existingCoin.getAmountUSD().add(accountCoin.getAmountUSD());

        existingCoin.setAmountCOIN(newAmountCoin);
        existingCoin.setAmountUSD(newAmountUSD);

        accountCoinRepository.save(existingCoin);
    }

    private void addNewCoin(Account account, AccountCoin accountCoin) {
        account.addCoins(accountCoin);
        accountCoinRepository.save(accountCoin);
    }

    private boolean isContainsCoin(List<AccountCoin> coinsUser, AccountCoin coin) {
        return coinsUser.stream()
                .anyMatch(c -> c.getId_coin().equals(coin.getId_coin()));
    }

    private AccountCoin getCoinFromUser(List<AccountCoin> coinsUser, AccountCoin coin) {
        return coinsUser.stream()
                .filter(ac -> ac.getId_coin().equals(coin.getId_coin()))
                .findFirst()
                .orElse(coin);
    }

}
