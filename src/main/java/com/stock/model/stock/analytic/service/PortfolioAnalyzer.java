package com.stock.model.stock.analytic.service;

import com.stock.model.user.account.entities.Account;
import com.stock.model.stock.analytic.entities.RiskType;

public interface PortfolioAnalyzer {
    RiskType getRiskStockPortfolio(Account account);
}
