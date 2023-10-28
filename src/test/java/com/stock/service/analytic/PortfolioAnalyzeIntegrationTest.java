//package com.stock.service.analytic;
//
//import com.stock.dto.analytic.DataPriceShort;
//import com.stock.service.api.producers.AlphaVantageMarket;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.temporal.TemporalAdjusters;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//@SpringBootTest
//class PortfolioAnalyzeIntegrationTest {
//    @Autowired
//    private AlphaVantageMarket alphaVantageMarket;
//
//    @Test
//    void getAnalyze() {
//        //рахується тестове значення для портфелю з КО, TESLA, IBM
//        //капіталізація КО = 257_398_997_000
//        //капіталізація TESLA = 855_099_376_000
//        //капіталізація IBM = 122_077_569_000
//        //середня капіталзація портфелю складає 1_234_575_942_000
//        //середня вага КО = 0.2086
//        //середня вага TESLA = 0.6928
//        //середня вага IBM = 0.0989
////        double tsla = getStandardDeviationsByTicker("TSLA");
////        System.out.println("Standard Deviation for TSLA: " + tsla);
////        double standardDeviation = Math.sqrt(ko*ko*0.2086*0.2086 + tsla*tsla*0.6928*0.2086 + ibm*ibm*0.0989*0.0989);
//
//        double ko = getStandardDeviationsByTicker("KO");
//        double ibm = getStandardDeviationsByTicker("IBM");
//        System.out.println("Standard Deviation for KO: " + ko);
//        System.out.println("Standard Deviation for IBM: " + ibm);
//        //рахується тестове значення ризикованості для портфелю з КО, IBM (Standard Deviation)
//        //капіталізація КО = 257_398_997_000
//        //капіталізація IBM = 122_077_569_000
//        //середня капіталзація портфелю складає 379_476_566_000
//        //середня вага КО = 0.6784
//        //середня вага IBM = 0.3216
////        double standardDeviation = Math.sqrt(ko * ko * 0.6784 * 0.6784 + ibm * ibm * 0.3216 * 0.3216);
////        System.out.println(standardDeviation);
//    }
//
//    //129,01 + 142370,129 + 172,68
//    private double getStandardDeviationsByTicker(String ticker) {
//        List<DataPriceShort> prices = alphaVantageMarket.findMonthlyPricesById(ticker);
//
//        LocalDate limit = LocalDate.now().minusYears(1);
//        System.out.println(prices.stream().filter(week -> week.getDate().isAfter(limit)).toList());
//        List<Double> doubles = prices.stream()
//                .filter(week -> week.getDate().isAfter(limit))
//                .map(DataPriceShort::getClose)
//                .map(BigDecimal::doubleValue)
//                .toList();
//
//        double prevElement = doubles.get(0);
////        double sum = doubles.get(0);
//        List<Double> differenceList = new ArrayList<>();
//        for (int i = 1; i < doubles.size(); i++) {
//            double currentElement = doubles.get(i);
////            sum += currentElement;
//            double difference = ((currentElement - prevElement) / prevElement) * 100;
////            double difference = (currentElement - prevElement) / prevElement;
//            differenceList.add(difference);
//
//            prevElement = currentElement;
//        }
//
////        double averageValue = sum / differenceList;
//
////        double average = differenceList.stream()
////                .map(value -> value - averageValue)
////                .map(value -> value * value)
////                .mapToDouble(value -> value)
////                .average()
////                .orElse(0.0);
////
////        return Math.sqrt(average);
//        double sum = differenceList.stream()
//                .mapToDouble(Double::doubleValue)
//                .sum();
//        return sum / differenceList.size();
//    }
//
//    @Test
//    void dividendsPredict() {
//        List<DataPriceShort> ko = alphaVantageMarket.findMonthlyPricesById("KO");
//        Map<Integer, Double> dividendDataLastYear = new TreeMap<>();
//
//        LocalDate now = LocalDate.now();
//
//        LocalDate startLastYear = now.with(TemporalAdjusters.firstDayOfYear()).minusYears(1);
//        LocalDate endLastYear = startLastYear.plusYears(1);
//
//        for(DataPriceShort data : ko) {
//            LocalDate date = data.getDate();
//            if(date.isAfter(startLastYear) && date.isBefore(endLastYear)) {
//                double div = data.getDividendAmount().doubleValue();
//                if (div != 0) {
//                    dividendDataLastYear.put(date.getMonthValue(), div);
//                }
//            }
//        }
//
//        int currentMonth = LocalDate.now().getMonthValue();
//        double predict = 0.0;
//
//        for(Integer month : dividendDataLastYear.keySet()) {
//            if(month >= currentMonth) {
//
//                predict += dividendDataLastYear.get(month);
//            }
//        }
//        System.out.println(predict);
//    }
//}