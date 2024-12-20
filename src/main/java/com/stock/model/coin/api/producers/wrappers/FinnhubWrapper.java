package com.stock.model.coin.api.producers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinnhubWrapper {
    @JsonProperty("c")
    private BigDecimal currentPrice;
    @JsonProperty("h")
    private BigDecimal highPrice;
    @JsonProperty("l")
    private BigDecimal lowPrice;
    @JsonProperty("o")
    private BigDecimal openPrice;
    @JsonProperty("pc")
    private BigDecimal previousClosePrice;
    @JsonProperty("t")
    private long timestamp;
}
