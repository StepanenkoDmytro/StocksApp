package com.stock.helpers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.model.Coin;
import lombok.Data;

import java.util.List;

@Data
public class CoinData {
    @JsonProperty("data")
    private List<Coin> data;
    @JsonProperty("timestamp")
    private long timestamp;

    @Override
    public String toString() {
        return "{" +
                "data:" + data +
                ", timestamp:" + timestamp +
                '}';
    }
}
