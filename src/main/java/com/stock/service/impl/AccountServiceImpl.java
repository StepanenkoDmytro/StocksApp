package com.stock.service.impl;

import com.stock.dto.coins.CoinDto;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.stocks.OverviewCompanyDto;
import com.stock.exceptions.AccountFetchException;
import com.stock.helper.AccountHelper;
import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.model.account.AccountStock;
import com.stock.model.account.AccountType;
import com.stock.model.user.User;
import com.stock.repository.account.AccountRepository;
import com.stock.service.AccountService;
import com.stock.service.TransactService;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactService transactService;
    private final UserService userService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, TransactService transactService, UserService userService) {
        this.accountRepository = accountRepository;
        this.transactService = transactService;
        this.userService = userService;
    }

    @Override
    public Account getAccountById(Long accountID) {
        return accountRepository.findById(accountID).orElseThrow(() ->
                new AccountFetchException(String.format("Account with id = %d not found", accountID)));
    }

    @Override
    @Transactional
    public AccountDto processCoinBuy(BigDecimal amount, CoinDto coin, Account account) {
        if (account == null || coin == null) {
            throw new AccountFetchException("In processCoinBuy account or coin is null");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        AccountCoin accountCoin = AccountCoin.fromCoin(coin, amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            transactService.logCoinRejected(amount, accountCoin, account);
            return AccountDto.mapAccount(account);
        }

        if (isContainsCoin(account, accountCoin)) {
            updateExistingCoin(account, accountCoin);
        } else {
            addNewCoin(account, accountCoin);
        }

        account.setBalance(newBalance);

        accountRepository.save(account);
        transactService.logCoinSuccess(amount, accountCoin, account);

        return AccountDto.mapAccount(account);
    }

    @Override
    @Transactional
    public AccountDto processStockBuy(OverviewCompanyDto stock, int count, Account account) {
        if (account == null || stock == null) {
            throw new AccountFetchException("In processStockBuy account or stock is null");
        }
        BigDecimal purchasePrice = stock.getPrice().multiply(BigDecimal.valueOf(count));

        BigDecimal newBalance = account.getBalance().subtract(purchasePrice);
        AccountStock accountStock = AccountStock.fromCompany(stock, count);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            transactService.logStockRejected(purchasePrice, accountStock, account);
            return AccountDto.mapAccount(account);
        }

        if (isContainsStock(account, accountStock)) {
            updateExistingStock(account, accountStock);
        } else {
            addNewStock(account, accountStock);
        }
        account.setBalance(newBalance);
        accountRepository.save(account);
        transactService.logStockSuccess(purchasePrice, accountStock, account);

        return AccountDto.mapAccount(account);
    }

    @Override
    public void createAccount(String accountName, String accountType, User user) {
        //зробити валідацію
        AccountType type = AccountType.valueOf(accountType);

        Account account = new Account();
        account.setAccountName(accountName);
        account.setAccountType(type);

        account.setAccountNumber(AccountHelper.generateAccountNumber());

        user.addAccount(account);
        userService.saveUser(user);
    }

    @Override
    @Transactional
    public AccountDto depositToAccountById(Long accountID, BigDecimal deposit) {
        Account account = getAccountById(accountID);
        //в майбутньому додати перевірку на статус акаунту
        BigDecimal balance = account.getBalance();
        BigDecimal newBalance = balance.add(deposit);

        account.setBalance(newBalance);

        accountRepository.save(account);
        transactService.logDepositSuccess(deposit, account);
        return AccountDto.mapAccount(account);
    }

    @Override
    public void deleteAccountById(Long accountID) {
        accountRepository.deleteById(accountID);
    }

    private void updateExistingCoin(Account account, AccountCoin accountCoin) {
        List<AccountCoin> coinsUser = account.getCoins();
        AccountCoin existingCoin = getCoinFromUser(coinsUser, accountCoin);

        BigDecimal newAmountCoin = existingCoin.getAmountCOIN().add(accountCoin.getAmountCOIN());
        BigDecimal newAmountUSD = existingCoin.getAmountUSD().add(accountCoin.getAmountUSD());

        existingCoin.setAmountCOIN(newAmountCoin);
        existingCoin.setAmountUSD(newAmountUSD);
    }

    private void addNewCoin(Account account, AccountCoin accountCoin) {
        account.addCoins(accountCoin);
    }

    private boolean isContainsCoin(Account account, AccountCoin coin) {
        List<AccountCoin> coinsUser = account.getCoins();
        return coinsUser.stream()
                .anyMatch(c -> c.getIdCoin().equals(coin.getIdCoin()));
    }

    private boolean isContainsStock(Account account, AccountStock stock) {
        List<AccountStock> stocksUser = account.getStocks();
        return stocksUser.stream()
                .anyMatch(c -> c.getSymbol().equals(stock.getSymbol()));
    }

    private AccountCoin getCoinFromUser(List<AccountCoin> coinsUser, AccountCoin coin) {
        return coinsUser.stream()
                .filter(ac -> ac.getIdCoin().equals(coin.getIdCoin()))
                .findFirst()
                .orElse(coin);
    }

    private void updateExistingStock(Account account, AccountStock newStock) {
        List<AccountStock> stocksUser = account.getStocks();
        AccountStock existingStock = getStockFromUser(stocksUser, newStock);

        int newCountStocks = existingStock.getCountStocks() + newStock.getCountStocks();
        BigDecimal newPriceStocks = existingStock.getBuyPrice()
                .add(newStock.getBuyPrice())
                .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);

        existingStock.setCountStocks(newCountStocks);
        existingStock.setBuyPrice(newPriceStocks);
    }

    private AccountStock getStockFromUser(List<AccountStock> stocksUser, AccountStock stock) {
        return stocksUser.stream()
                .filter(as -> as.getSymbol().equals(stock.getSymbol()))
                .findFirst()
                .orElse(stock);
    }

    private void addNewStock(Account account, AccountStock accountStock) {
        account.addStocks(accountStock);
    }
}
