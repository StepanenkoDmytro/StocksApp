package com.stock.model.coin.api.producers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.model.coin.api.producers.entity.coinCap.Coin;
import lombok.Data;

@Data
public class CoinWrapper {
    @JsonProperty("data")
    private Coin data;
    @JsonProperty("timestamp")
    private long timestamp;
}
