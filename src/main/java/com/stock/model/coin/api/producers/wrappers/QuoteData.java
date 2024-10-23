package com.stock.model.coin.api.producers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.model.coin.api.producers.entity.alphaVantage.stock.Quote;
import lombok.Data;

@Data
public class QuoteData {
    @JsonProperty("Global Quote")
    private Quote quote;
}
