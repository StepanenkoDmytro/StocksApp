package com.stock.service.impl;

import com.stock.api.AlphaVantageMarket;
import com.stock.api.FinnhubMarket;
import com.stock.api.YHFinanceMarket;
import com.stock.api.entity.alphaVantage.stock.OverviewCompany;
import com.stock.api.entity.yahooFinance.Mover;
import com.stock.api.entity.yahooFinance.YHQuote;
import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.dto.forCharts.PiePrice;
import com.stock.dto.stocks.CompanyDto;
import com.stock.dto.stocks.OverviewCompanyDto;
import com.stock.model.stock.Company;
import com.stock.repository.stock.CompanyRepository;
import com.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {
    private final AlphaVantageMarket alphaVantageMarket;
    private final FinnhubMarket finnhubMarket;
    private final YHFinanceMarket yhFinanceMarket;
    private final CompanyRepository companyRepository;

    @Autowired
    public StockServiceImpl(AlphaVantageMarket alphaVantageMarket, FinnhubMarket finnhubMarket, YHFinanceMarket yhFinanceMarket, CompanyRepository companyRepository) {
        this.alphaVantageMarket = alphaVantageMarket;
        this.finnhubMarket = finnhubMarket;
        this.yhFinanceMarket = yhFinanceMarket;
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDto getCompanyBySymbol(String symbol) {
        Optional<Company> companyDB = companyRepository.findBySymbol(symbol);
        CompanyDto result;
        if (companyDB.isPresent()) {
            result = CompanyDto.mapCompany(companyDB.get());
        } else {
            OverviewCompany companyAPI = alphaVantageMarket.findCompanyByTicker(symbol);
            if (companyAPI.getName() != null) {
//                System.out.println(symbol);
                Company newCompany = Company.mapOverviewCompany(companyAPI);
                newCompany.setUpdated(new Date());
                companyRepository.save(newCompany);
                result = CompanyDto.mapCompany(newCompany);
            } else {
                result = new CompanyDto(symbol, "not_found", "not_found", "not_found");
            }
        }
        return result;
    }

    @Override
    public List<PiePrice> getPriceAccountStocksByList(List<AccountStockDto> stocks) {
        if (stocks == null) {
            return new ArrayList<>();
        }
        return stocks.stream()
                .map(stock -> {
                    BigDecimal actualStocksPrice = finnhubMarket.findPriceStockByTicker(stock.getSymbol()).multiply(BigDecimal.valueOf(stock.getCountStocks()));
                    return new PiePrice(stock.getName(), actualStocksPrice);
                })
                .toList();
    }
    @Override
    public List<PiePrice> getPriceStocksByList(List<AccountStockDto> stocks) {
        if (stocks == null) {
            return new ArrayList<>();
        }
        return stocks.stream()
                .map(stock -> {
                    BigDecimal actualStocksPrice = finnhubMarket.findPriceStockByTicker(stock.getSymbol());
                    return new PiePrice(stock.getSymbol(), actualStocksPrice);
                })
                .toList();
    }

    @Override
    @Cacheable(value = "getMovers", key = "#typeOfMover")
    public List<CompanyDto> getMovers(String typeOfMover) {
        List<Mover> movers = yhFinanceMarket.getMovers();
//        Mover mover1 = movers.get(0);
//        System.out.println(mover1);
//        System.out.println(movers);
        Optional<List<YHQuote>> first = movers.stream()
                .filter(mover -> mover.getCanonicalName().equals(typeOfMover))
                .map(Mover::getQuotes)
                .findFirst();
        List<List<YHQuote>> lists = movers.stream()
                .filter(mover -> mover.getCanonicalName().equals(typeOfMover))
                .map(Mover::getQuotes)
                .toList();
//        System.out.println(lists);
        return first.orElseGet(ArrayList::new)
                .stream()
                .map(yhQuote -> getCompanyBySymbol(yhQuote.getSymbol()))
                .toList();
    }

    @Override
    @Cacheable(value = "getOverviewCompany", key = "#ticker")
    public OverviewCompanyDto getOverviewCompanyBySymbol(String ticker) {
        OverviewCompany company = alphaVantageMarket.findCompanyByTicker(ticker);

        OverviewCompanyDto companyDto = OverviewCompanyDto.mapOverviewCompany(company);

        BigDecimal actualStocksPrice = finnhubMarket.findPriceStockByTicker(ticker);
        companyDto.setPrice(actualStocksPrice);
        return companyDto;
    }
}
