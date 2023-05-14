package com.stock.repository.account;

import com.stock.model.trasact.Transact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

public interface TransactRepository extends JpaRepository<Transact, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO transacts(account_id, transaction_type, amount, source, status_transact, reason_code, created)" +
            "VALUES(:account_id, :transact_type, :amount, :source, :status, :reason_code, :created_at)", nativeQuery = true)
    void logTransaction(@Param("account_id")Long account_id,
                        @Param("transact_type")String transact_type,
                        @Param("amount") BigDecimal amount,
                        @Param("source")String source,
                        @Param("status")String status,
                        @Param("reason_code")String reason_code,
                        @Param("created_at") Date created_at);
}
