package com.stock.service.api.producers.impl;

import com.stock.service.api.producers.GlobalStocksIndexMarket;
import com.stock.service.api.producers.wrappers.GlobalIndexWrapper;
import com.stock.dto.analytic.DataPriceShort;
import com.stock.service.helpers.DataIntervalConverter;
import com.stock.service.helpers.RequestManager;
import com.stock.service.helpers.ResponseMapper;
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
    public List<DataPriceShort> getSP500Data() {
        String url = UriComponentsBuilder.fromHttpUrl(GLOBAL_INDEX_BASE_URL)
                .queryParam("index", "SP500")
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithHeaderXRapidAPIKey(url, apiKey, GLOBAL_INDEX_RAPIDAPI_HOST);

        GlobalIndexWrapper globalIndexWrapper = responseMapper.convertCustomResponse(response, GlobalIndexWrapper.class);
        return DataIntervalConverter.convertDailyToMonthlyData(globalIndexWrapper.getData());
    }
}
