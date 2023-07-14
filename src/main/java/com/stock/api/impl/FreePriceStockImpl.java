package com.stock.api.impl;

import com.stock.api.FreePriceStock;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class FreePriceStockImpl implements FreePriceStock {
//    private static final String BASE_URL_FINNHUB_QUOTE = "https://finnhub.io/api/v1/quote";
    @Override
    public BigDecimal findPriceStockByTicker(String ticker) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://get-live-stock-price-by-symbol.p.rapidapi.com/api/stocks?input=AAPL"))
                .header("X-RapidAPI-Key", "8ce1155150mshedb9ae8f466e99cp182d93jsn79a86660c015")
                .header("X-RapidAPI-Host", "get-live-stock-price-by-symbol.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new BigDecimal(response.body());
    }
}
