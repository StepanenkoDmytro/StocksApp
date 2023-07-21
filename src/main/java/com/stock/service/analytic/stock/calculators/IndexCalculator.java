package com.stock.service.analytic.stock.calculators;

import com.stock.api.GlobalStocksIndexMarket;
import com.stock.api.entity.globalIndex.IndexData;
import com.stock.model.stock.analytic.ProfitData;
import com.stock.repository.stock.ProfitDataRepository;
import com.stock.service.analytic.stock.service.ProfitDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class IndexCalculator {
    private final GlobalStocksIndexMarket globalStocksIndexMarket;
    private final ProfitDataRepository profitDataRepository;
    private final ProfitDataService profitDataService;

    @Autowired
    public IndexCalculator(GlobalStocksIndexMarket globalStocksIndexMarket, ProfitDataRepository profitDataRepository, ProfitDataService profitDataService) {
        this.globalStocksIndexMarket = globalStocksIndexMarket;
        this.profitDataRepository = profitDataRepository;
        this.profitDataService = profitDataService;
    }

    public Map<Integer, Double> getIndexProfit() {
        List<ProfitData> sp500 = profitDataRepository.findAllByTicker("SP500");
        if (!sp500.isEmpty()) {

            return mapDBDataToMap(sp500);
        } else {
            List<IndexData> sp500Data = globalStocksIndexMarket.getSP500Data();
            Map<LocalDate, Double> localDateDoubleMap = convertToMap(sp500Data);

            return calculateIndexProfit(localDateDoubleMap);
        }
    }

    public Double calculateTotalProfit(Map<Integer, Double> monthlyCloseMap) {
        Collection<Double> values = monthlyCloseMap.values();
        double sum = values.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        return sum / values.size();
    }

    private Map<Integer, Double> mapDBDataToMap(List<ProfitData> indexData) {
        Map<Integer, Double> result = new HashMap<>();

        for (ProfitData data : indexData) {
            double doubleValue = data.getProfitData().doubleValue();
            int monthValue = data.getDate().getMonthValue();
            result.put(monthValue, doubleValue);
        }
        return result;
    }

    private Map<LocalDate, Double> convertToMap(List<IndexData> data) {
        Map<LocalDate, Double> dataRoMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        for (IndexData indexData : data) {
            LocalDate date = LocalDate.parse(indexData.getDate(), formatter);
            dataRoMap.put(date, indexData.getClose());
        }
        return dataRoMap;
    }

    private Map<Integer, Double> calculateIndexProfit(Map<LocalDate, Double> indexData) {
        Map<Integer, Double> monthlyCloseMap = new HashMap<>();

        List<LocalDate> localDates = indexData.keySet().stream()
                .sorted(Comparator.naturalOrder())
                .toList();

        LocalDate firstDayOfMonth = localDates.get(0);
        int month = firstDayOfMonth.getMonthValue();

        LocalDate prevDate = firstDayOfMonth;
        for (int i = 1; i < localDates.size(); i++) {
            int currentMonth = localDates.get(i).getMonthValue();
            double diff;

            if (month == currentMonth) {
                prevDate = localDates.get(i);
            } else {
                diff = calculateDifferenceDouble(firstDayOfMonth, prevDate, indexData);

                monthlyCloseMap.put(month, diff);
                profitDataService.saveProfit("SP500", prevDate, diff);

                month = currentMonth;
                firstDayOfMonth = localDates.get(i);
            }

            if (i == localDates.size() - 1) {
                diff = calculateDifferenceDouble(firstDayOfMonth, localDates.get(i), indexData);

                monthlyCloseMap.put(month, diff);
                profitDataService.saveProfit("SP500", localDates.get(i), diff);
            }
        }
        return monthlyCloseMap;
    }

    private Double calculateDifferenceDouble(LocalDate first, LocalDate last, Map<LocalDate, Double> map) {
        Double closeFirstDay = map.get(first);
        Double closeLastDay = map.get(last);

        return ((closeLastDay - closeFirstDay) / closeFirstDay) * 100;
    }
}
