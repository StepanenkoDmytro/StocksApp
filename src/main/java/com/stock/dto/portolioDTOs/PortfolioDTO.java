package com.stock.dto.portolioDTOs;

import com.stock.dto.accountDtos.AccountCoinDto;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.dto.portfolio.spending.SpendingDTO;
import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.model.portfolio.Portfolio;
import com.stock.model.portfolio.PortfolioSpending;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
