package com.stock.service.analytic;

import com.stock.model.account.Account;
import com.stock.model.stock.analytic.RiskType;

public interface PortfolioRiskAnalyzer {
    RiskType getRiskStockPortfolio(Account account);
}
