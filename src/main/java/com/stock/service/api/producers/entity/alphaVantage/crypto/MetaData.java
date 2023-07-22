package com.stock.service.api.producers.entity.alphaVantage.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MetaData {
    @JsonProperty("1. Information")
    private String information;

    @JsonProperty("2. Digital Currency Code")
    private String digitalCurrencyCode;

    @JsonProperty("3. Digital Currency Name")
    private String digitalCurrencyName;

    @JsonProperty("4. Market Code")
    private String marketCode;

    @JsonProperty("5. Market Name")
    private String marketName;

    @JsonProperty("6. Last Refreshed")
    private String lastRefreshed;

    @JsonProperty("7. Time Zone")
    private String timeZone;
}
