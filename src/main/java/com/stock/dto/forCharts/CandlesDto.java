package com.stock.dto.forCharts;

import com.stock.api.entity.alphaVantage.crypto.TimeSeriesData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CandlesDto {
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;
    private String date;

    //    public static CandlesDto mapCandlesDto(Candle candle) {
//        BigDecimal open = new BigDecimal(candle.getOpen());
//        BigDecimal high = new BigDecimal(candle.getHigh());
//        BigDecimal low = new BigDecimal(candle.getLow());
//        BigDecimal close = new BigDecimal(candle.getClose());
//        BigDecimal volume = new BigDecimal(candle.getVolume());
//
//        return new CandlesDto(open,high,low, close, volume, candle.getPeriod());
//    }
    public static CandlesDto mapFromTimeSeriesData(TimeSeriesData candleData, String date) {
        BigDecimal open = new BigDecimal(candleData.getOpenUSD());
        BigDecimal high = new BigDecimal(candleData.getHighUSD());
        BigDecimal low = new BigDecimal(candleData.getLowUSD());
        BigDecimal close = new BigDecimal(candleData.getCloseUSD());
        BigDecimal volume = new BigDecimal(candleData.getVolume());

        return new CandlesDto(open, high, low, close, volume, date);
    }
}
