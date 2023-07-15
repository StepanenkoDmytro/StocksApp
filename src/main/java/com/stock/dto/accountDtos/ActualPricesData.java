package com.stock.dto.accountDtos;

import com.stock.dto.forCharts.PricesData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ActualPricesData {
    private List<PricesData> data;
}
