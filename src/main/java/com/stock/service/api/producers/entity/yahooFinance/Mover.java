package com.stock.service.api.producers.entity.yahooFinance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Mover {
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("canonicalName")
    private String canonicalName;
    @JsonProperty("start")
    private int start;
    @JsonProperty("count")
    private int count;
    @JsonProperty("total")
    private int total;
    @JsonProperty("quotes")
    private List<YHQuote> quotes;
}
