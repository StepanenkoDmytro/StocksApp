package com.stock.helpers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.model.Coin;
import lombok.Data;

@Data
public class CoinWrapper {
    @JsonProperty("data")
    private Coin data;
    @JsonProperty("timestamp")
    private long timestamp;
}
