package com.stock.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.api.AlphaVantageMarket;
import com.stock.api.entity.alphaVantage.crypto.TimeSeriesData;
import com.stock.api.entity.alphaVantage.stock.Company;
import com.stock.api.entity.alphaVantage.stock.OverviewCompany;
import com.stock.api.wrappers.CandlesAlphaVantageData;
import com.stock.api.wrappers.QuoteData;
import com.stock.dto.forCharts.CandlesDto;
import com.stock.exceptions.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:security-keys.properties")
public class AlphaVantageMarketImpl implements AlphaVantageMarket {
    private final RequestManager requestManager;
    private final ObjectMapper objectMapper;
    private final String BASE_URL_ALPHA_VANTAGE = "https://www.alphavantage.co/query";
    @Value("${alphavantage.api.key}")
    private String apiKey;
    private final HttpClient client = HttpClient.newHttpClient();

    @Autowired
    public AlphaVantageMarketImpl(RequestManager requestManager, ObjectMapper objectMapper) {
        this.requestManager = requestManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<CandlesDto> findCandlesById(String coinSymbol) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "DIGITAL_CURRENCY_DAILY")
                .queryParam("symbol", coinSymbol)
                .queryParam("market", "CNY")
                .queryParam("apikey", apiKey);

        HttpResponse<String> response = sendRequest(builder.toUriString());

        List<CandlesDto> result = new ArrayList<>();
        Map<String, TimeSeriesData> data = processResponse(response, CandlesAlphaVantageData.class).getData();
        try {
            List<String> limit100 = data.keySet().stream().limit(300).toList();
            for (int i = 0; i < limit100.size(); i++) {
                TimeSeriesData candleData = data.get(limit100.get(i));
                CandlesDto candle = CandlesDto.mapFromTimeSeriesData(candleData, limit100.get(i));
                result.add(candle);
            }
            return result;
        } catch (NullPointerException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Company> findAllCompanies() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "LISTING_STATUS")
                .queryParam("apikey", apiKey);

        HttpResponse<String> response = sendRequest(builder.toUriString());

        return mapResponseToCompanies(response.body());
    }

    public BigDecimal findStockPriceByTicker(String ticker) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "GLOBAL_QUOTE")
                .queryParam("symbol", ticker)
                .queryParam("apikey", apiKey);

        HttpResponse<String> response = sendRequest(builder.toUriString());
        System.out.println(response.body());
        QuoteData data = processResponse(response, QuoteData.class);
        String price = data.getQuote().getPrice();
        return new BigDecimal(price);
    }

    @Override
    public OverviewCompany findCompanyByTicker(String ticker) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL_ALPHA_VANTAGE)
                .queryParam("function", "OVERVIEW")
                .queryParam("symbol", ticker)
                .queryParam("apikey", apiKey);

        HttpResponse<String> response = sendRequest(builder.toUriString());
        OverviewCompany overviewCompany = processResponse(response, OverviewCompany.class);
        return overviewCompany;
    }


    private List<Company> mapResponseToCompanies(String response) {
        String[] companiesString = response.split("\\r?\\n");
        return Arrays.stream(companiesString, 1, companiesString.length)
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
