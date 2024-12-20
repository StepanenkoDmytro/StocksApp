package com.stock.dto.accountDtos;

import com.stock.model.user.account.entities.AccountStock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStockDto {
    private String symbol;
    private String assetType;
    private String name;
    private String currency;
    private BigDecimal buyPrice;
    private int countStocks;
    private String sector;
    private BigDecimal dividendYield;

    public static AccountStockDto mapToDto(AccountStock stock) {
        return new AccountStockDto(
                stock.getSymbol(),
                stock.getAssetType(),
                stock.getName(),
                stock.getCurrency(),
                stock.getBuyPrice(),
                stock.getCountStocks(),
                stock.getSector(),
                stock.getDividendYield()
        );
    }
}
