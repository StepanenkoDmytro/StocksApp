package com.stock.service.analytic.stock.calculators;

import com.stock.api.AlphaVantageMarket;
import com.stock.dto.analytic.DataPrice;
import com.stock.model.stock.analytic.ProfitData;
import com.stock.repository.stock.ProfitDataRepository;
import com.stock.service.analytic.stock.service.ProfitDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class StockCalculator {
    private final AlphaVantageMarket alphaVantageMarket;
    private final ProfitDataRepository profitDataRepository;
    private final ProfitDataService profitDataService;

    @Autowired
    public StockCalculator(AlphaVantageMarket alphaVantageMarket, ProfitDataRepository profitDataRepository, ProfitDataService profitDataService) {
        this.alphaVantageMarket = alphaVantageMarket;
        this.profitDataRepository = profitDataRepository;
        this.profitDataService = profitDataService;
    }

    public Map<Integer, Double> getListProfitsByStock(String ticker) {
        List<ProfitData> stockProfit = profitDataRepository.findAllByTicker(ticker);

        if (!stockProfit.isEmpty()) {

            return mapDBDataToMap(stockProfit);
        } else {
            List<DataPrice> prices = alphaVantageMarket.findMonthlyPricesById(ticker);
            return calculateStockProfit(prices, ticker);
        }
    }

    private Map<Integer, Double> calculateStockProfit(List<DataPrice> prices, String ticker) {
        Map<Integer, Double> mapProfits = new HashMap<>();
        LocalDate limit = LocalDate.now().minusYears(1);
        prices.stream()
                .filter(month -> month.getDate().isAfter(limit))
                .forEach(month -> {
                    double close = month.getClose().doubleValue();
                    double open = month.getOpen().doubleValue();

                    double value = ((close - open) / open) * 100;
                    LocalDate monthValue = month.getDate();
                    mapProfits.put(monthValue.getMonthValue(), value);

                    profitDataService.saveProfit(ticker, monthValue, value);
                });
        return mapProfits;
    }

    private Map<Integer, Double> mapDBDataToMap(List<ProfitData> stockDataProfit) {
        Map<Integer, Double> result = new HashMap<>();

        for(ProfitData data : stockDataProfit) {
            double doubleValue = data.getProfitData().doubleValue();
            int monthValue = data.getDate().getMonthValue();
            result.put(monthValue, doubleValue);
        }
        return result;
    }

    public Double calculateTotalProfit(Map<Integer, Double> monthlyCloseMap) {
        Collection<Double> values = monthlyCloseMap.values();
        double sum = values.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        return sum / values.size();
    }
}
