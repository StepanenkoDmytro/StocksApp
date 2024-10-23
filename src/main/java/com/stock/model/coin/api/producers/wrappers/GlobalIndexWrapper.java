package com.stock.model.coin.api.producers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.model.coin.api.producers.entity.globalIndex.IndexData;
import lombok.Data;

import java.util.List;

@Data
public class GlobalIndexWrapper {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("data")
    private List<IndexData> data;
}
