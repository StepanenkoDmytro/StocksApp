package com.stock.service.analytic.stock.service;

import com.stock.model.stock.analytic.ProfitData;
import com.stock.repository.stock.ProfitDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ProfitDataServiceImpl implements ProfitDataService {
    private final ProfitDataRepository profitDataRepository;

    @Autowired
    public ProfitDataServiceImpl(ProfitDataRepository profitDataRepository) {
        this.profitDataRepository = profitDataRepository;
    }

    @Override
    public void saveProfit(String ticker, LocalDate date, double value) {
        ProfitData profit = new ProfitData();
        profit.setProfitData(BigDecimal.valueOf(value));
        profit.setDate(date);
        profit.setTicker(ticker);
        profitDataRepository.save(profit);
    }
}
