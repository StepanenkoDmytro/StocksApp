package com.stock.api.entity.coinCap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Coin {
    @JsonProperty("id")
    String id;
    @JsonProperty("rank")
    String rank;
    @JsonProperty("symbol")
    String symbol;
    @JsonProperty("name")
    String name;
    @JsonProperty("supply")
    String supply;
    @JsonProperty("maxSupply")
    String maxSupply;
    @JsonProperty("marketCapUsd")
    String marketCapUsd;
    @JsonProperty("volumeUsd24Hr")
    String volumeUsd24Hr;
    @JsonProperty("priceUsd")
    String priceUsd;
    @JsonProperty("changePercent24Hr")
    String changePercent24Hr;
    @JsonProperty("vwap24Hr")
    String vwap24Hr;
    @JsonProperty("explorer")
    String explorer;

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", rank='" + rank + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", supply='" + supply + '\'' +
                ", maxSupply='" + maxSupply + '\'' +
                ", marketCapUsd='" + marketCapUsd + '\'' +
                ", volumeUsd24Hr='" + volumeUsd24Hr + '\'' +
                ", priceUsd='" + priceUsd + '\'' +
                ", changePercent24Hr='" + changePercent24Hr + '\'' +
                ", vwap24Hr='" + vwap24Hr + '\'' +
                ", explorer='" + explorer + '\'' +
                '}';
    }
}
