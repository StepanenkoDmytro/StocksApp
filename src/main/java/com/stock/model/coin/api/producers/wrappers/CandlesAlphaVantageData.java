package com.stock.model.coin.api.producers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.model.coin.api.producers.entity.alphaVantage.crypto.MetaData;
import com.stock.model.coin.api.producers.entity.alphaVantage.crypto.TimeSeriesData;
import lombok.Data;

import java.util.Map;

@Data
public class CandlesAlphaVantageData {
    @JsonProperty("Meta Data")
    private MetaData metaData;
    @JsonProperty("Time Series (Digital Currency Daily)")
    private Map<String, TimeSeriesData> data;
}
