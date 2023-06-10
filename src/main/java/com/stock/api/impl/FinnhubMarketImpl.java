package com.stock.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.api.FinnhubMarket;
import com.stock.api.wrappers.FinnhubWrapper;
import com.stock.exceptions.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class FinnhubMarketImpl implements FinnhubMarket {
    private final ObjectMapper objectMapper;
    private final String BASE_URL_FINNHUB = "https://finnhub.io/api/v1/quote";
    @Value("${finnhub.api.key}")
    private String apiKey;
    private final HttpClient client = HttpClient.newHttpClient();

    @Autowired
    public FinnhubMarketImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public BigDecimal findPriceStockByTicker(String ticker) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL_FINNHUB)
                .queryParam("symbol", ticker)
                .queryParam("token", apiKey);

        HttpResponse<String> response = sendRequest(builder.toUriString());
        FinnhubWrapper data = processResponse(response, FinnhubWrapper.class);
        return data.getCurrentPrice();
    }

    private HttpResponse<String> sendRequest(String url) {
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .headers("Accept-Encoding", "deflate")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RequestException("Unexpected status code: " + response.body());
            }
            return response;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RequestException("Request was interrupted", e);
        } catch (IOException e) {
            throw new RequestException("Failed to retrieve data: " + e.getMessage());
        }
    }

    private <T> T processResponse(HttpResponse<String> response, Class<T> responseType) {
        try {
            return objectMapper.readValue(response.body(), responseType);
        } catch (JsonProcessingException e) {
            throw new RequestException("Failed to process data: " + e.getMessage());
        }
    }
}
