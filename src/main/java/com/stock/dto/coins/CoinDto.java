package com.stock.dto.coins;

import com.stock.model.coin.api.producers.entity.coinCap.Coin;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
public class CoinDto {
    private String id;
    private String name;
    private String symbol;
    private String assetType;
    private BigDecimal price;
    private BigDecimal marketCapitalization;

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

        String assetType = "Crypto";

        return new CoinDto(
                coin.getId(),
                coin.getName(),
                coin.getSymbol(),
                assetType,
                valuePrice.setScale(2, RoundingMode.HALF_UP),
                marketCapUsd.setScale(2, RoundingMode.HALF_UP)
                );
    }
}
