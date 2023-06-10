package com.stock.dto.stocks;

import lombok.Data;

@Data
public class StockBuyDetails {
    private OverviewCompanyDto activeStock;
    private StockBuy data;
}
