package com.stock.service;

import com.stock.model.user.User;

import java.math.BigDecimal;

public interface AccountService {
    void depositToAccount(User user, Long accountID, BigDecimal deposit);
}
