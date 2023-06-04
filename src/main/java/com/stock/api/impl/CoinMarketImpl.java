package com.stock.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.api.CoinMarket;
import com.stock.dto.coins.CoinDto;
import com.stock.exceptions.RequestException;
import com.stock.api.entity.Coin;
import com.stock.api.wrappers.CoinData;
import com.stock.api.wrappers.CoinWrapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoinMarketImpl implements CoinMarket {
    private final RequestManager requestManager;
    private final ObjectMapper objectMapper;


    public CoinMarketImpl(RequestManager requestManager, ObjectMapper objectMapper) {
        this.requestManager = requestManager;
        this.objectMapper = objectMapper;
    }

    public List<Coin> findAll(int page) {
        HttpResponse<String> response = requestManager.sendGetAllRequest(page);
        return processResponse(response, CoinData.class).getData();
    }

    public List<Coin> findByFilter(String filter) {
        HttpResponse<String> response = requestManager.sendRequestByFilter(filter);
        return processResponse(response, CoinData.class).getData();
    }

    public Coin findByTicker(String ticker) {
        HttpResponse<String> response = requestManager.sendGetByTicker(ticker);
        return processResponse(response, CoinWrapper.class).getData();
    }

    public List<CoinDto> findByTikersList(List<String> coinsList) {
        HttpResponse<String> response = requestManager.sendGetCoinsByList(coinsList);
        List<Coin> data = processResponse(response, CoinData.class).getData();
        return data.stream()
                .map(CoinDto::mapCoinToDto)
                .collect(Collectors.toList());
    }

    private <T> T processResponse(HttpResponse<String> response, Class<T> responseType){
        try {
            return objectMapper.readValue(response.body(), responseType);
        } catch (JsonProcessingException e) {
            throw new RequestException("Failed to process data: " + e.getMessage());
        }
    }
}
