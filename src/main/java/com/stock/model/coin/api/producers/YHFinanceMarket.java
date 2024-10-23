package com.stock.model.coin.api.producers;

import com.stock.model.coin.api.producers.entity.yahooFinance.Mover;

import java.util.List;

public interface YHFinanceMarket {
    List<Mover> getMovers();
}
