package com.stock.service.helpers;

import com.stock.dto.analytic.DataPriceShort;
import com.stock.service.api.producers.entity.globalIndex.IndexData;
import com.stock.service.helpers.DataIntervalConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DataIntervalConverterTest {
    private List<IndexData> indexFewMonthDataList;
    private List<IndexData> indexFewMonthDataListWithDiffYears;
    private List<IndexData> indexOneMonthDataList;

    private List<DataPriceShort> expectedForOneMonthDataList;
    private List<DataPriceShort> expectedForFewMonthDataList;
    private List<DataPriceShort> expectedForFewMonthDataLIstWithDiffYears;

    @Test
    void convertDailyToMonthlyData_withEmptyList() {
        List<IndexData> indexDataList = new ArrayList<>();

        List<DataPriceShort> expected = new ArrayList<>();
        List<DataPriceShort> result = DataIntervalConverter.convertDailyToMonthlyData(indexDataList);
        assertIterableEquals(expected, result);
    }

    @Test
    void convertDailyToMonthlyData_withUnsortedData() {
        Collections.shuffle(indexFewMonthDataList);

        List<DataPriceShort> result = DataIntervalConverter.convertDailyToMonthlyData(indexFewMonthDataList);

        expectedForFewMonthDataList.sort(Comparator.comparing(DataPriceShort::getDate));
        result.sort(Comparator.comparing(DataPriceShort::getDate));

        assertIterableEquals(expectedForFewMonthDataList, result);
    }

    @Test
    void convertDailyToMonthlyData_withOneMonth() {
        List<DataPriceShort> result = DataIntervalConverter.convertDailyToMonthlyData(indexOneMonthDataList);

        expectedForOneMonthDataList.sort(Comparator.comparing(DataPriceShort::getDate));
        result.sort(Comparator.comparing(DataPriceShort::getDate));
        assertIterableEquals(expectedForOneMonthDataList, result);
    }

    @Test
    void convertDailyToMonthlyData_withFewMonth() {
        List<DataPriceShort> result = DataIntervalConverter.convertDailyToMonthlyData(indexFewMonthDataList);

        expectedForFewMonthDataList.sort(Comparator.comparing(DataPriceShort::getDate));
        result.sort(Comparator.comparing(DataPriceShort::getDate));
        assertIterableEquals(expectedForFewMonthDataList, result);
    }

    @Test
    void convertDailyToMonthlyData_withFewMonth_andDiffYears() {
        List<DataPriceShort> result = DataIntervalConverter.convertDailyToMonthlyData(indexFewMonthDataListWithDiffYears);

        expectedForFewMonthDataLIstWithDiffYears.sort(Comparator.comparing(DataPriceShort::getDate));
        result.sort(Comparator.comparing(DataPriceShort::getDate));
        assertIterableEquals(expectedForFewMonthDataLIstWithDiffYears, result);
    }

    @BeforeEach
    void init() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        BigDecimal dividendZero = BigDecimal.ZERO;
        indexFewMonthDataList = new ArrayList<>(
                Arrays.asList(
                        new IndexData("01-01-2022", 3400.25),
                        new IndexData("01-02-2022", 3410.15),
                        new IndexData("01-03-2022", 3420.75),
                        new IndexData("01-04-2022", 3430.45),
                        new IndexData("01-05-2022", 3440.80),
                        new IndexData("01-06-2022", 3450.15),
                        new IndexData("01-09-2022", 3445.45),
                        new IndexData("01-10-2022", 3433.95),
                        new IndexData("01-11-2022", 3420.65),
                        new IndexData("01-12-2022", 3425.45),
                        new IndexData("01-13-2022", 3433.25),
                        new IndexData("01-14-2022", 3440.14),
                        new IndexData("01-15-2022", 3442.75),
                        new IndexData("01-21-2022", 3440.85),
                        new IndexData("01-22-2022", 3450.15),
                        new IndexData("01-23-2022", 3461.23),
                        new IndexData("01-24-2022", 3458.95),
                        new IndexData("01-27-2022", 3452.57),
                        new IndexData("02-01-2022", 3450.45),
                        new IndexData("02-02-2022", 3447.18),
                        new IndexData("02-03-2022", 3445.95),
                        new IndexData("02-08-2022", 3453.83),
                        new IndexData("02-07-2022", 3451.75),
                        new IndexData("02-09-2022", 3455.45),
                        new IndexData("02-10-2022", 3457.55),
                        new IndexData("02-11-2022", 3458.11),
                        new IndexData("02-15-2022", 3453.25),
                        new IndexData("02-16-2022", 3449.22),
                        new IndexData("02-17-2022", 3447.45),
                        new IndexData("02-18-2022", 3445.35),
                        new IndexData("02-19-2022", 3443.26),
                        new IndexData("02-23-2022", 3441.15),
                        new IndexData("02-24-2022", 3437.29),
                        new IndexData("02-25-2022", 3438.55),
                        new IndexData("02-26-2022", 3433.78),
                        new IndexData("02-27-2022", 3439.55),
                        new IndexData("03-01-2022", 3442.92),
                        new IndexData("03-02-2022", 3448.35),
                        new IndexData("03-03-2022", 3452.54),
                        new IndexData("03-04-2022", 3454.75),
                        new IndexData("03-05-2022", 3457.80),
                        new IndexData("03-06-2022", 3460.45),
                        new IndexData("03-07-2022", 3462.20),
                        new IndexData("03-10-2022", 3465.15),
                        new IndexData("03-11-2022", 3468.21),
                        new IndexData("03-12-2022", 3469.05),
                        new IndexData("03-13-2022", 3473.53),
                        new IndexData("03-14-2022", 3475.45),
                        new IndexData("03-15-2022", 3477.76),
                        new IndexData("03-16-2022", 3474.25),
                        new IndexData("03-17-2022", 3479.17)
                ));
        expectedForFewMonthDataList = new ArrayList<>(
                Arrays.asList(
                        new DataPriceShort(BigDecimal.valueOf(3400.25), BigDecimal.valueOf(3452.57), dividendZero, LocalDate.parse("01-27-2022", formatter)),
                        new DataPriceShort(BigDecimal.valueOf(3450.45), BigDecimal.valueOf(3439.55), dividendZero, LocalDate.parse("02-27-2022", formatter)),
                        new DataPriceShort(BigDecimal.valueOf(3442.92), BigDecimal.valueOf(3479.17), dividendZero, LocalDate.parse("03-17-2022", formatter))
                )
        );

        indexOneMonthDataList = new ArrayList<>(
                Arrays.asList(
                        new IndexData("01-01-2022", 3400.25),
                        new IndexData("01-02-2022", 3410.15),
                        new IndexData("01-03-2022", 3420.75),
                        new IndexData("01-04-2022", 3430.45),
                        new IndexData("01-05-2022", 3440.80),
                        new IndexData("01-06-2022", 3450.15),
                        new IndexData("01-09-2022", 3445.45),
                        new IndexData("01-10-2022", 3433.95),
                        new IndexData("01-11-2022", 3420.65),
                        new IndexData("01-12-2022", 3425.45),
                        new IndexData("01-13-2022", 3433.25),
                        new IndexData("01-14-2022", 3440.14),
                        new IndexData("01-15-2022", 3442.75),
                        new IndexData("01-21-2022", 3440.85),
                        new IndexData("01-22-2022", 3450.15),
                        new IndexData("01-23-2022", 3461.23),
                        new IndexData("01-24-2022", 3458.95),
                        new IndexData("01-27-2022", 3452.57)
                ));
        expectedForOneMonthDataList = new ArrayList<>(
                List.of(
                        new DataPriceShort(BigDecimal.valueOf(3400.25), BigDecimal.valueOf(3452.57), dividendZero, LocalDate.parse("01-27-2022", formatter))
                )
        );

        indexFewMonthDataListWithDiffYears = new ArrayList<>(indexFewMonthDataList);
        indexFewMonthDataListWithDiffYears.addAll(
                Arrays.asList(
                        new IndexData("01-01-2023", 3500.25),
                        new IndexData("01-02-2023", 3510.15),
                        new IndexData("01-03-2023", 3520.75),
                        new IndexData("01-04-2023", 3530.45),
                        new IndexData("01-05-2023", 3540.80),
                        new IndexData("01-06-2023", 3550.15),
                        new IndexData("01-09-2023", 3545.45),
                        new IndexData("01-10-2023", 3533.95),
                        new IndexData("01-11-2023", 3520.65),
                        new IndexData("01-12-2023", 3525.45),
                        new IndexData("01-13-2023", 3533.25),
                        new IndexData("01-14-2023", 3540.14),
                        new IndexData("01-15-2023", 3542.75),
                        new IndexData("01-21-2023", 3540.85),
                        new IndexData("01-22-2023", 3550.15),
                        new IndexData("01-23-2023", 3561.23),
                        new IndexData("01-24-2023", 3558.95),
                        new IndexData("01-27-2023", 3552.57)
                )
        );

        expectedForFewMonthDataLIstWithDiffYears = new ArrayList<>(
                Arrays.asList(
                        new DataPriceShort(BigDecimal.valueOf(3500.25), BigDecimal.valueOf(3552.57), dividendZero, LocalDate.parse("01-27-2023", formatter)),
                        new DataPriceShort(BigDecimal.valueOf(3400.25), BigDecimal.valueOf(3452.57), dividendZero, LocalDate.parse("01-27-2022", formatter)),
                        new DataPriceShort(BigDecimal.valueOf(3450.45), BigDecimal.valueOf(3439.55), dividendZero, LocalDate.parse("02-27-2022", formatter)),
                        new DataPriceShort(BigDecimal.valueOf(3442.92), BigDecimal.valueOf(3479.17), dividendZero, LocalDate.parse("03-17-2022", formatter))
                )
        );
    }
}
