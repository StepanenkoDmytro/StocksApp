package com.stock.service;

import com.stock.dto.CoinDto;
import com.stock.model.account.Account;

import java.math.BigDecimal;

public interface TransactService {
    void logSuccess(BigDecimal amount, CoinDto coin, Account account);
    void logRejected(BigDecimal amount, CoinDto coin, Account account);
}
