package com.stock.model.user.account.repository;

import com.stock.model.trasact.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
