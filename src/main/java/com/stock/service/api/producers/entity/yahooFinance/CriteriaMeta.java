package com.stock.service.api.producers.entity.yahooFinance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CriteriaMeta {
    @JsonProperty("size")
    private int size;
    @JsonProperty("offset")
    private int offset;
    @JsonProperty("sortField")
    private String sortField;
    @JsonProperty("sortType")
    private String sortType;
    @JsonProperty("quoteType")
    private String quoteType;
    @JsonProperty("topOperator")
    private String topOperator;
}
