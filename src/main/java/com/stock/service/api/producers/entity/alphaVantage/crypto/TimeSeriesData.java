package com.stock.service.api.producers.entity.alphaVantage.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TimeSeriesData {
    @JsonProperty("1a. open (CNY)")
    private String openCNY;

    @JsonProperty("1b. open (USD)")
    private String openUSD;

    @JsonProperty("2a. high (CNY)")
    private String highCNY;

    @JsonProperty("2b. high (USD)")
    private String highUSD;

    @JsonProperty("3a. low (CNY)")
    private String lowCNY;

    @JsonProperty("3b. low (USD)")
    private String lowUSD;

    @JsonProperty("4a. close (CNY)")
    private String closeCNY;

    @JsonProperty("4b. close (USD)")
    private String closeUSD;

    @JsonProperty("5. volume")
    private String volume;

    @JsonProperty("6. market cap (USD)")
    private String marketCapUSD;
}
