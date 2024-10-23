package com.stock.model.coin.api.producers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.model.coin.api.producers.entity.alphaVantage.crypto.MetaData;
import com.stock.model.coin.api.producers.entity.alphaVantage.stock.MonthlyTimeSeries;
import lombok.Data;

import java.util.Map;

@Data
public class MonthlyDataAlphaVantage {
    @JsonProperty("Meta Data")
    private MetaData metaData;
    @JsonProperty("Monthly Adjusted Time Series")
    private Map<String, MonthlyTimeSeries> data;
}
