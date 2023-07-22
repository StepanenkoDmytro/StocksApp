package com.stock.service.analytic.impl;

import com.stock.model.stock.analytic.ProfitData;
import com.stock.repository.stock.ProfitDataRepository;
import com.stock.service.analytic.ProfitDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProfitDataServiceImpl implements ProfitDataService {
    private final ProfitDataRepository profitDataRepository;

    @Autowired
    public ProfitDataServiceImpl(ProfitDataRepository profitDataRepository) {
        this.profitDataRepository = profitDataRepository;
    }

    @Override
    public List<ProfitData> getProfitsByTicker(String ticker) {
        return profitDataRepository.findAllByTicker(ticker);
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
