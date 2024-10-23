package com.stock.service.impl;

import com.stock.model.user.account.entities.Account;
import com.stock.model.user.account.entities.AccountCoin;
import com.stock.model.user.account.entities.AccountStock;
import com.stock.model.trasact.Transact;
import com.stock.model.user.account.repository.TransactRepository;
import com.stock.service.TransactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactServiceImpl implements TransactService {
    private final TransactRepository transactRepository;

    @Autowired
    public TransactServiceImpl(TransactRepository transactRepository) {
        this.transactRepository = transactRepository;
    }

    @Override
    public List<Transact> getTransactsByUserID(Long id) {
        return transactRepository.findByUserId(id);
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

    @Override
    public void logStockSuccess(BigDecimal purchasePrice, AccountStock stock, Account account) {
        Transact log = Transact.transactSTOCK("rejected", purchasePrice, stock);
        account.addTransact(log);

        transactRepository.save(log);
    }

    @Override
    public void logStockRejected(BigDecimal purchasePrice, AccountStock stock, Account account) {
        Transact log = Transact.transactSTOCK("rejected", purchasePrice, stock);
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
