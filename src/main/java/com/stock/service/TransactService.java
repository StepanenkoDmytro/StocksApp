package com.stock.service;

import com.stock.dto.CoinDto;
import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;

import java.math.BigDecimal;

public interface TransactService {
    void logSuccess(BigDecimal amount, AccountCoin coin, Account account);
    void logRejected(BigDecimal amount, AccountCoin coin, Account account);
}
