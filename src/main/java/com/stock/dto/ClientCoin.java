package com.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClientCoin {
    List<CoinDto> data;
    int totalPages;
    int totalItems;
    int currentPage;
}
