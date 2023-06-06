package com.stock.dto.forCharts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class PieCoinsData {
    private List<PieCoinPrice> pieCoins;
    private BigDecimal totalBalance;
}
