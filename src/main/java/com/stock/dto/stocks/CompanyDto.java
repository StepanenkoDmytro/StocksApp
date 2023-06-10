package com.stock.dto.stocks;

import com.stock.api.entity.alphaVantage.stock.Company;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CompanyDto {
    private String symbol;
    private String name;
    private String exchange;
    private String assetType;

    public static CompanyDto mapCompany(Company company) {
        return new CompanyDto(
                company.getSymbol(),
                company.getName(),
                company.getExchange(),
                company.getAssetType());
    }
}
