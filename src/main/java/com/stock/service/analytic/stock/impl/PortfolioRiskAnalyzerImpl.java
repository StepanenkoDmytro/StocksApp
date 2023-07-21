package com.stock.service.analytic.stock.impl;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.exceptions.AccountFetchException;
import com.stock.model.account.AccountType;
import com.stock.model.stock.analytic.RiskType;
import com.stock.service.analytic.stock.analytic_helper.InterpreterHelper;
import com.stock.service.analytic.stock.calculators.IndexCalculator;
import com.stock.service.analytic.stock.PortfolioRiskAnalyzer;
import com.stock.service.analytic.stock.calculators.StockCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PortfolioRiskAnalyzerImpl implements PortfolioRiskAnalyzer {
    private final IndexCalculator indexCalculator;
    private final StockCalculator stockCalculator;

    @Autowired
    public PortfolioRiskAnalyzerImpl(IndexCalculator indexCalculator, StockCalculator stockCalculator) {
        this.indexCalculator = indexCalculator;
        this.stockCalculator = stockCalculator;
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
        Map<Integer, Double> indexProfit = indexCalculator.getIndexProfit();
        Double avgSP500 = indexCalculator.calculateTotalProfit(indexProfit);

        List<Double> listStocksCovariance = calculateStocksCovariance(stocks, indexProfit, avgSP500);
        double dispersion = calculateDispersion(indexProfit, avgSP500);

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
                    Map<Integer, Double> profits = stockCalculator.getListProfitsByStock(stock.getSymbol());
                    Double avgProfit = stockCalculator.calculateTotalProfit(profits);

                    double covariance = profits.keySet().stream()
                            .mapToDouble(i -> (profits.get(i) - avgProfit) * (indexProfit.get(i) - avgSP500))
                            .sum();
                    return covariance / (profits.size() - 1);
                })
                .toList();
    }
}
