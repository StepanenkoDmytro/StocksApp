package com.stock.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PieCoinsPrice {
    private String label;
    private BigDecimal value;

    public PieCoinsPrice(String label, BigDecimal value) {
        this.label = label;
        this.value = value;
    }
}
