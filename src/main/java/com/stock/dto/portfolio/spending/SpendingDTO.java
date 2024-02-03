package com.stock.dto.portfolio.spending;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpendingDTO {
    private String category;
    private String title;
    private BigDecimal cost;
    private Date date;
}
