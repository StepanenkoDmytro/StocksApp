package com.stock.model.stock.analytic.service.impl;

import com.stock.model.stock.analytic.service.ProfitDataService;
import com.stock.model.coin.api.producers.AlphaVantageMarket;
import com.stock.model.coin.api.producers.GlobalStocksIndexMarket;
import com.stock.dto.analytic.DataPriceShort;
import com.stock.model.stock.analytic.entities.ProfitData;
import com.stock.service.helpers.YearMonthKey;
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

    public Map<YearMonthKey, Double> getStocksProfit(String ticker) {
        List<ProfitData> stockProfit = profitDataService.getProfitsByTicker(ticker);

        if (!stockProfit.isEmpty()) {

            return mapDBProfitDataToMap(stockProfit);
        } else {
            List<DataPriceShort> prices = alphaVantageMarket.findMonthlyPricesById(ticker);
            return calculateProfit(prices, ticker);
        }
    }

    public Map<YearMonthKey, Double> getIndexProfit() {
        List<ProfitData> sp500 = profitDataService.getProfitsByTicker("SP500");
        if (!sp500.isEmpty()) {

            return mapDBProfitDataToMap(sp500);
        } else {
            List<DataPriceShort> sp500Data = globalStocksIndexMarket.getSP500Data();
            return calculateProfit(sp500Data, "SP500");
        }
    }

    private Map<YearMonthKey, Double> calculateProfit(List<DataPriceShort> prices, String ticker) {
        Map<YearMonthKey, Double> mapProfits = new HashMap<>();
        LocalDate limit = LocalDate.now().minusYears(1);

        prices.stream()
                .filter(month -> month.getDate().isAfter(limit))
                .forEach(month -> {
                    double close = month.getClose().doubleValue();
                    double open = month.getOpen().doubleValue();
                    LocalDate date = month.getDate();

                    YearMonthKey key = new YearMonthKey(date.getMonthValue(), date.getYear());
                    double value = ((close - open) / open) * 100;
                    mapProfits.put(key, value);

                    profitDataService.saveProfit(ticker, date, value);
                });
        return mapProfits;
    }

    private Map<YearMonthKey, Double> mapDBProfitDataToMap(List<ProfitData> stockDataProfit) {
        Map<YearMonthKey, Double> result = new HashMap<>();

        for(ProfitData data : stockDataProfit) {
            double doubleValue = data.getProfitData().doubleValue();
            LocalDate date = data.getDate();
            result.put(new YearMonthKey(date.getMonthValue(), date.getYear()), doubleValue);
        }
        return result;
    }
}
