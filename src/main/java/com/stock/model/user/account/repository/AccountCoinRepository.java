package com.stock.model.user.account.repository;

import com.stock.model.user.account.entities.AccountCoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountCoinRepository extends JpaRepository<AccountCoin, Long> {
}
