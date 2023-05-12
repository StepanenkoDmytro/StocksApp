package com.stock.service;

import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;

import java.math.BigDecimal;

public interface TransactService {
    void logCoinSuccess(BigDecimal amount, AccountCoin coin, Account account);
    void logCoinRejected(BigDecimal amount, AccountCoin coin, Account account);
    void logDepositSuccess(BigDecimal deposit, Account account);
}
