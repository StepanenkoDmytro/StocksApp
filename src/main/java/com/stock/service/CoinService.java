package com.stock.service;


import com.stock.dto.CoinDto;

import java.util.List;

public interface CoinService {
    List<CoinDto> getAllCoins(int page);
    CoinDto getByTicker(String ticker);
}
