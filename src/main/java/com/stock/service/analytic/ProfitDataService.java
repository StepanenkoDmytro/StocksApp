package com.stock.service.analytic;

import com.stock.model.stock.analytic.ProfitData;

import java.time.LocalDate;
import java.util.List;

public interface ProfitDataService {
    List<ProfitData> getProfitsByTicker(String ticker);
    void saveProfit(String ticker, LocalDate date, double value);
}
