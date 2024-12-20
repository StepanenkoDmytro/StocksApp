package com.stock.model.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CompanyDtoWithPrice {
    private String symbol;
    private String name;
    private String assetType;
    private BigDecimal price;

    public CompanyDtoWithPrice(String symbol, String name, String assetType) {
        this.symbol = symbol;
        this.name = name;
        this.assetType = assetType;
    }

    public static CompanyDtoWithPrice mapCompany(CompanyDto companyDto) {
        String assetType = "Stock";
        return new CompanyDtoWithPrice(
                companyDto.getSymbol(),
                companyDto.getName(),
                assetType);
    }
}
