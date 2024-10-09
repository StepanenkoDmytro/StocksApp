package com.stock.model.stock;

import com.stock.service.api.producers.entity.alphaVantage.stock.OverviewCompany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "asset_type")
    private String assetType;
    @Column(name = "name")
    private String name;
    @Column(name = "exchange")
    private String exchange;
    @Column(name = "currency")
    private String currency;
    @Column(name = "country")
    private String country;
    @Column(name = "sector")
    private String sector;
    @Column(name = "industry")
    private String industry;
    @Column(name = "dividend_yield")
    private BigDecimal dividendYield;
    @Column(name = "market_capitalization")
    private BigDecimal marketCapitalization;
    @Column(name = "updated")
    @LastModifiedDate
    private Date updated;

    public Company(String symbol, String assetType, String name, String exchange, String currency, String country, String sector, String industry, BigDecimal dividendYield, BigDecimal marketCapitalization) {
        this.symbol = symbol;
        this.assetType = assetType;
        this.name = name;
        this.exchange = exchange;
        this.currency = currency;
        this.country = country;
        this.sector = sector;
        this.industry = industry;
        this.dividendYield = dividendYield;
        this.marketCapitalization = marketCapitalization;
    }

    public static Company mapOverviewCompany(OverviewCompany company) {
        BigDecimal companyDividend;

        if(company.getDividendYield() == null || company.getDividendYield().equals("None")) {
            companyDividend = BigDecimal.ZERO;
        } else {
            companyDividend = new BigDecimal(company.getDividendYield());
        }
        BigDecimal marketCapitalization = new BigDecimal(company.getMarketCapitalization());
        return new Company(
                company.getSymbol(),
                company.getAssetType(),
                company.getName(),
                company.getExchange(),
                company.getCurrency(),
                company.getCountry(),
                company.getSector(),
                company.getIndustry(),
                companyDividend,
                marketCapitalization
        );
    }
}
