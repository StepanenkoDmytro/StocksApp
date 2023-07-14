package com.stock.service.analytic.stock;

import com.stock.model.account.AccountStock;

import java.util.List;

public interface PortfolioAnalytic {
    String calculateRiskByStandardDeviation(List<AccountStock> stocks);
}
