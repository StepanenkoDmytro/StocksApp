package com.stock.dto.portolioDTOs;

import com.stock.dto.portfolio.spending.SpendingDTO;
import com.stock.model.user.portfolio.entities.Portfolio;
import com.stock.model.user.portfolio.entities.PortfolioSpending;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDTO {
    private Long id;
    private List<SpendingDTO> spendings;

    public static PortfolioDTO mapPortfolio(Portfolio portfolio) {
        return new PortfolioDTO(
                portfolio.getId(),
                mapPortfolioSpendings(portfolio.getSpendingsList())
        );
    }

    private static List<SpendingDTO> mapPortfolioSpendings(List<PortfolioSpending> spendings) {
        if (spendings == null) {
            return new ArrayList<>();
        }
        return spendings.stream()
                .map(SpendingDTO::mapToDTO)
                .toList();
    }
}
