package com.stock.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.api.entity.alphaVantage.stock.Company;
import com.stock.api.wrappers.CoinData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AlphaVantageMarketImplTest {

    @Test
    void getCompanies() {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=NLQ85O2NIS5PUGC7"))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String[] companiesString = response.body().split("\\r?\\n");
//        for (String str : companiesString) {
//            System.out.println(str);
//        }
        List<Company> companies = Arrays.stream(companiesString, 1, companiesString.length)
                .map(company -> company.split(","))
                .map(value -> {
                    Company company = new Company();
                    company.setSymbol(value[0]);
                    company.setName(value[1]);
                    company.setExchange(value[2]);
                    company.setAssetType(value[3]);
                    company.setIpoDate(value[4]);
                    company.setDelistingDate(value[5]);
                    company.setStatus(value[6]);
                    return company;
                })
                .toList();

        System.out.println(companies);
    }
}
