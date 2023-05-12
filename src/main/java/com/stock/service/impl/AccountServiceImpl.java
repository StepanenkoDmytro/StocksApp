package com.stock.service.impl;

import com.stock.exceptions.AccountFetchException;
import com.stock.exceptions.ImageNotFoundException;
import com.stock.model.account.Account;
import com.stock.model.account.Transact;
import com.stock.model.user.User;
import com.stock.repository.account.AccountRepository;
import com.stock.service.AccountService;
import com.stock.service.TransactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactService transactService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, TransactService transactService) {
        this.accountRepository = accountRepository;
        this.transactService = transactService;
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
