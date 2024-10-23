package com.stock.model.stock.dto;

import com.stock.model.coin.api.producers.entity.alphaVantage.stock.AVCompany;
import com.stock.model.coin.api.producers.entity.finnhub.FinnhubCompany;
import com.stock.model.coin.api.producers.entity.twelveData.TwelveCompany;
import com.stock.model.stock.entities.Company;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyDto {
    private String symbol;
    private String name;
//    private BigDecimal price;
    private String exchange;
    private String assetType;

    public static CompanyDto mapAVCompany(AVCompany avCompany) {
        return new CompanyDto(
                avCompany.getSymbol(),
                avCompany.getName(),
                avCompany.getExchange(),
                avCompany.getAssetType());
    }

    public static CompanyDto mapOverviewCompany(OverviewCompanyDto company) {
        return new CompanyDto(
                company.getSymbol(),
                company.getName(),
//                company.getPrice(),
                company.getExchange(),
                company.getAssetType());
    }

    public static CompanyDto mapFinnhubCompany(FinnhubCompany company) {
        return new CompanyDto(
                company.getTicker(),
                company.getName(),
                company.getExchange(),
                "Common Stock");
    }

    public static CompanyDto mapTwelveCompany(TwelveCompany company) {
        return new CompanyDto(
                company.getSymbol(),
                company.getName(),
                company.getExchange(),
                company.getType());
    }

    public static CompanyDto mapCompany(Company company) {
        return new CompanyDto(
                company.getSymbol(),
                company.getName(),
                company.getExchange(),
                company.getAssetType());
    }
}
