package com.stock.api.impl;

import com.stock.api.FearGreedIndexMarket;
import com.stock.api.entity.FearGreedIndex.FearGreedIndex;
import com.stock.api.wrappers.FearGreedIndexWrapper;
import com.stock.dto.coins.FearGreedIndexDto;
import com.stock.helper.RequestManager;
import com.stock.helper.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpResponse;
import java.util.List;

import static java.lang.Integer.parseInt;

@Component
public class FearGreedIndexMarketImpl implements FearGreedIndexMarket {
    private final RequestManager requestManager;
    private final ResponseMapper responseMapper;

    @Autowired
    public FearGreedIndexMarketImpl(RequestManager requestManager, ResponseMapper responseMapper) {
        this.requestManager = requestManager;
        this.responseMapper = responseMapper;
    }

    @Override
    public FearGreedIndexDto getFearGreedIndex() {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.alternative.me/fng/")
                .queryParam("limit",31)
                .build()
                .toUriString();

        HttpResponse<String> response = requestManager.sendHttpRequestWithHeaderXRapidAPIKey(url, "201e3351admsh63f9a520db137aap1b54c7jsnaa5e16071bcd", "fear-and-greed-index.p.rapidapi.com");
//        System.out.println(response.body());
        FearGreedIndexWrapper fearGreedIndexWrapper = responseMapper.convertCustomResponse(response, FearGreedIndexWrapper.class);
        List<FearGreedIndex> data = fearGreedIndexWrapper.getData();

        return new FearGreedIndexDto(
                parseInt(data.get(0).getValue()),
                parseInt(data.get(1).getValue()),
                parseInt(data.get(7).getValue()),
                parseInt(data.get(30).getValue())
        );
    }
}
