package com.stock.service.api.producers.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stock.service.api.producers.entity.FearGreedIndex.FearGreedIndex;
import lombok.Data;

import java.util.List;

@Data
public class FearGreedIndexWrapper {
    @JsonProperty("name")
    private String title;
    @JsonProperty("data")
    private List<FearGreedIndex> data;
}
