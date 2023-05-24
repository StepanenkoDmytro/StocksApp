package com.stock.service.coincap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.api.wrappers.CoinData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CoinCapTest {

    @Test
    void getAssets() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.coincap.io/v2/assets"))
                .headers("Accept-Encoding", "deflate")
                .headers("Authorization", "Bearer 6f7ad941-76c7-4f38-b473-5a4c76e427ea")
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        CoinData data = objectMapper.readValue(response.body(), CoinData.class);


        System.out.println(data.getData());

    }
}

