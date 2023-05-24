package com.stock.service.impl;

import com.stock.dto.CoinDto;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.exceptions.AccountFetchException;
import com.stock.helper.GenAccountNumber;
import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.model.account.AccountType;
import com.stock.model.user.User;
import com.stock.repository.account.AccountCoinRepository;
import com.stock.repository.account.AccountRepository;
import com.stock.service.AccountService;
import com.stock.service.TransactService;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactService transactService;
    private final UserService userService;

    private final AccountCoinRepository accountCoinRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, TransactService transactService, UserService userService, AccountCoinRepository accountCoinRepository) {
        this.accountRepository = accountRepository;
        this.transactService = transactService;
        this.userService = userService;
        this.accountCoinRepository = accountCoinRepository;
    }

    @Override
    public Account getAccountById(Long accountID){
        return accountRepository.findById(accountID).orElseThrow(() ->
                new AccountFetchException(String.format("Account with id = %d not found", accountID)));
    }

    @Override
    @Transactional
    public void updateCoinUser(BigDecimal amount, CoinDto coin, Account account) {
        BigDecimal newBalance = account.getBalance().subtract(amount);
        AccountCoin accountCoin = AccountCoin.fromCoin(coin, amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            transactService.logCoinRejected(amount, accountCoin, account);
            return;
        }

        List<AccountCoin> coinsUser = account.getCoins();

        if (isContainsCoin(coinsUser, accountCoin)) {
            updateExistingCoin(coinsUser, accountCoin);
        } else {
            addNewCoin(account, accountCoin);
        }

        accountRepository.changeAccountBalanceById(newBalance, account.getId());
        transactService.logCoinSuccess(amount, accountCoin, account);
    }

    @Transactional
    private void updateExistingCoin(List<AccountCoin> coinsUser, AccountCoin accountCoin) {
        AccountCoin existingCoin = getCoinFromUser(coinsUser, accountCoin);

        BigDecimal newAmountCoin = existingCoin.getAmountCOIN().add(accountCoin.getAmountCOIN());
        BigDecimal newAmountUSD = existingCoin.getAmountUSD().add(accountCoin.getAmountUSD());

        existingCoin.setAmountCOIN(newAmountCoin);
        existingCoin.setAmountUSD(newAmountUSD);

        accountCoinRepository.save(existingCoin);
    }

    @Transactional
    private void addNewCoin(Account account, AccountCoin accountCoin) {
        account.addCoins(accountCoin);
        accountCoinRepository.save(accountCoin);
    }

    private boolean isContainsCoin(List<AccountCoin> coinsUser, AccountCoin coin) {
        return coinsUser.stream()
                .anyMatch(c -> c.getId_coin().equals(coin.getId_coin()));
    }

    private AccountCoin getCoinFromUser(List<AccountCoin> coinsUser, AccountCoin coin) {
        return coinsUser.stream()
                .filter(ac -> ac.getId_coin().equals(coin.getId_coin()))
                .findFirst()
                .orElse(coin);
    }

    @Override
    public void createAccount(String accountName, User user){
        Account account = new Account();
        account.setAccount_name(accountName);
        account.setAccount_type(AccountType.CryptoWallet);

        account.setAccount_number(GenAccountNumber.generateAccountNumber());

        user.addAccount(account);
        userService.saveUser(user);
    }

    @Override
    @Transactional
    public AccountDto depositToAccountById(User user, Long accountID, BigDecimal deposit) {
        BigDecimal balance = accountRepository.getAccountBalance(user.getId(), accountID);
        BigDecimal newBalance = balance.add( deposit );
        Account account = accountRepository.findById(accountID).orElseThrow(
                () -> new AccountFetchException(String.format("Account with id = %d not found", accountID)));

        accountRepository.changeAccountBalanceById(newBalance, accountID);
        transactService.logDepositSuccess(deposit, account);
        return AccountDto.mapAccount(account);
    }

    @Override
    public void deleteAccountById(Long accountID) {
        accountRepository.deleteById(accountID);
    }
}
