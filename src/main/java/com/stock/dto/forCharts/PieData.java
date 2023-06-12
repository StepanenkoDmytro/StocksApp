package com.stock.dto.forCharts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class PieData {
    private List<PiePrice> data;
    private BigDecimal totalBalance;
}
