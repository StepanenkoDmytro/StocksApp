package com.stock.service.analytic.stock.service;

import java.time.LocalDate;

public interface ProfitDataService {
    void saveProfit(String ticker, LocalDate date, double value);
}
