package com.stock.helpers;


import com.stock.exceptions.RequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@PropertySource("classpath:api_keys.properties")
@Component
public class RequestHelper {
    private static final String BASE_URL = "https://api.coincap.io/v2/assets";
    private final HttpClient client = HttpClient.newHttpClient();
    @Value("${coincap.api.key}")
    private String API_KEY;

    public HttpResponse<String> sendGetAllRequest() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL))
                .headers("Accept-Encoding", "deflate")
                .headers("Authorization", API_KEY)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RequestException("Failed to retrieve data: " + response.body());
            }
            return response;
        } catch (IOException | InterruptedException e) {
            throw new RequestException("Failed to retrieve data: " + e.getMessage());
        }
    }

    public HttpResponse<String> sendGetByTicker(String ticker) {
        String requestTicker = "/" + ticker;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + requestTicker))
                .headers("Accept-Encoding", "deflate")
                .headers("Authorization", API_KEY)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RequestException(
                        String.format("Failed to retrieve coin: %s %s", ticker, response.body()));
            }
            return response;
        } catch (IOException | InterruptedException e) {
            throw new RequestException(
                    String.format("Failed to retrieve coin: %s %s", ticker, e.getMessage()));
        }
    }
}
