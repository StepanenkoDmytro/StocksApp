package com.stock.api;

import com.stock.api.entity.yahooFinance.Mover;

import java.util.List;

public interface YHFinanceMarket {
    List<Mover> getMovers();
}
