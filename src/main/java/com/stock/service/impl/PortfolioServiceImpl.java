package com.stock.service.impl;

import com.stock.model.portfolio.Portfolio;
import com.stock.model.portfolio.PortfolioCategory;
import com.stock.repository.portfolio.PortfolioCategoryRepository;
import com.stock.repository.portfolio.PortfolioRepository;
import com.stock.repository.portfolio.PortfolioSpendingRepository;
import com.stock.service.PortfolioService;
import com.stock.service.exceptions.AccountFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioSpendingRepository portfolioSpendingRepository;
    private final PortfolioCategoryRepository portfolioCategoryRepository;

    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, PortfolioSpendingRepository portfolioSpendingRepository, PortfolioCategoryRepository portfolioCategoryRepository) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioSpendingRepository = portfolioSpendingRepository;
        this.portfolioCategoryRepository = portfolioCategoryRepository;
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

    @Override
    public void deleteCategory(String id) {
        List<PortfolioCategory> children = portfolioCategoryRepository.findByParent(id);
        for (PortfolioCategory child : children) {
            deleteCategory(child.getId());
        }

        portfolioCategoryRepository.deleteById(id);
    }
}
