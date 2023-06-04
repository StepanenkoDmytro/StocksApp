package com.stock.api.impl;


import com.stock.exceptions.RequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

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
    private static final String BASE_URL = "https://api.coincap.io/v2/assets";
    private final HttpClient client = HttpClient.newHttpClient();
    @Value("${coincap.api.key}")
    private String apiKey;


    public HttpResponse<String> sendGetAllRequest(int page) {
        page = page - 1;
        String url = String.format("%s?limit=%d&offset=%d", BASE_URL, PAGE_LIMIT, page * PAGE_LIMIT);
        return sendRequest(url);
    }

    public HttpResponse<String> sendRequestByFilter(String filter) {
        String url = String.format("%s?search=%s", BASE_URL, filter);
        return sendRequest(url);
    }

    public HttpResponse<String> sendGetByTicker(String ticker) {
        String requestTicker = String.format("%s/%s", BASE_URL, ticker);
        return sendRequest(requestTicker);
    }

    public HttpResponse<String> sendGetCoinsByList(List<String> coinsList) {
        String coins = String.join(",", coinsList);
        String requestTicker = String.format("%s?ids=%s", BASE_URL, coins);
        return sendRequest(requestTicker);
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
