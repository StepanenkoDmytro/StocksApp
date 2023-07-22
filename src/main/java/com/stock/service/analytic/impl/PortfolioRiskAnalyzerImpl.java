package com.stock.service.analytic.impl;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.service.exceptions.AccountFetchException;
import com.stock.model.account.AccountType;
import com.stock.model.stock.analytic.RiskType;
import com.stock.service.helpers.InterpreterHelper;
import com.stock.service.analytic.PortfolioRiskAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class PortfolioRiskAnalyzerImpl implements PortfolioRiskAnalyzer {
    private final ProfitDataProcessor profitDataProcessor;

    @Autowired
    public PortfolioRiskAnalyzerImpl(ProfitDataProcessor profitDataProcessor) {
        this.profitDataProcessor = profitDataProcessor;
    }

    @Override
    public RiskType getRiskStockPortfolio(AccountDto account) {
        if(account.getAccountType().equals(AccountType.StockWallet.name())) {
            List<AccountStockDto> stocks = account.getStocks();
            double risk = calculateRiskStocksByBeta(stocks);
            return InterpreterHelper.riskInterpreter(risk);
        }
        throw new AccountFetchException("PortfolioRiskAnalyzer: Invalid account type");
    }

    private double calculateRiskStocksByBeta(List<AccountStockDto> stocks) {
        Map<Integer, Double> indexProfit = profitDataProcessor.getIndexProfit();
        double avgIndexProfit = calculateTotalProfit(indexProfit);

        List<Double> listStocksCovariance = calculateStocksCovariance(stocks, indexProfit, avgIndexProfit);
        double dispersion = calculateDispersion(indexProfit, avgIndexProfit);

        double sum = listStocksCovariance.stream()
                .map(value -> (value / dispersion)) //calculate β for stock
                .mapToDouble(Double::doubleValue)
                .sum();

        return sum / listStocksCovariance.size(); //divide the sum of β by the quantity
    }

    private double calculateDispersion(Map<Integer, Double> indexProfits, Double avgProfit) {
        return indexProfits.values().stream()
                .mapToDouble(value -> Math.pow(value - avgProfit, 2))
                .sum() / (indexProfits.size() - 1);
    }

    private List<Double> calculateStocksCovariance(List<AccountStockDto> stocks, Map<Integer, Double> indexProfit, Double avgSP500) {
        return stocks.stream()
                .map(stock -> {
                    Map<Integer, Double> profits = profitDataProcessor.getStocksProfit(stock.getSymbol());
                    double avgProfit = calculateTotalProfit(profits);

                    double covariance = profits.keySet().stream()
                            .mapToDouble(i -> (profits.get(i) - avgProfit) * (indexProfit.get(i) - avgSP500))
                            .sum();
                    return covariance / (profits.size() - 1);
                })
                .toList();
    }

    private double calculateTotalProfit(Map<Integer, Double> monthlyCloseMap) {
        Collection<Double> values = monthlyCloseMap.values();
        double sum = values.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        return sum / values.size();
    }
}
