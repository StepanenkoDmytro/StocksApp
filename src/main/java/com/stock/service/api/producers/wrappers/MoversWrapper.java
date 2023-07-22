package com.stock.service.api.producers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.service.api.producers.entity.yahooFinance.MoversData;
import lombok.Data;

@Data
public class MoversWrapper {
    @JsonProperty("finance")
    private MoversData finance;
}
