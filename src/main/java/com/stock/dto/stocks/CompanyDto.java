package com.stock.dto.stocks;

import com.stock.api.entity.alphaVantage.stock.Company;
import com.stock.api.entity.alphaVantage.stock.OverviewCompany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CompanyDto {
    private String symbol;
    private String name;
    private BigDecimal price;
    private String exchange;
    private String assetType;

    public CompanyDto(String symbol, String name, String exchange, String assetType) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = exchange;
        this.assetType = assetType;
    }

    public static CompanyDto mapCompany(Company company) {
        return new CompanyDto(
                company.getSymbol(),
                company.getName(),
                company.getExchange(),
                company.getAssetType());
    }

    public static CompanyDto mapOverviewCompany(OverviewCompanyDto company) {
        return new CompanyDto(
                company.getSymbol(),
                company.getName(),
                company.getPrice(),
                company.getExchange(),
                company.getAssetType());
    }

}
