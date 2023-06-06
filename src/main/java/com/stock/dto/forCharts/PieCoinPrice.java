package com.stock.dto.forCharts;

import com.stock.dto.coins.CoinDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PieCoinPrice {
    private String label;
    private BigDecimal value;

    public PieCoinPrice(String label, BigDecimal value) {
        this.label = label;
        this.value = value;
    }

    public static PieCoinPrice mapCoinDto(CoinDto coin) {
        return new PieCoinPrice(coin.getName(), coin.getPriceUSD());
    }
}
