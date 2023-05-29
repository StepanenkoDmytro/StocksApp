package com.stock.service;

import com.stock.dto.coins.CoinDto;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.model.account.Account;
import com.stock.model.user.User;

import java.math.BigDecimal;

public interface AccountService {
    Account getAccountById(Long accountID);
    void createAccount(String accountName, User user);
    AccountDto depositToAccountById(User user, Long accountID, BigDecimal deposit);
    void deleteAccountById(Long accountID);
    void updateCoinUser(BigDecimal amount, CoinDto coin, Account account);
}
