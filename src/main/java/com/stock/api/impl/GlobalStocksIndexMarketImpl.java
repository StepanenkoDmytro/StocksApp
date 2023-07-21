package com.stock.api.impl;

import com.stock.api.GlobalStocksIndexMarket;
import com.stock.api.entity.globalIndex.IndexData;
import com.stock.api.wrappers.GlobalIndexWrapper;
import com.stock.helper.RequestManager;
import com.stock.helper.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpResponse;
import java.util.List;

@Component
@PropertySource("classpath:security-keys.properties")
public class GlobalStocksIndexMarketImpl implements GlobalStocksIndexMarket {
    private static final String GLOBAL_INDEX_RAPIDAPI_HOST = "global-market-indices-data.p.rapidapi.com";
    private static final String GLOBAL_INDEX_BASE_URL = "https://global-market-indices-data.p.rapidapi.com/v1/index_historic_price";
    private final RequestManager requestManager;
    private final ResponseMapper responseMapper;
    @Value("${global-index.api.key}")
    private String apiKey;

    @Autowired
    public GlobalStocksIndexMarketImpl(RequestManager requestManager, ResponseMapper responseMapper) {
        this.requestManager = requestManager;
        this.responseMapper = responseMapper;
    }

    @Override
    public List<IndexData> getSP500Data() {
        String url = UriComponentsBuilder.fromHttpUrl(GLOBAL_INDEX_BASE_URL)
                .queryParam("index", "SP500")
                .build()
                .toUriString();


        HttpResponse<String> response = requestManager.sendHttpRequestWithHeaderXRapidAPIKey(url, apiKey, GLOBAL_INDEX_RAPIDAPI_HOST);

        GlobalIndexWrapper globalIndexWrapper = responseMapper.convertCustomResponse(response, GlobalIndexWrapper.class);
        return globalIndexWrapper.getData();
    }
}
