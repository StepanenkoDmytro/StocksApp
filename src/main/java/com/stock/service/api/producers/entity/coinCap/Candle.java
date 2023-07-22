package com.stock.service.api.producers.entity.coinCap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Candle {
    @JsonProperty("open")
    String open;
    @JsonProperty("high")
    String high;
    @JsonProperty("low")
    String low;
    @JsonProperty("close")
    String close;
    @JsonProperty("volume")
    String volume;
    @JsonProperty("period")
    String period;

    @Override
    public String toString() {
        return "{" +
                "open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", volume='" + volume + '\'' +
                ", period='" + period + '\'' +
                '}';
    }
}
