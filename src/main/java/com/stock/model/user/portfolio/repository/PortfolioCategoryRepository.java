package com.stock.model.user.portfolio.repository;

import com.stock.model.user.portfolio.entities.PortfolioCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioCategoryRepository extends JpaRepository<PortfolioCategory, String> {
    List<PortfolioCategory> findByParent(String parentId);
}
