package com.stock.dto.analytic;

import com.stock.service.api.producers.entity.alphaVantage.stock.MonthlyTimeSeries;
import com.stock.service.api.producers.entity.alphaVantage.stock.WeeklyTimeSeries;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@AllArgsConstructor
public class DataPriceShort {
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal dividendAmount;
    private LocalDate date;

    public static Optional<DataPriceShort> mapFromTimeSeriesData(WeeklyTimeSeries candleData, String date) {
        try {
            BigDecimal open = new BigDecimal(candleData.getOpen());
            BigDecimal close = new BigDecimal(candleData.getClose());
            BigDecimal dividendAmount = BigDecimal.ZERO;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newDate = LocalDate.parse(date, formatter);

            DataPriceShort dataPrice = new DataPriceShort(open, close, dividendAmount, newDate);
            return Optional.of(dataPrice);
        } catch (NullPointerException | NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<DataPriceShort> mapFromTimeSeriesData(MonthlyTimeSeries candleData, String date) {
        try {
            BigDecimal open = new BigDecimal(candleData.getOpen());
            BigDecimal close = new BigDecimal(candleData.getClose());
            BigDecimal dividendAmount = new BigDecimal(candleData.getDividendAmount());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newDate = LocalDate.parse(date, formatter);

            DataPriceShort dataPrice = new DataPriceShort(open, close, dividendAmount, newDate);
            return Optional.of(dataPrice);
        } catch (NullPointerException | NumberFormatException e) {
            return Optional.empty();
        }
    }
}
