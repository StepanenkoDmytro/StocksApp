package com.stock.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.api.entity.alphaVantage.crypto.TimeSeriesData;
import com.stock.api.entity.alphaVantage.stock.AVCompany;
import com.stock.api.entity.alphaVantage.stock.WeeklyTimeSeries;
import com.stock.api.wrappers.CandlesAlphaVantageData;
import com.stock.api.wrappers.WeeklyDataAlphaVantage;
import com.stock.dto.analytic.DataPrice;
import com.stock.dto.forCharts.CandlesDto;
import com.stock.exceptions.RequestException;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ResponseMapper {
    private final ObjectMapper objectMapper;

    public ResponseMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T convertCustomResponse(HttpResponse<String> response, Class<T> responseType) {
        try {
            return objectMapper.readValue(response.body(), responseType);
        } catch (JsonProcessingException e) {
            throw new RequestException("Failed to process data: " + e.getMessage());
        }
    }

    public List<AVCompany> convertCompaniesResponse(String response) {
        String[] companiesString = response.split("\\r?\\n");
        return Arrays.stream(companiesString, 1, companiesString.length)
                .map(company -> company.split(","))
                .map(AVCompany::mapFromStringArray)
                .toList();
    }

    public List<CandlesDto> convertCandlesResponse(HttpResponse<String> response) {
        List<CandlesDto> result = new ArrayList<>();
        Map<String, TimeSeriesData> data = convertCustomResponse(response, CandlesAlphaVantageData.class).getData();

        List<String> limit100 = data.keySet().stream().limit(300).toList();
        for (String key : limit100) {
            TimeSeriesData candleData = data.get(key);
            Optional<CandlesDto> candleOptional = Optional.ofNullable(candleData)
                    .flatMap(candle -> CandlesDto.mapFromTimeSeriesData(candle, key));
            candleOptional.ifPresent(result::add);
        }
        return result;
    }

    public List<DataPrice> convertDataPriceResponse(HttpResponse<String> response) {
        List<DataPrice> result = new ArrayList<>();
        Map<String, WeeklyTimeSeries> data = convertCustomResponse(response, WeeklyDataAlphaVantage.class).getData();

        for (String key : data.keySet()) {
            WeeklyTimeSeries candleData = data.get(key);
            Optional<DataPrice> candleOptional = Optional.ofNullable(candleData)
                    .flatMap(candle -> DataPrice.mapFromTimeSeriesData(candle, key));
            candleOptional.ifPresent(result::add);
        }
        return result;
    }
}