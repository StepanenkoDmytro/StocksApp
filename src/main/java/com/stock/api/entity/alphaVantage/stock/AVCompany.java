package com.stock.api.entity.alphaVantage.stock;

import lombok.Data;

@Data
public class AVCompany {
    private String symbol;
    private String name;
    private String exchange;
    private String assetType;
    private String ipoDate;
    private String delistingDate;
    private String status;

    public static AVCompany mapFromStringArray(String[] values) {
        AVCompany AVCompany = new AVCompany();
        AVCompany.setSymbol(values[0]);
        AVCompany.setName(values[1]);
        AVCompany.setExchange(values[2]);
        AVCompany.setAssetType(values[3]);
        AVCompany.setIpoDate(values[4]);
        AVCompany.setDelistingDate(values[5]);
        AVCompany.setStatus(values[6]);
        return AVCompany;
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
