package com.stock.model.user.portfolio.repository;

import com.stock.model.user.portfolio.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
