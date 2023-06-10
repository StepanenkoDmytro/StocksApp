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
