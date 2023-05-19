package com.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CoinsByFilter {
    List<CoinDto> data;
    int totalItems;
}
