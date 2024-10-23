package com.stock.model.coin.api.producers.entity.globalIndex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IndexData {
    @JsonProperty("date")
    private String date;
    @JsonProperty("close")
    private double close;

}
