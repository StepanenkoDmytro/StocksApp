package com.stock.api.entity.FearGreedIndex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FearGreedIndex {
    @JsonProperty("value")
    private String value;

    @JsonProperty("value_classification")
    private String valueClassification;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("time_until_update")
    private String timeUntilUpdate;
}
