package com.stock.model.user.portfolio.service;

import com.stock.model.user.portfolio.entities.Portfolio;
import com.stock.model.user.portfolio.entities.PortfolioCategory;
import com.stock.model.user.portfolio.repository.PortfolioCategoryRepository;
import com.stock.model.user.portfolio.repository.PortfolioRepository;
import com.stock.model.user.portfolio.repository.PortfolioSpendingRepository;
import com.stock.exceptions.AccountFetchException;
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
