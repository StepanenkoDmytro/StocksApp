package com.stock.api.entity.alphaVantage.stock;

import lombok.Data;

@Data
public class Company {
    private String symbol;
    private String name;
    private String exchange;
    private String assetType;
    private String ipoDate;
    private String delistingDate;
    private String status;

    public static Company mapFromStringArray(String[] values) {
        Company company = new Company();
        company.setSymbol(values[0]);
        company.setName(values[1]);
        company.setExchange(values[2]);
        company.setAssetType(values[3]);
        company.setIpoDate(values[4]);
        company.setDelistingDate(values[5]);
        company.setStatus(values[6]);
        return company;
    }

    @Override
    public String toString() {
        return "{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", exchange='" + exchange + '\'' +
                ", assetType='" + assetType + '\'' +
                ", ipoDate='" + ipoDate + '\'' +
                ", delistingDate='" + delistingDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
