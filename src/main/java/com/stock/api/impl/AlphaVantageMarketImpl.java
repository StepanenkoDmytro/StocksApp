package com.stock.api.impl;

import com.stock.api.AlphaVantageMarket;
import com.stock.api.entity.alphaVantage.stock.AVCompany;
import com.stock.api.entity.alphaVantage.stock.OverviewCompany;
import com.stock.api.wrappers.QuoteData;
import com.stock.dto.analytic.DataPrice;
import com.stock.dto.forCharts.CandlesDto;
import com.stock.helper.RequestManager;
import com.stock.helper.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.List;

@Component
@PropertySource("classpath:security-keys.properties")
public class AlphaVantageMarketImpl implements AlphaVantageMarket {
    private static final String BASE_URL_ALPHA_VANTAGE = "https://www.alphavantage.co/query";
    private final RequestManager requestManager;
    private final ResponseMapper responseMapper;
    @Value("${alphavantage.api.key}")
    private String apiKey;

    @Autowired
    public AlphaVantageMarketImpl(RequestManager requestManager, ResponseMapper responseMapper) {
        this.requestManager = requestManager;
        this.responseMapper = responseMapper;
    }

    @Override
    public List<CandlesDto> findCandlesById(String coinSymbol) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "DIGITAL_CURRENCY_DAILY")
                .queryParam("symbol", coinSymbol)
                .queryParam("market", "CNY")
                .queryParam("apikey", apiKey)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithParamApiKey(url);
        return responseMapper.convertCandlesResponse(response);
    }

    @Override
    public List<DataPrice> findWeeklyPricesById(String coinSymbol) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "TIME_SERIES_WEEKLY")
                .queryParam("symbol", coinSymbol)
//                .queryParam("market", "CNY")
                .queryParam("apikey", apiKey)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithParamApiKey(url);
        return responseMapper.convertDataPriceResponse(response);
    }

    @Override
    public List<AVCompany> findAllCompanies() {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "LISTING_STATUS")
                .queryParam("apikey", apiKey)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithParamApiKey(url);

        return responseMapper.convertCompaniesResponse(response.body());
    }

    public BigDecimal findStockPriceByTicker(String ticker) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "GLOBAL_QUOTE")
                .queryParam("symbol", ticker)
                .queryParam("apikey", apiKey)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithParamApiKey(url);
        QuoteData data = responseMapper.convertCustomResponse(response, QuoteData.class);
        String price = data.getQuote().getPrice();
        return new BigDecimal(price);
    }

    @Override
    public OverviewCompany findCompanyByTicker(String ticker) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "OVERVIEW")
                .queryParam("symbol", ticker)
                .queryParam("apikey", apiKey)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithParamApiKey(url);
        return responseMapper.convertCustomResponse(response, OverviewCompany.class);
    }
}
