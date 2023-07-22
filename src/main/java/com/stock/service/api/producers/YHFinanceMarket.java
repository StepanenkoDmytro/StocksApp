package com.stock.service.api.producers;

import com.stock.service.api.producers.entity.yahooFinance.Mover;

import java.util.List;

public interface YHFinanceMarket {
    List<Mover> getMovers();
}
