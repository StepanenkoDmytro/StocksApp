package com.stock.model.stock.service;

import com.stock.model.stock.dto.CompaniesForClient;
import com.stock.model.user.account.entities.AccountStock;
import com.stock.model.coin.api.StockAPIService;
import com.stock.model.coin.api.producers.entity.alphaVantage.stock.OverviewCompany;
import com.stock.model.coin.api.producers.entity.yahooFinance.Mover;
import com.stock.model.coin.api.producers.entity.yahooFinance.YHQuote;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.dto.forCharts.PricesData;
import com.stock.model.stock.dto.CompanyDto;
import com.stock.model.stock.dto.OverviewCompanyDto;
import com.stock.model.stock.entities.Company;
import com.stock.model.stock.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {
    private final StockAPIService stockAPIService;
    private final CompanyRepository companyRepository;

    @Autowired
    public StockServiceImpl(StockAPIService stockAPIService, CompanyRepository companyRepository) {
        this.stockAPIService = stockAPIService;
        this.companyRepository = companyRepository;
    }

    @Override
    public CompaniesForClient getCompaniesList(int page) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Company> all = companyRepository.findAll(pageable);

        List<CompanyDto> companies = all.getContent().stream()
                .map(CompanyDto::mapCompany)
                .collect(Collectors.toList());

        return new CompaniesForClient(companies, all.getTotalPages(), all.getTotalElements(), page);
    }

    @Override
    public CompanyDto getCompanyBySymbol(String symbol) {
        Optional<Company> companyDB = companyRepository.findBySymbol(symbol);
        CompanyDto result;
        if (companyDB.isPresent()) {
            result = CompanyDto.mapCompany(companyDB.get());
        } else {
            OverviewCompany companyAPI = stockAPIService.findCompanyByTicker(symbol);
            if (companyAPI.getName() != null) {
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
    public List<PricesData> getPriceAccountStocksByListWithUSD(AccountDto account) {
        if (account == null || account.getStocks() == null) {
            return new ArrayList<>();
        }

        List<AccountStockDto> stocks = account.getStocks();

        List<PricesData> prices = stocks.stream()
                .map(stock -> {
                    BigDecimal actualStocksPrice = stockAPIService.findPriceStockByTicker(stock.getSymbol()).multiply(BigDecimal.valueOf(stock.getCountStocks()));
                    return new PricesData(stock.getName(), actualStocksPrice);
                })
                .collect(Collectors.toList());

        PricesData freeUSD = new PricesData("USD", account.getBalance());
        prices.add(freeUSD);
        return prices;
    }

    @Override
    public List<PricesData> getPriceAccountStocksByListWithoutUSD(List<AccountStock> stocks) {
        return stocks.stream()
                .map(stock -> {
                    BigDecimal actualStocksPrice = stockAPIService.findPriceStockByTicker(stock.getSymbol()).multiply(BigDecimal.valueOf(stock.getCountStocks()));
                    return new PricesData(stock.getSymbol(), actualStocksPrice);
                })
                .toList();
    }

    @Override
    public List<PricesData> getPriceStocksByList(AccountDto account) {
        if (account == null || account.getStocks() == null) {
            return new ArrayList<>();
        }

        List<AccountStockDto> stocks = account.getStocks();
        return stocks.stream()
                .map(stock -> {
                    BigDecimal actualStocksPrice = stockAPIService.findPriceStockByTicker(stock.getSymbol());
                    return new PricesData(stock.getSymbol(), actualStocksPrice);
                })
                .toList();
    }

    //typesOfMover: DAY_GAINERS, DAY_LOSERS, MOST_ACTIVES
    @Override
    @Cacheable(value = "getMovers", key = "#typeOfMover")
    public List<CompanyDto> getMovers(String typeOfMover) {
        List<Mover> movers = stockAPIService.getMovers();
        Optional<List<YHQuote>> first = movers.stream()
                .filter(mover -> mover.getCanonicalName().equals(typeOfMover))
                .map(Mover::getQuotes)
                .findFirst();
        return first.orElseGet(ArrayList::new)
                .stream()
                .map(yhQuote -> getCompanyBySymbol(yhQuote.getSymbol()))
                .toList();
    }

    public BigDecimal getPriceBySymbol(String symbol) {
        return stockAPIService.findPriceStockByTicker(symbol);
    }

    @Override
    @Cacheable(value = "getOverviewCompany", key = "#ticker")
    public OverviewCompanyDto getOverviewCompanyBySymbol(String ticker) {
        OverviewCompany company = stockAPIService.findCompanyByTicker(ticker);

        OverviewCompanyDto companyDto = OverviewCompanyDto.mapOverviewCompany(company);

        BigDecimal actualStocksPrice = stockAPIService.findPriceStockByTicker(ticker);
        companyDto.setPrice(actualStocksPrice);
        return companyDto;
    }
}
