package com.stock.service.api;

import com.stock.model.coin.api.producers.FearGreedIndexMarket;
import com.stock.dto.coins.FearGreedIndexDto;
import com.stock.model.coin.service.CoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FearGreedIndexTest {
    @Autowired
    private FearGreedIndexMarket fearGreedIndexMarket;
    @Autowired
    private CoinService coinService;

    @Test
    void getIndex() {
        FearGreedIndexDto fearGreedIndex = fearGreedIndexMarket.getFearGreedIndex();
        System.out.println(fearGreedIndex);

        FearGreedIndexDto fearGreedIndex1 = coinService.getFearGreedIndex();
        System.out.println(fearGreedIndex1);


    }
}
