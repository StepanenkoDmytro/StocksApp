package com.stock.api.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.api.entity.globalIndex.IndexData;
import lombok.Data;

import java.util.List;

@Data
public class GlobalIndexWrapper {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("data")
    private List<IndexData> data;
}
