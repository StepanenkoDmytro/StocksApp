package com.stock.model.coin.api.producers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.model.coin.api.producers.entity.yahooFinance.MoversData;
import lombok.Data;

@Data
public class MoversWrapper {
    @JsonProperty("finance")
    private MoversData finance;
}
