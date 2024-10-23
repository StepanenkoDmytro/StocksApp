package com.stock.model.coin.api.producers.impl;

import com.stock.model.coin.api.producers.YHFinanceMarket;
import com.stock.model.coin.api.producers.entity.yahooFinance.Mover;
import com.stock.model.coin.api.producers.wrappers.MoversWrapper;
import com.stock.service.helpers.RequestManager;
import com.stock.service.helpers.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpResponse;
import java.util.List;

@Component
public class YHFinanceMarketImpl implements YHFinanceMarket {
    private static final String YAHOO_RAPIDAPI_HOST = "yh-finance.p.rapidapi.com";
    private static final String BASE_URL_YAHOO = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-movers";
    private final RequestManager requestManager;
    private final ResponseMapper responseMapper;
    @Value("${yahoofinance.api.key}")
    private String apiKey;

    @Autowired
    public YHFinanceMarketImpl(RequestManager requestManager, ResponseMapper responseMapper) {
        this.requestManager = requestManager;
        this.responseMapper = responseMapper;
    }

    @Override
    @Cacheable(value = "getMovers")
    public List<Mover> getMovers() {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_YAHOO)
                .queryParam("region", "US")
                .queryParam("lang", "en-US")
                .queryParam("count", 5)
                .queryParam("start", 0)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithHeaderXRapidAPIKey(url, apiKey, YAHOO_RAPIDAPI_HOST);
        MoversWrapper moversData = responseMapper.convertCustomResponse(response, MoversWrapper.class);
        System.out.println(moversData.getFinance().getResult());
        return moversData.getFinance().getResult();
    }


}
