package com.stock.api.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.api.entity.coinCap.Candle;
import lombok.Data;

import java.util.List;

@Data
public class CandlesData {
    @JsonProperty("data")
    private List<Candle> data;
    @JsonProperty("timestamp")
    private long timestamp;
}
