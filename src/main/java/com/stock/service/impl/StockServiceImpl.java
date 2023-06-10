package com.stock.service.impl;

import com.stock.api.AlphaVantageMarket;
import com.stock.api.FinnhubMarket;
import com.stock.api.entity.alphaVantage.stock.Company;
import com.stock.api.entity.alphaVantage.stock.OverviewCompany;
import com.stock.api.impl.RequestManager;
import com.stock.dto.stocks.CompaniesForClient;
import com.stock.dto.stocks.CompanyDto;
import com.stock.dto.stocks.OverviewCompanyDto;
import com.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private final AlphaVantageMarket alphaVantageMarket;
    private final FinnhubMarket finnhubMarket;

    @Autowired
    public StockServiceImpl(AlphaVantageMarket alphaVantageMarket, FinnhubMarket finnhubMarket) {
        this.alphaVantageMarket = alphaVantageMarket;
        this.finnhubMarket = finnhubMarket;
    }

    @Override
    public CompaniesForClient getAll(int page) {
        List<Company> allCompanies = alphaVantageMarket.findAllCompanies();
        int totalPages = (int) Math.ceil((double) allCompanies.size() / RequestManager.PAGE_LIMIT);

        int startIndex = (page - 1) * RequestManager.PAGE_LIMIT;
        int endIndex = Math.min(RequestManager.PAGE_LIMIT, allCompanies.size() - startIndex);
        List<CompanyDto> data = allCompanies.stream()
                .skip(startIndex)
                .limit(endIndex)
                .map(CompanyDto::mapCompany)
                .toList();

        return new CompaniesForClient(data, totalPages, data.size(), page);
    }

    @Override
    public OverviewCompanyDto getCompanyBySymbol(String symbol) {
        OverviewCompany company = alphaVantageMarket.findCompanyByTicker(symbol);
        BigDecimal price = finnhubMarket.findPriceStockByTicker(symbol);
        return OverviewCompanyDto.mapToDto(company, price);
    }
}
