package com.stock.model.user.portfolio.service;

import com.stock.model.user.portfolio.entities.Portfolio;

public interface PortfolioService {
    Portfolio getPortfolioById(Long portfolioID);
    void savePortfolio(Portfolio portfolio);
    void deleteSpending(String id);
    void deleteCategory(String id);
}
