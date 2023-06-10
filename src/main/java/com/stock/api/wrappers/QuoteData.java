package com.stock.api.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.api.entity.alphaVantage.crypto.MetaData;
import com.stock.api.entity.alphaVantage.stock.Quote;
import lombok.Data;

@Data
public class QuoteData {
    @JsonProperty("Global Quote")
    private Quote quote;
}
