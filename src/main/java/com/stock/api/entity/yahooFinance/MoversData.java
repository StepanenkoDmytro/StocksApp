package com.stock.api.entity.yahooFinance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MoversData {
    @JsonProperty("result")
    private List<Mover> result;
    @JsonProperty("error")
    private String error;
}
