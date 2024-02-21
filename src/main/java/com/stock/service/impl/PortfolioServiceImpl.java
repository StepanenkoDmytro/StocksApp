package com.stock.service.impl;

import com.stock.model.portfolio.Portfolio;
import com.stock.repository.portfolio.PortfolioRepository;
import com.stock.repository.portfolio.PortfolioSpendingRepository;
import com.stock.service.PortfolioService;
import com.stock.service.exceptions.AccountFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioSpendingRepository portfolioSpendingRepository;

    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, PortfolioSpendingRepository portfolioSpendingRepository) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioSpendingRepository = portfolioSpendingRepository;
    }

    @Override
    public Portfolio getPortfolioById(Long portfolioID) {
        return portfolioRepository.findById(portfolioID).orElseThrow(() ->
                new AccountFetchException(String.format("Portfolio with id = %d not found", portfolioID)));
    }

    @Override
    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    @Override
    public void deleteSpending(String id) {
        portfolioSpendingRepository.deleteById(id);
    }
}
