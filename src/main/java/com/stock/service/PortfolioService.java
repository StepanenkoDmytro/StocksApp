package com.stock.service;

import com.stock.model.portfolio.Portfolio;

public interface PortfolioService {
    Portfolio getPortfolioById(Long portfolioID);
    void savePortfolio(Portfolio portfolio);
    void deleteSpending(String id);
    void deleteCategory(String id);
}
