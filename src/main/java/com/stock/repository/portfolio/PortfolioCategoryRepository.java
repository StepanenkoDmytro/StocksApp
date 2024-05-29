package com.stock.repository.portfolio;

import com.stock.model.portfolio.PortfolioCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioCategoryRepository extends JpaRepository<PortfolioCategory, String> {
    List<PortfolioCategory> findByParent(String parentId);
}
