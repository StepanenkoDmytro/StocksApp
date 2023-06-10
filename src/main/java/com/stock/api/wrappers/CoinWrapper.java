package com.stock.api.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.api.entity.coinCap.Coin;
import lombok.Data;

@Data
public class CoinWrapper {
    @JsonProperty("data")
    private Coin data;
    @JsonProperty("timestamp")
    private long timestamp;
}
