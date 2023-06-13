package com.stock.api.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.api.entity.yahooFinance.MoversData;
import lombok.Data;

@Data
public class MoversWrapper {
    @JsonProperty("finance")
    private MoversData finance;
}
