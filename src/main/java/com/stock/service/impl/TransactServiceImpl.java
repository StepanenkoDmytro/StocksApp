package com.stock.service.impl;

import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.model.trasact.Transact;
import com.stock.repository.account.TransactRepository;
import com.stock.service.TransactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactServiceImpl implements TransactService {
    private final TransactRepository transactRepository;

    @Autowired
    public TransactServiceImpl(TransactRepository transactRepository) {
        this.transactRepository = transactRepository;
    }

    @Transactional
    public void logCoinSuccess(BigDecimal amount, AccountCoin coin, Account account) {
        Transact log = Transact.transactCOIN("success", amount, coin);
        account.addTransact(log);

        transactRepository.save(log);
    }

    @Transactional
    public void logCoinRejected(BigDecimal amount, AccountCoin coin, Account account) {
        Transact log = Transact.transactCOIN("rejected", amount, coin);
        account.addTransact(log);

        transactRepository.save(log);
    }

    @Transactional
    public void logDepositSuccess(BigDecimal deposit, Account account) {
        Transact log = Transact.transactDeposit(deposit);
        account.addTransact(log);

        transactRepository.save(log);
    }
}
