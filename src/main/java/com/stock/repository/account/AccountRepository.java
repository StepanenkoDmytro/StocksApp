package com.stock.repository.account;

import com.stock.model.account.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> getUserAccountsById(Long id);

    @Query(value = "SELECT sum(balance) FROM accounts WHERE user_id = :userID", nativeQuery = true)
    BigDecimal getTotalBalance(@Param("userID")Long userID);

    @Query(value = "SELECT balance FROM accounts WHERE user_id = :userID AND id = :accountID", nativeQuery = true)
    BigDecimal getAccountBalance(@Param("userID") Long userID, @Param("accountID") Long accountID);

    @Modifying
    @Query(value ="UPDATE accounts SET balance = :newBalance WHERE id = :accountID" , nativeQuery = true)
    @Transactional
    void changeAccountBalanceById(@Param("newBalance") BigDecimal newBalance, @Param("accountID") Long accountID);

    @EntityGraph(attributePaths = "coins")
    Account save(Account account);
}
