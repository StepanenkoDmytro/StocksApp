package com.stock.api.impl;

import com.stock.api.entity.twelveData.TwelveCompany;
import com.stock.api.wrappers.TwelveDataMarket;
import com.stock.dto.stocks.CompanyDto;
import com.stock.helper.RequestManager;
import com.stock.helper.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpResponse;

@Component
public class TwelveDataMarketImpl implements TwelveDataMarket {
    private static final String TWELVE_DATA_RAPIDAPI_HOST = "twelve-data1.p.rapidapi.com";
    private static final String BASE_URL_TWELVE_DATA_PROFILE = "https://twelve-data1.p.rapidapi.com/profile";
    private final RequestManager requestManager;
    private final ResponseMapper responseMapper;
    @Value("${twelvedata.api.key}")
    private String apiKey;

    @Autowired
    public TwelveDataMarketImpl(RequestManager requestManager, ResponseMapper responseMapper) {
        this.requestManager = requestManager;
        this.responseMapper = responseMapper;
    }

    @Override
    public CompanyDto findCompanyByTicker(String ticker) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_TWELVE_DATA_PROFILE)
                .queryParam("symbol", ticker)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithHeaderXRapidAPIKey(url, apiKey, TWELVE_DATA_RAPIDAPI_HOST);
        System.out.println(response.body());
        TwelveCompany company = responseMapper.convertCustomResponse(response, TwelveCompany.class);
        return CompanyDto.mapTwelveCompany(company);
    }
}
