package com.stock.api.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.api.entity.alphaVantage.crypto.MetaData;
import com.stock.api.entity.alphaVantage.stock.WeeklyTimeSeries;
import lombok.Data;

import java.util.Map;

@Data
public class WeeklyDataAlphaVantage {
    @JsonProperty("Meta Data")
    private MetaData metaData;
    @JsonProperty("Weekly Time Series")
    private Map<String, WeeklyTimeSeries> data;
}
