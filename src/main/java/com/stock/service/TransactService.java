package com.stock.service;

import com.stock.model.user.account.entities.Account;
import com.stock.model.user.account.entities.AccountCoin;
import com.stock.model.user.account.entities.AccountStock;
import com.stock.model.trasact.Transact;

import java.math.BigDecimal;
import java.util.List;

public interface TransactService {
    List<Transact> getTransactsByUserID(Long id);
    void logCoinSuccess(BigDecimal amount, AccountCoin coin, Account account);
    void logCoinRejected(BigDecimal amount, AccountCoin coin, Account account);
    void logStockSuccess(BigDecimal purchasePrice, AccountStock stock, Account account);
    void logStockRejected(BigDecimal purchasePrice, AccountStock stock, Account account);
    void logDepositSuccess(BigDecimal deposit, Account account);
}
