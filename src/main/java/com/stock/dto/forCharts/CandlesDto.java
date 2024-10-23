package com.stock.dto.forCharts;

import com.stock.model.coin.api.producers.entity.alphaVantage.crypto.TimeSeriesData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

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
    public static Optional<CandlesDto> mapFromTimeSeriesData(TimeSeriesData candleData, String date) {
        try {
            BigDecimal open = new BigDecimal(candleData.getOpenUSD());
            BigDecimal high = new BigDecimal(candleData.getHighUSD());
            BigDecimal low = new BigDecimal(candleData.getLowUSD());
            BigDecimal close = new BigDecimal(candleData.getCloseUSD());
            BigDecimal volume = new BigDecimal(candleData.getVolume());

            CandlesDto candlesDto = new CandlesDto(open, high, low, close, volume, date);
            return Optional.of(candlesDto);
        } catch (NullPointerException | NumberFormatException e) {
            return Optional.empty();
        }
    }

}
