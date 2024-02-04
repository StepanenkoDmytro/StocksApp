package com.stock.dto.portfolio.spending;

import com.stock.model.portfolio.PortfolioSpending;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpendingDTO {
    private String id;
    private String category;
    private String title;
    private BigDecimal cost;
    private Date date;

    public static PortfolioSpending mapFromDTO(SpendingDTO spendingDTO) {
        return new PortfolioSpending(
                spendingDTO.id,
                spendingDTO.category,
                spendingDTO.title,
                spendingDTO.cost,
                spendingDTO.date);
    }
}
