package com.stock.model.user.portfolio.repository;

import com.stock.model.user.portfolio.entities.PortfolioSpending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioSpendingRepository extends JpaRepository<PortfolioSpending, String> {
}
