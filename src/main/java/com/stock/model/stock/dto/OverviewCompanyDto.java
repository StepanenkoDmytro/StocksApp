package com.stock.model.stock.dto;

import com.stock.model.coin.api.producers.entity.alphaVantage.stock.OverviewCompany;
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

    public OverviewCompanyDto() {
    }
    public OverviewCompanyDto(String symbol, String assetType, String name, String exchange, String currency, String country, String sector, String industry, String marketCapitalization, BigDecimal dividendYield, String dividendDate, String exDividendDate) {
        this.symbol = symbol;
        this.assetType = assetType;
        this.name = name;
        this.exchange = exchange;

        this.currency = currency;
        this.country = country;
        this.sector = sector;
        this.industry = industry;
        this.marketCapitalization = marketCapitalization;
        this.dividendYield = dividendYield;
        this.dividendDate = dividendDate;
        this.exDividendDate = exDividendDate;
    }

    public static OverviewCompanyDto mapOverviewCompany(OverviewCompany company) {
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
