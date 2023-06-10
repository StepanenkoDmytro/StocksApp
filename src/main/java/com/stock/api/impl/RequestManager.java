package com.stock.api.impl;


import com.stock.exceptions.RequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@PropertySource("classpath:security-keys.properties")
@Component
public class RequestManager {
    public static final int MAX_ELEMENTS = 1080;
    public static final int PAGE_LIMIT = 9;
    public static final int MAX_PAGES = MAX_ELEMENTS / PAGE_LIMIT;
    private static final String BASE_URL_FOR_ASSETS = "https://api.coincap.io/v2/assets";
    private static final String BASE_URL_FOR_CANDLES = "https://api.coincap.io/v2/candles";
    private final HttpClient client = HttpClient.newHttpClient();
    @Value("${coincap.api.key}")
    private String apiKey;


    public HttpResponse<String> sendGetAllRequest(int page) {
        page = page - 1;
        String url = String.format("%s?limit=%d&offset=%d", BASE_URL_FOR_ASSETS, PAGE_LIMIT, page * PAGE_LIMIT);
        return sendRequest(url);
    }

    public HttpResponse<String> sendRequestByFilter(String filter) {
        String url = String.format("%s?search=%s", BASE_URL_FOR_ASSETS, filter);
        return sendRequest(url);
    }

    public HttpResponse<String> sendGetByTicker(String ticker) {
        String requestTicker = String.format("%s/%s", BASE_URL_FOR_ASSETS, ticker);
        return sendRequest(requestTicker);
    }

    public HttpResponse<String> sendGetCoinsByList(List<String> coinsList) {
        String coins = String.join(",", coinsList);
        String requestTicker = String.format("%s?ids=%s", BASE_URL_FOR_ASSETS, coins);
        return sendRequest(requestTicker);
    }

    public HttpResponse<String> sendGetCandlesForBaseAndQuoteCoins(String baseID, String quoteID){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL_FOR_CANDLES)
                .queryParam("exchange", "binance")
                .queryParam("interval", "h8")
                .queryParam("baseId", baseID)
                .queryParam("quoteId", quoteID);
        return sendRequest(builder.toUriString());
    }

    private HttpResponse<String> sendRequest(String url) {
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .headers("Accept-Encoding", "deflate")
                .headers("Authorization", apiKey)
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
}
