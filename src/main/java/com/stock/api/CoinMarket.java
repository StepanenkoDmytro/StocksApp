package com.stock.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.exceptions.RequestException;
import com.stock.api.entity.Coin;
import com.stock.api.wrappers.CoinData;
import com.stock.api.wrappers.CoinWrapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;

@Component
public class CoinMarket {
    private final RequestHelper requestHelper;

    public CoinMarket(RequestHelper requestHelper) {
        this.requestHelper = requestHelper;
    }

    public List<Coin> findAll() {
        HttpResponse<String> response = requestHelper.sendGetAllRequest();

        ObjectMapper objectMapper = new ObjectMapper();
        CoinData data;
        try {
            data = objectMapper.readValue(response.body(), CoinData.class);
        } catch (JsonProcessingException e) {
            throw new RequestException("Failed to process data: " + e.getMessage());
        }
        return data.getData();
    }

    public Coin findByTicker(String ticker) {
        HttpResponse<String> response = requestHelper.sendGetByTicker(ticker);

        ObjectMapper objectMapper = new ObjectMapper();
        CoinWrapper data;
        try {
            data = objectMapper.readValue(response.body(), CoinWrapper.class);
        } catch (JsonProcessingException e) {
            throw new RequestException("Failed to process data: " + e.getMessage());
        }
        return data.getData();
    }
}
