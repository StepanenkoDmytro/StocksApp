package com.stock.model.coin.api.producers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.model.coin.api.producers.entity.coinCap.Candle;
import lombok.Data;

import java.util.List;

@Data
public class CandlesData {
    @JsonProperty("data")
    private List<Candle> data;
    @JsonProperty("timestamp")
    private long timestamp;
}
