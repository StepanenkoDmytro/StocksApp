package com.stock.service.impl;

import com.stock.exceptions.AccountFetchException;
import com.stock.helper.GenAccountNumber;
import com.stock.model.account.Account;
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
    public Account getAccountById(Long accountID){
        return accountRepository.findById(accountID).orElseThrow(() ->
                new AccountFetchException(String.format("Account with id = %d not found", accountID)));
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
    public void depositToAccountById(User user, Long accountID, BigDecimal deposit) {
        BigDecimal balance = accountRepository.getAccountBalance(user.getId(), accountID);
        BigDecimal newBalance = balance.add( deposit );
        Account account = accountRepository.findById(accountID).orElseThrow(
                () -> new AccountFetchException(String.format("Account with id = %d not found", accountID)));

        accountRepository.changeAccountBalanceById(newBalance, accountID);
        transactService.logDepositSuccess(deposit, account);
    }
}
