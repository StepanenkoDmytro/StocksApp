package com.stock.analytic;

import com.stock.api.AlphaVantageMarket;
import com.stock.dto.analytic.DataPrice;
import com.stock.dto.forCharts.CandlesDto;
import com.stock.model.account.Account;
import com.stock.model.account.AccountStock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@SpringBootTest
class PortfolioAnalyticTest {
    @Autowired
    private AlphaVantageMarket alphaVantageMarket;

    @Test
    void getAnaliz() {
        //рахується тестове значення для портфелю з КО, TESLA, IBM
        //капіталізація КО = 257_398_997_000
        //капіталізація TESLA = 855_099_376_000
        //капіталізація IBM = 122_077_569_000
        //середня капіталзація портфелю складає 1_234_575_942_000
        //середня вага КО = 0.2086
        //середня вага TESLA = 0.6928
        //середня вага IBM = 0.0989
//        double tsla = getStandardDeviationsByTicker("TSLA");
//        System.out.println("Standard Deviation for TSLA: " + tsla);
//        double standardDeviation = Math.sqrt(ko*ko*0.2086*0.2086 + tsla*tsla*0.6928*0.2086 + ibm*ibm*0.0989*0.0989);

        double ko = getStandardDeviationsByTicker("KO");
        double ibm = getStandardDeviationsByTicker("IBM");
        System.out.println("Standard Deviation for KO: " + ko);
        System.out.println("Standard Deviation for IBM: " + ibm);
        //рахується тестове значення ризикованості для портфелю з КО, IBM (Standard Deviation)
        //капіталізація КО = 257_398_997_000
        //капіталізація IBM = 122_077_569_000
        //середня капіталзація портфелю складає 379_476_566_000
        //середня вага КО = 0.6784
        //середня вага IBM = 0.3216
        double standardDeviation = Math.sqrt(ko * ko * 0.6784 * 0.6784 + ibm * ibm * 0.3216 * 0.3216);
        System.out.println(standardDeviation);
    }

    //129,01 + 142370,129 + 172,68
    private double getStandardDeviationsByTicker(String ticker) {
        List<DataPrice> prices = alphaVantageMarket.findWeeklyPricesById(ticker);
        LocalDate limit = LocalDate.now().minusYears(5);

        List<Double> doubles = prices.stream()
                .filter(week -> week.getDate().isAfter(limit))
                .map(DataPrice::getClose)
                .map(BigDecimal::doubleValue)
                .toList();

        double prevElement = doubles.get(0);
        double sum = doubles.get(0);
        List<Double> differenceList = new ArrayList<>();
        for (int i = 1; i < doubles.size(); i++) {
            double currentElement = doubles.get(i);
            sum += currentElement;
            double difference = (currentElement - prevElement) / prevElement;
            differenceList.add(difference);

            prevElement = currentElement;
        }

        double averageValue = sum / differenceList.size();

        double average = differenceList.stream()
                .map(value -> value - averageValue)
                .map(value -> value * value)
                .mapToDouble(value -> value)
                .average()
                .orElse(0.0);

        return Math.sqrt(average);
    }
}
