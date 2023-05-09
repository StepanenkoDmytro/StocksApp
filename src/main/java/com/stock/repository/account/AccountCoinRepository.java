package com.stock.repository.account;

import com.stock.model.account.AccountCoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountCoinRepository extends JpaRepository<AccountCoin, Long> {
}
