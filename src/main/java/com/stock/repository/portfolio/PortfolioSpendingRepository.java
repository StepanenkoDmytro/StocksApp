package com.stock.repository.portfolio;

import com.stock.model.portfolio.PortfolioSpending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioSpendingRepository extends JpaRepository<PortfolioSpending, String> {
}
