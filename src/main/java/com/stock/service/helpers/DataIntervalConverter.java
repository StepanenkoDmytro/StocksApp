package com.stock.service.helpers;

import com.stock.service.api.producers.entity.globalIndex.IndexData;
import com.stock.dto.analytic.DataPriceShort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataIntervalConverter {
    public static List<DataPriceShort> convertDailyToMonthlyData(List<IndexData> indexDataList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        Map<YearMonthKey, List<IndexData>> dataByMonth = indexDataList.stream()
                .sorted(Comparator.comparing(data -> LocalDate.parse(data.getDate(), formatter)))
                .collect(Collectors.groupingBy(data -> {
                    LocalDate date = LocalDate.parse(data.getDate(), formatter);
                    return new YearMonthKey(date.getMonthValue(), date.getYear());
                }));

        List<DataPriceShort> dataPriceList = new ArrayList<>();
        for (List<IndexData> monthData : dataByMonth.values()) {
            BigDecimal open = BigDecimal.valueOf(monthData.get(0).getClose());
            BigDecimal close = BigDecimal.valueOf(monthData.get(monthData.size() - 1).getClose());
            BigDecimal dividend = BigDecimal.ZERO;
            LocalDate date = LocalDate.parse(monthData.get(monthData.size() - 1).getDate(), formatter);

            DataPriceShort dataPrice = new DataPriceShort(open, close, dividend, date);

            dataPriceList.add(dataPrice);
        }

        return dataPriceList;
    }
}
