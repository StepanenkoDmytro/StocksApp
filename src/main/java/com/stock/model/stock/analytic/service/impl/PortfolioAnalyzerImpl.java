package com.stock.model.stock.analytic.service.impl;

import com.stock.dto.forCharts.PricesData;
import com.stock.model.user.account.entities.Account;
import com.stock.model.user.account.entities.AccountStock;
import com.stock.model.stock.service.StockService;
import com.stock.exceptions.AccountFetchException;
import com.stock.model.user.account.entities.AccountType;
import com.stock.model.stock.analytic.entities.RiskType;
import com.stock.service.helpers.InterpreterHelper;
import com.stock.model.stock.analytic.service.PortfolioAnalyzer;
import com.stock.service.helpers.YearMonthKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PortfolioAnalyzerImpl implements PortfolioAnalyzer {
    private final ProfitDataProcessor profitDataProcessor;
    private final StockService stockService;

    @Autowired
    public PortfolioAnalyzerImpl(ProfitDataProcessor profitDataProcessor, StockService stockService) {
        this.profitDataProcessor = profitDataProcessor;
        this.stockService = stockService;
    }

    @Override
    public RiskType getRiskStockPortfolio(Account account) {
        if (AccountType.StockWallet.equals(account.getAccountType())) {
            List<AccountStock> stocks = account.getStocks();
            double risk = calculateRiskStocksByBeta(stocks);
            return InterpreterHelper.riskInterpreter(risk);
        }
        throw new AccountFetchException("PortfolioRiskAnalyzer doesn't implements this account type yet");
    }

    private double calculateRiskStocksByBeta(List<AccountStock> stocks) {
        Map<YearMonthKey, Double> indexProfit = profitDataProcessor.getIndexProfit();
        double avgIndexProfit = calculateTotalProfit(indexProfit);

        Map<AccountStock, Double> listStocksCovariance = calculateStocksCovariance(stocks, indexProfit, avgIndexProfit);
        double dispersion = calculateDispersion(indexProfit, avgIndexProfit);
        Map<String, Double> listStocksWeight = calculateStockWeights(stocks);

        return listStocksCovariance.keySet().stream()
                .map(key -> ((listStocksCovariance.get(key) / dispersion) * listStocksWeight.get(key.getSymbol()))) //calculate β for stock with weight in portfolio
                .mapToDouble(Double::doubleValue)
                .sum(); //get sum of β * weight
    }

    private Map<String, Double> calculateStockWeights(List<AccountStock> stocks) {
        List<PricesData> prices = stockService.getPriceAccountStocksByListWithoutUSD(stocks);
        double portfolioCost = prices.stream()
                .map(PricesData::getValue)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();

        Map<String, Double> stockWeights = new HashMap<>();
        for(PricesData data : prices) {
            double weight = data.getValue().doubleValue() / portfolioCost;
            stockWeights.put(data.getLabel(), weight);
        }
        return stockWeights;
    }

    private double calculateDispersion(Map<YearMonthKey, Double> indexProfits, Double avgProfit) {
        return indexProfits.values().stream()
                .mapToDouble(value -> Math.pow(value - avgProfit, 2))
                .sum() / (indexProfits.size() - 1);
    }

    private Map<AccountStock, Double> calculateStocksCovariance(List<AccountStock> stocks, Map<YearMonthKey, Double> indexProfit, Double avgSP500) {
        Map<AccountStock, Double> stockCovarianceMap = new HashMap<>();

        for (AccountStock stock : stocks) {
            Map<YearMonthKey, Double> profits = profitDataProcessor.getStocksProfit(stock.getSymbol());
            double avgProfit = calculateTotalProfit(profits);

            double covariance = profits.keySet().stream()
                    .mapToDouble(i -> (profits.get(i) - avgProfit) * (indexProfit.get(i) - avgSP500))
                    .sum();
            double covarianceValue = covariance / (profits.size() - 1);

            stockCovarianceMap.put(stock, covarianceValue);
        }

        return stockCovarianceMap;
    }

    private double calculateTotalProfit(Map<YearMonthKey, Double> monthlyCloseMap) {
        Collection<Double> values = monthlyCloseMap.values();
        double sum = values.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        return sum / values.size();
    }
}
