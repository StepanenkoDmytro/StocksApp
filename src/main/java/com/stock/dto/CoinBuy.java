package com.stock.dto;

import lombok.Data;

@Data
public class CoinBuy {
    private Long accountID;
    private String amount;
    private String coinId;
}
