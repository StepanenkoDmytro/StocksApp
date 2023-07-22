package com.stock.service.api.producers.entity.finnhub;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FinnhubCompany {
    @JsonProperty("country")
    private String country;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("ipo")
    private String ipo;
    @JsonProperty("marketCapitalization")
    private String marketCapitalization;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ticker")
    private String ticker;
    @JsonProperty("finnhubIndustry")
    private String finnhubIndustry;
    @JsonProperty("logo")
    private String logo;
}
