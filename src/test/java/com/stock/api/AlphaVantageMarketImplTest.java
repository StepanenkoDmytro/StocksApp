package com.stock.api;

import com.stock.dto.stocks.CompaniesForClient;
import com.stock.dto.stocks.CompanyDto;
import com.stock.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class AlphaVantageMarketImplTest {
    @Autowired
    private StockService stockService;

//    @Test
//    void getCompanies() {
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .GET()
//                .uri(URI.create("https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=NLQ85O2NIS5PUGC7"))
//                .build();
//
//        HttpResponse<String> response = null;
//        try {
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        String[] companiesString = response.body().split("\\r?\\n");
//
//        List<Company> companies = Arrays.stream(companiesString, 1, companiesString.length)
//                .map(company -> company.split(","))
//                .map(value -> {
//                    Company company = new Company();
//                    company.setSymbol(value[0]);
//                    company.setName(value[1]);
//                    company.setExchange(value[2]);
//                    company.setAssetType(value[3]);
//                    company.setIpoDate(value[4]);
//                    company.setDelistingDate(value[5]);
//                    company.setStatus(value[6]);
//                    return company;
//                })
//                .toList();
//
//    }

//    @Test
//    void find5CompanyByTicker() {
////        List<String> tickers = new ArrayList<>(Arrays.asList("TSLA", "AMD", "PLTR", "SOFI", "NIO"));
//
//        long startTime = System.currentTimeMillis();
////        List<CompanyDto> companies = tickers.stream()
////                .map(ticker -> stockService.getCompanyBySymbol(ticker))
////                .toList();
//        CompaniesForClient losersMovers = stockService.getLosersMovers();
//        List<CompanyDto> companiesLosers = losersMovers.getData();
//
//        CompaniesForClient gainersMovers = stockService.getGainersMovers();
//        List<CompanyDto> companiesGainers = gainersMovers.getData();
//
//        CompaniesForClient activesMovers = stockService.getGainersMovers();
//        List<CompanyDto> companiesActives = activesMovers.getData();
//
//        long endTime = System.currentTimeMillis();
//        long executionTime = endTime - startTime;
//        System.out.println("Execution time to get price and with db: " + executionTime + " ms");
//        companiesLosers.forEach(company -> System.out.println("Name: " + company.getName() + company.getPrice()));
//        companiesGainers.forEach(company -> System.out.println("Name: " + company.getName() + company.getPrice()));
//        companiesActives.forEach(company -> System.out.println("Name: " + company.getName() + company.getPrice()));
//
//        System.out.println("----------------------------------");
//
//        long startTime1 = System.currentTimeMillis();
////        List<CompanyDto> companies = tickers.stream()
////                .map(ticker -> stockService.getCompanyBySymbol(ticker))
////                .toList();
//        CompaniesForClient losersMovers1 = stockService.getLosersMovers();
//        List<CompanyDto> companiesLosers1 = losersMovers1.getData();
//
//        CompaniesForClient gainersMovers1 = stockService.getGainersMovers();
//        List<CompanyDto> companiesGainers1 = gainersMovers1.getData();
//
//        CompaniesForClient activesMovers1 = stockService.getGainersMovers();
//        List<CompanyDto> companiesActives1 = activesMovers1.getData();
//
//        long endTime1 = System.currentTimeMillis();
//        long executionTime1 = endTime1 - startTime1;
//        System.out.println("Execution time to get price and with db: " + executionTime1 + " ms");
//        companiesLosers1.forEach(company -> System.out.println("Name: " + company.getName() + company.getPrice()));
//        companiesGainers1.forEach(company -> System.out.println("Name: " + company.getName() + company.getPrice()));
//        companiesActives1.forEach(company -> System.out.println("Name: " + company.getName() + company.getPrice()));
//    }

    @Test
    void find10CompanyByTicker() {
        List<String> tickers = new ArrayList<>(Arrays.asList(
                "TSLA", "AMD", "PLTR", "SOFI", "NIO", "GOOG", "MSFT", "AAPL", "META", "AMZN"
        ));

        long startTime = System.currentTimeMillis();
        List<CompanyDto> companies = tickers.stream()
                .map(ticker -> stockService.getCompanyBySymbol(ticker))
                .toList();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Execution time: " + executionTime + " ms");
        companies.forEach(System.out::println);
        //Execution time: 2385 ms, вторіе 5 запросов - null
    }
}
