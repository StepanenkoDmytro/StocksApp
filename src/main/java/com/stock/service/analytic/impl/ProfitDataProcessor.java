package com.stock.service.analytic.impl;

import com.stock.service.analytic.ProfitDataService;
import com.stock.service.api.producers.AlphaVantageMarket;
import com.stock.service.api.producers.GlobalStocksIndexMarket;
import com.stock.dto.analytic.DataPriceShort;
import com.stock.model.stock.analytic.ProfitData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class ProfitDataProcessor {
    private final AlphaVantageMarket alphaVantageMarket;
    private final GlobalStocksIndexMarket globalStocksIndexMarket;
    private final ProfitDataService profitDataService;
    @Autowired
    public ProfitDataProcessor(AlphaVantageMarket alphaVantageMarket, ProfitDataService profitDataService, GlobalStocksIndexMarket globalStocksIndexMarket) {
        this.alphaVantageMarket = alphaVantageMarket;
        this.profitDataService = profitDataService;
        this.globalStocksIndexMarket = globalStocksIndexMarket;
    }

    public Map<Integer, Double> getStocksProfit(String ticker) {
        List<ProfitData> stockProfit = profitDataService.getProfitsByTicker(ticker);

        if (!stockProfit.isEmpty()) {

            return mapDBProfitDataToMap(stockProfit);
        } else {
            List<DataPriceShort> prices = alphaVantageMarket.findMonthlyPricesById(ticker);
            return calculateProfit(prices, ticker);
        }
    }

    public Map<Integer, Double> getIndexProfit() {
        List<ProfitData> sp500 = profitDataService.getProfitsByTicker("SP500");;
        if (!sp500.isEmpty()) {

            return mapDBProfitDataToMap(sp500);
        } else {
            List<DataPriceShort> sp500Data = globalStocksIndexMarket.getSP500Data();
            return calculateProfit(sp500Data, "SP500");
        }
    }

    private Map<Integer, Double> calculateProfit(List<DataPriceShort> prices, String ticker) {
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

    private Map<Integer, Double> mapDBProfitDataToMap(List<ProfitData> stockDataProfit) {
        Map<Integer, Double> result = new HashMap<>();

        for(ProfitData data : stockDataProfit) {
            double doubleValue = data.getProfitData().doubleValue();
            int monthValue = data.getDate().getMonthValue();
            result.put(monthValue, doubleValue);
        }
        return result;
    }
}
