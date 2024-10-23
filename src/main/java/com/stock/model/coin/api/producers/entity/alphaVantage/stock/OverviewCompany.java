package com.stock.model.coin.api.producers.entity.alphaVantage.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OverviewCompany {
    @JsonProperty("Symbol")
    private String symbol;
    @JsonProperty("AssetType")
    private String assetType;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Exchange")
    private String exchange;
    @JsonProperty("Currency")
    private String currency;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Sector")
    private String sector;
    @JsonProperty("Industry")
    private String industry;
    @JsonProperty("MarketCapitalization")
    private String marketCapitalization;
    @JsonProperty("DividendYield")
    private String dividendYield;
    @JsonProperty("DividendDate")
    private String dividendDate;
    @JsonProperty("ExDividendDate")
    private String exDividendDate;
}
