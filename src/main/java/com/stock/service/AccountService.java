package com.stock.service;

import com.stock.model.account.Account;
import com.stock.model.user.User;

import java.math.BigDecimal;

public interface AccountService {
    Account getAccountById(Long accountID);
    void createAccount(String accountName, User user);
    void depositToAccountById(User user, Long accountID, BigDecimal deposit);
}
