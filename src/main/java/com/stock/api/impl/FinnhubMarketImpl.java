package com.stock.api.impl;

import com.stock.api.FinnhubMarket;
import com.stock.api.wrappers.FinnhubWrapper;
import com.stock.helper.RequestManager;
import com.stock.helper.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@Component
public class FinnhubMarketImpl implements FinnhubMarket {
    private static final String BASE_URL_FINNHUB = "https://finnhub.io/api/v1/quote";
    private final RequestManager requestManager;
    private final ResponseMapper responseMapper;
    @Value("${finnhub.api.key}")
    private String apiKey;

    @Autowired
    public FinnhubMarketImpl(RequestManager requestManager, ResponseMapper responseMapper) {
        this.requestManager = requestManager;
        this.responseMapper = responseMapper;
    }

    @Override
    public BigDecimal findPriceStockByTicker(String ticker) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_FINNHUB)
                .queryParam("symbol", ticker)
                .queryParam("token", apiKey)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithParamApiKey(url, apiKey);
        FinnhubWrapper data = responseMapper.convertCustomResponse(response, FinnhubWrapper.class);
        return data.getCurrentPrice();
    }
}
