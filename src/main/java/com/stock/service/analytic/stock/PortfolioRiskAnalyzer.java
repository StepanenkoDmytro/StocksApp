package com.stock.service.analytic.stock;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.model.stock.analytic.RiskType;

public interface PortfolioRiskAnalyzer {
    RiskType getRiskStockPortfolio(AccountDto account);
}
