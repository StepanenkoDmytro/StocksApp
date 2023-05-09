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

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public BigDecimal getPriceByTicker(String ticker) {
        Coin coin = coinMarket.findByTicker(ticker);
        double price = Double.parseDouble(coin.getPriceUsd());
        return BigDecimal.valueOf(price);
    }

    private boolean isContainsCoin(List<AccountCoin> coinsUser, AccountCoin coin) {
        for (AccountCoin ac : coinsUser) {
            if (ac.getId_coin().equals(coin.getId_coin())) {
                return true;
            }
        }
        return false;
    }

    private AccountCoin getCoinFromUser(List<AccountCoin> coinsUser, AccountCoin coin) {
        for (AccountCoin ac : coinsUser) {
            if (ac.getId_coin().equals(coin.getId_coin())) {
                return ac;
            }
        }
        return coin;
    }

    @Override
    public void updateCoinUser(BigDecimal amount, CoinDto coin, Account account) {
        BigDecimal newBalance = account.getBalance().subtract(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {


            AccountCoin accountCoin = AccountCoin.fromCoin(coin, amount);



            List<AccountCoin> coinsUser = account.getCoins();
            if (isContainsCoin(coinsUser, accountCoin)) {
                AccountCoin c = getCoinFromUser(coinsUser, accountCoin);
                BigDecimal newAmountCoin = c.getAmountCOIN().add(accountCoin.getAmountCOIN());
                BigDecimal newAmountUSD = c.getAmountUSD().add(accountCoin.getAmountUSD());
                c.setAmountCOIN(newAmountCoin);

                c.setAmountUSD(newAmountUSD);

                accountCoinRepository.save(c);
            } else {
                account.addCoins(accountCoin);
                accountCoinRepository.save(accountCoin);
            }

            accountRepository.changeAccountBalanceById(newBalance, account.getId());
            transactService.logSuccess(amount, coin, account);
        } else {
            transactService.logRejected(amount, coin, account);
        }
    }
}
