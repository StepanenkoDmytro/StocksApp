package com.stock.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.dto.CoinDto;
import com.stock.exceptions.RequestException;
import com.stock.api.entity.Coin;
import com.stock.api.wrappers.CoinData;
import com.stock.api.wrappers.CoinWrapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoinMarket {
    private final RequestHelper requestHelper;
    private final ObjectMapper objectMapper;


    public CoinMarket(RequestHelper requestHelper, ObjectMapper objectMapper) {
        this.requestHelper = requestHelper;
        this.objectMapper = objectMapper;
    }

    public List<Coin> findAll(int page) {
        HttpResponse<String> response = requestHelper.sendGetAllRequest(page);
        return processResponse(response, CoinData.class).getData();
    }

    public List<Coin> findByFilter(String filter) {
        HttpResponse<String> response = requestHelper.sendRequestByFilter(filter);
        return processResponse(response, CoinData.class).getData();
    }

    public Coin findByTicker(String ticker) {
        HttpResponse<String> response = requestHelper.sendGetByTicker(ticker);
        return processResponse(response, CoinWrapper.class).getData();
    }

    public List<CoinDto> findByTikersList(List<String> coinsList) {
        HttpResponse<String> response = requestHelper.sendGetCoinsByList(coinsList);
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
