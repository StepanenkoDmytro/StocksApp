package com.stock.dto.analytic;


import com.stock.api.entity.alphaVantage.crypto.TimeSeriesData;
import com.stock.api.entity.alphaVantage.stock.WeeklyTimeSeries;
import com.stock.dto.forCharts.CandlesDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@AllArgsConstructor
public class DataPrice {
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;
    private LocalDate date;

    public static Optional<DataPrice> mapFromTimeSeriesData(WeeklyTimeSeries candleData, String date) {
        try {
            BigDecimal open = new BigDecimal(candleData.getOpen());
            BigDecimal high = new BigDecimal(candleData.getHigh());
            BigDecimal low = new BigDecimal(candleData.getLow());
            BigDecimal close = new BigDecimal(candleData.getClose());
            BigDecimal volume = new BigDecimal(candleData.getVolume());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newDate = LocalDate.parse(date, formatter);

            DataPrice dataPrice = new DataPrice(open, high, low, close, volume, newDate);
            return Optional.of(dataPrice);
        } catch (NullPointerException | NumberFormatException e) {
            return Optional.empty();
        }
    }
}
