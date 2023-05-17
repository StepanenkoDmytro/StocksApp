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
    String symbol;
    BigDecimal priceUSD;
    BigDecimal marketCapUsd;

    public static CoinDto mapCoinToDto(Coin coin) {
        BigDecimal valuePrice;
        BigDecimal marketCapUsd;
        if(coin.getMarketCapUsd() != null) {
            marketCapUsd = BigDecimal.valueOf(Double.parseDouble(coin.getMarketCapUsd()));
            valuePrice = BigDecimal.valueOf(Double.parseDouble(coin.getPriceUsd()));
        }else {
            marketCapUsd = BigDecimal.ZERO;
            valuePrice = BigDecimal.ZERO;
        }

        return new CoinDto(
                coin.getId(),
                coin.getName(),
                coin.getSymbol(),
                valuePrice.setScale(2, RoundingMode.HALF_UP),
                marketCapUsd.setScale(2, RoundingMode.HALF_UP)
                );
    }
}
