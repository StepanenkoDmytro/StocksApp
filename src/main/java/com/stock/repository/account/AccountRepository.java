package com.stock.repository.account;

import com.stock.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> getUserAccountsById(Long id);

    @Query(value = "SELECT sum(balance) FROM accounts WHERE user_id = :user_id", nativeQuery = true)
    BigDecimal getTotalBalance(@Param("user_id")Long user_id);

    @Query(value = "SELECT balance FROM accounts WHERE user_id = :user_id AND id = :account_id", nativeQuery = true)
    BigDecimal getAccountBalance(@Param("user_id") Long user_id, @Param("account_id") Long account_id);

    @Modifying
    @Query(value ="UPDATE accounts SET balance = :new_balance WHERE id = :account_id" , nativeQuery = true)
    @Transactional
    void changeAccountBalanceById(@Param("new_balance") BigDecimal new_balance, @Param("account_id") Long account_id);
}
