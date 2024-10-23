package com.stock.model.user.account.service;

import com.stock.dto.accountDtos.ActualPricesData;
import com.stock.dto.coins.CoinDto;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.forCharts.PieChartData;
import com.stock.model.stock.dto.OverviewCompanyDto;
import com.stock.model.user.account.entities.Account;
import com.stock.model.user.User;

import java.math.BigDecimal;

public interface AccountService {
    Account getAccountById(Long accountID);
    void createAccount(String accountName, String accountType, User user);
    AccountDto depositToAccountById(Long accountID, BigDecimal deposit);
    void deleteAccountById(Long accountID);
    AccountDto processCoinBuy(BigDecimal amount, CoinDto coin, Account account);
    AccountDto processStockBuy(OverviewCompanyDto stock, BigDecimal price, int count, Account account);
    PieChartData getPieChartData(AccountDto account);
    ActualPricesData getActualPricesData(AccountDto account);
}
