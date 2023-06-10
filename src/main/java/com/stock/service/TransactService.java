package com.stock.service;

import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.model.account.AccountStock;
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
