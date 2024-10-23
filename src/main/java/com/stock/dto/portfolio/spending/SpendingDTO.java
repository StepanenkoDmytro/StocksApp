package com.stock.dto.portfolio.spending;

import com.stock.model.user.portfolio.entities.PortfolioSpending;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpendingDTO {
    private String id;
    private Boolean isSaved;
    private String category;
    private String comment;
    private BigDecimal cost;
    private Date date;

    public static PortfolioSpending mapFromDTO(SpendingDTO spending) {
        return new PortfolioSpending(
                spending.id,
                spending.category,
                spending.comment,
                spending.cost,
                spending.date
        );
    }

    public static SpendingDTO mapToDTO(PortfolioSpending spending) {
        return new SpendingDTO(
                spending.getId(),
                true,
                spending.getCategoryId(),
                spending.getComment(),
                spending.getCost(),
                spending.getDate()
        );
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
