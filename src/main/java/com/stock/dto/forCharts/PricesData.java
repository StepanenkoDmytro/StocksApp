package com.stock.dto.forCharts;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PricesData {
    private String label;
    private BigDecimal value;

    public PricesData(String label, BigDecimal value) {
        this.label = label;
        this.value = value;
    }
}
