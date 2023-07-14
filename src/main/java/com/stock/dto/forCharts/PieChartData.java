package com.stock.dto.forCharts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PieChartData {
    private List<PiePrice> data;
}
