package com.stock.model.coin.api.producers.entity.yahooFinance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YHQuote {
    @JsonProperty("quoteType")
    private String quoteType;
    @JsonProperty("fullExchangeName")
    private String fullExchangeName;
    @JsonProperty("symbol")
    private String symbol;
}
