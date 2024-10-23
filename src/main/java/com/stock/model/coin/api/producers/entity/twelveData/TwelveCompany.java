package com.stock.model.coin.api.producers.entity.twelveData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TwelveCompany {
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;
    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("type")
    private String type;
}
