package com.stock.service.impl;

import com.stock.model.profile.Portfolio;
import com.stock.repository.portfolio.PortfolioRepository;
import com.stock.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }
}
