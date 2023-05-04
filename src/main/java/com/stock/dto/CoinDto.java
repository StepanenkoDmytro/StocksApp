package com.stock.dto;

import com.stock.api.entity.Coin;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
public class CoinDto {
    String id;
    String name;
    BigDecimal priceUSD;
    BigDecimal marketCapUsd;

    public static CoinDto mapCoinToDto(Coin coin) {
        BigDecimal valuePrice = BigDecimal.valueOf(Double.parseDouble(coin.getPriceUsd()));
        BigDecimal marketCapUsd = BigDecimal.valueOf(Double.parseDouble(coin.getMarketCapUsd()));

        return new CoinDto(
                coin.getId(),
                coin.getName(),
                valuePrice.setScale(2, RoundingMode.HALF_UP),
                marketCapUsd.setScale(2, RoundingMode.HALF_UP)
                );
    }
}
