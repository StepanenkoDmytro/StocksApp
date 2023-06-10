package com.stock.dto.stocks;

import com.stock.api.entity.alphaVantage.stock.OverviewCompany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OverviewCompanyDto {
    private String symbol;
    private String assetType;
    private String name;
    private BigDecimal price;
    private String exchange;
    private String currency;
    private String country;
    private String sector;
    private String industry;
    private String marketCapitalization;
    private BigDecimal dividendYield;
    private String dividendDate;
    private String exDividendDate;

    public static OverviewCompanyDto mapToDto(OverviewCompany company, BigDecimal price) {
        BigDecimal dividendYield;
        if(company.getDividendYield() == null || company.getDividendYield().isEmpty()) {
            dividendYield = BigDecimal.ZERO;
        } else {
            dividendYield = new BigDecimal(company.getDividendYield());
        }

        return new OverviewCompanyDto(
                company.getSymbol(),
                company.getAssetType(),
                company.getName(),
                price,
                company.getExchange(),
                company.getCurrency(),
                company.getCountry(),
                company.getSector(),
                company.getIndustry(),
                company.getMarketCapitalization(),
                dividendYield,
                company.getDividendDate(),
                company.getExDividendDate());
    }
}
