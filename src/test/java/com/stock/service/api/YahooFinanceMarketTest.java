package com.stock.service.api;

import com.stock.service.api.producers.entity.yahooFinance.Mover;
import com.stock.service.api.producers.wrappers.MoversWrapper;
import com.stock.service.exceptions.RequestException;
import com.stock.service.helpers.RequestManager;
import com.stock.service.helpers.ResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@SpringBootTest
class YahooFinanceMarketTest {
    private final RequestManager requestManager;
    private final ResponseMapper responseMapper;
    private final HttpClient client = HttpClient.newHttpClient();

    @Autowired
    public YahooFinanceMarketTest(RequestManager requestManager, ResponseMapper responseMapper) {
        this.requestManager = requestManager;
        this.responseMapper = responseMapper;
    }

    @Test
    void testMapper(){
        String url = UriComponentsBuilder.fromHttpUrl("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-movers")
                .queryParam("region", "US")
                .queryParam("lang", "en-US")
                .queryParam("count", 5)
                .queryParam("start", 0)
                .build()
                .toUriString();

        System.out.println(url);
        HttpResponse<String> response = sendHttpRequestWithHeaderApiKey(url, "poh");
        MoversWrapper moversData = responseMapper.convertCustomResponse(response, MoversWrapper.class);
        List<Mover> result = moversData.getFinance().getResult();
        System.out.println(result);
//        for(Mover mover : result) {
//            System.out.println(mover.getCanonicalName());
//            List<YHQuote> quotes = mover.getQuotes();
//            for(YHQuote quote : quotes) {
//                System.out.println(quote.getSymbol());
//            }
//        }
    }

    public HttpResponse<String> sendHttpRequestWithHeaderApiKey(String url, String apiKey) {
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .headers("Accept-Encoding", "gzip, deflate, br")
                .headers("X-RapidAPI-Key", "8ce1155150mshedb9ae8f466e99cp182d93jsn79a86660c015")
                .headers("X-RapidAPI-Host", "yh-finance.p.rapidapi.com")
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RequestException("Unexpected status code: " + response.statusCode());
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
