package com.stock.dto.forCharts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class PieChartData {
    private List<PricesData> data;

    private BigDecimal actualTotalBalance;
}
