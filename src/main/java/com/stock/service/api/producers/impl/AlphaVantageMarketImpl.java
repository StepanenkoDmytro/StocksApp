package com.stock.service.api.producers.impl;

import com.stock.service.api.producers.AlphaVantageMarket;
import com.stock.service.api.producers.entity.alphaVantage.stock.AVCompany;
import com.stock.service.api.producers.entity.alphaVantage.stock.OverviewCompany;
import com.stock.service.api.producers.wrappers.QuoteData;
import com.stock.dto.analytic.DataPriceShort;
import com.stock.dto.forCharts.CandlesDto;
import com.stock.service.helpers.RequestManager;
import com.stock.service.helpers.ResponseMapper;
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
    public List<DataPriceShort> findWeeklyPricesById(String ticker) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "TIME_SERIES_WEEKLY")
                .queryParam("symbol", ticker)
                .queryParam("apikey", apiKey)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithParamApiKey(url);
        return responseMapper.convertDataPriceResponseByWeekly(response);
    }

    @Override
    public List<DataPriceShort> findMonthlyPricesById(String ticker) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "TIME_SERIES_MONTHLY_ADJUSTED")
                .queryParam("symbol", ticker)
                .queryParam("apikey", apiKey)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithParamApiKey(url);
        return responseMapper.convertDataPriceResponseByMonthly(response);
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
