package com.stock.api;

import com.stock.api.entity.FearGreedIndex.FearGreedIndex;
import com.stock.dto.coins.FearGreedIndexDto;

public interface FearGreedIndexMarket {
    FearGreedIndexDto getFearGreedIndex();
}
