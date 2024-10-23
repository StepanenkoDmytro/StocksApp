package com.stock.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.model.coin.api.producers.wrappers.CoinData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

@SpringBootTest
public class CoinCapMarketImplTest {

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
    @Test
    void fearIndex() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://fear-and-greed-index.p.rapidapi.com/v1/fgi"))
                .header("X-RapidAPI-Key", "8ce1155150mshedb9ae8f466e99cp182d93jsn79a86660c015")
                .header("X-RapidAPI-Host", "fear-and-greed-index.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());
    }
}

