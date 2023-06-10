package com.stock.model.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stock.dto.stocks.OverviewCompanyDto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "account_stocks")
@Data
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AccountStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;
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
    @Column(name = "buy_price")
    private BigDecimal buyPrice;
    @Column(name = "count_stocks")
    private int countStocks;
    @Column(name = "country")
    private String country;
    @Column(name = "sector")
    private String sector;
    @Column(name = "industry")
    private String industry;
    @Column(name = "dividend_yield")
    private BigDecimal dividendYield;

    public static AccountStock fromCompany(OverviewCompanyDto company, int count) {
        AccountStock stock = new AccountStock();
        stock.setSymbol(company.getSymbol());
        stock.setAssetType(company.getAssetType());
        stock.setName(company.getName());
        stock.setExchange(company.getExchange());
        stock.setCurrency(company.getCurrency());
        stock.setBuyPrice(company.getPrice());
        stock.setCountry(company.getCountry());
        stock.setSector(company.getSector());
        stock.setIndustry(company.getIndustry());
        stock.setDividendYield(company.getDividendYield());
        stock.setCountStocks(count);
        return stock;
    }
}
