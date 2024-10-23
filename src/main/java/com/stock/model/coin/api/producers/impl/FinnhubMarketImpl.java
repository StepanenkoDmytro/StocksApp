package com.stock.model.coin.api.producers.impl;

import com.stock.model.coin.api.producers.FinnhubMarket;
import com.stock.model.coin.api.producers.entity.finnhub.FinnhubCompany;
import com.stock.model.coin.api.producers.wrappers.FinnhubWrapper;
import com.stock.model.stock.dto.CompanyDto;
import com.stock.service.helpers.RequestManager;
import com.stock.service.helpers.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.http.HttpResponse;

@Component
public class FinnhubMarketImpl implements FinnhubMarket {
    private static final String BASE_URL_FINNHUB_QUOTE = "https://finnhub.io/api/v1/quote";
    private static final String BASE_URL_FINNHUB_STOCK = "https://finnhub.io/api/v1/stock/profile";
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
    @Cacheable(value = "getStockPrice", key = "#ticker")
    public BigDecimal findPriceStockByTicker(String ticker) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_FINNHUB_QUOTE)
                .queryParam("symbol", ticker)
                .queryParam("token", apiKey)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithParamApiKey(url);
        FinnhubWrapper data = responseMapper.convertCustomResponse(response, FinnhubWrapper.class);
        return data.getCurrentPrice();
    }

    @Override
    public CompanyDto findCompanyByTicker(String ticker) {
//        System.out.println(apiKey);
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_FINNHUB_STOCK)
                .queryParam("symbol", ticker)
                .queryParam("token", apiKey)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithParamApiKey(url);
        FinnhubCompany company = responseMapper.convertCustomResponse(response, FinnhubCompany.class);
        return CompanyDto.mapFinnhubCompany(company);
    }
}
