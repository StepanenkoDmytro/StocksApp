package com.stock.model.stock.dto;

import lombok.Data;

@Data
public class StockBuyDetails {
    private OverviewCompanyDto activeStock;
    private StockBuy data;
}
