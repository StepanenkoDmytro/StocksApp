package com.stock.service.api.impl;

import com.stock.service.api.StockAPIService;
import com.stock.service.api.producers.AlphaVantageMarket;
import com.stock.service.api.producers.FinnhubMarket;
import com.stock.service.api.producers.YHFinanceMarket;
import com.stock.service.api.producers.entity.alphaVantage.stock.OverviewCompany;
import com.stock.service.api.producers.entity.yahooFinance.Mover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StockAPIServiceImpl implements StockAPIService {
    private final AlphaVantageMarket alphaVantageMarket;
    private final FinnhubMarket finnhubMarket;
    private final YHFinanceMarket yhFinanceMarket;
    @Autowired
    public StockAPIServiceImpl(AlphaVantageMarket alphaVantageMarket, FinnhubMarket finnhubMarket, YHFinanceMarket yhFinanceMarket) {
        this.alphaVantageMarket = alphaVantageMarket;
        this.finnhubMarket = finnhubMarket;
        this.yhFinanceMarket = yhFinanceMarket;
    }

    @Override
    public OverviewCompany findCompanyByTicker(String ticker) {
        return alphaVantageMarket.findCompanyByTicker(ticker);
    }

    @Override
    public BigDecimal findPriceStockByTicker(String ticker) {
        return finnhubMarket.findPriceStockByTicker(ticker);
    }

    @Override
    public List<Mover> getMovers() {
        return yhFinanceMarket.getMovers();
    }
}
