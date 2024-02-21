package com.stock.dto.coins;

import com.stock.dto.forCharts.CandlesDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CoinDetails {
    private CoinDto coin;
//    private List<CandlesDto> candles;
}
