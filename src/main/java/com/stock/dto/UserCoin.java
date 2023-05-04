package com.stock.dto;

import com.stock.api.entity.Coin;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
public class UserCoin {
    String id;
    BigDecimal buyPrice;

    public static UserCoin mapCoinToUser(Coin coin) {
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(coin.getPriceUsd()));
        BigDecimal price = value.setScale(2, RoundingMode.HALF_UP);
        return new UserCoin(
                coin.getId(),
                price);
    }
}
