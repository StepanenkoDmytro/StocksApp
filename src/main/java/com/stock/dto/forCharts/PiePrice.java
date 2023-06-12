package com.stock.dto.forCharts;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PiePrice {
    private String label;
    private BigDecimal value;

    public PiePrice(String label, BigDecimal value) {
        this.label = label;
        this.value = value;
    }
}
