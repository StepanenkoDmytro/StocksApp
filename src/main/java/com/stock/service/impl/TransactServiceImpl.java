package com.stock.service.impl;

import com.stock.dto.CoinDto;
import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.model.account.Transact;
import com.stock.repository.account.TransactRepository;
import com.stock.service.TransactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactServiceImpl implements TransactService {
    private final TransactRepository transactRepository;

    @Autowired
    public TransactServiceImpl(TransactRepository transactRepository) {
        this.transactRepository = transactRepository;
    }

    public void logSuccess(BigDecimal amount, AccountCoin coin, Account account){
        Transact log = Transact.transactCOIN("success", amount, coin.getId_coin());
        account.addTransact(log);


        transactRepository.save(log);
    }

    public void logRejected(BigDecimal amount, AccountCoin coin, Account account){
        Transact log = Transact.transactCOIN("rejected", amount, coin.getId_coin());
        account.addTransact(log);

        transactRepository.save(log);
    }
}
