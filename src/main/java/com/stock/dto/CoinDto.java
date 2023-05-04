package com.stock.dto;

import com.stock.model.Coin;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
public class CoinDto {
    String name;
    String symbol;
    BigDecimal priceUSD;

    public static CoinDto mapCoinToDto(Coin coin) {
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(coin.getPriceUsd()));
        BigDecimal price = value.setScale(2, RoundingMode.HALF_UP);
        return new CoinDto(
                coin.getName(),
                coin.getSymbol(),
                price
                );
    }
}
