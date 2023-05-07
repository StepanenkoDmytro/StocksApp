package com.stock.repository.account;

import com.stock.model.account.Transact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactRepository extends JpaRepository<Transact, Long> {
}
