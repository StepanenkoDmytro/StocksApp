package com.stock.service.impl;

import com.stock.api.AlphaVantageMarket;
import com.stock.api.FinnhubMarket;
import com.stock.api.YHFinanceMarket;
import com.stock.api.entity.alphaVantage.stock.Company;
import com.stock.api.entity.alphaVantage.stock.OverviewCompany;
import com.stock.api.entity.yahooFinance.Mover;
import com.stock.api.entity.yahooFinance.YHQuote;
import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.dto.forCharts.PiePrice;
import com.stock.helper.RequestManager;
import com.stock.dto.stocks.CompaniesForClient;
import com.stock.dto.stocks.CompanyDto;
import com.stock.dto.stocks.OverviewCompanyDto;
import com.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private final AlphaVantageMarket alphaVantageMarket;
    private final FinnhubMarket finnhubMarket;
    private final YHFinanceMarket yhFinanceMarket;

    @Autowired
    public StockServiceImpl(AlphaVantageMarket alphaVantageMarket, FinnhubMarket finnhubMarket, YHFinanceMarket yhFinanceMarket) {
        this.alphaVantageMarket = alphaVantageMarket;
        this.finnhubMarket = finnhubMarket;
        this.yhFinanceMarket = yhFinanceMarket;
    }

    @Override
    public CompaniesForClient getAll(int page) {
//        List<Company> allCompanies = alphaVantageMarket.findAllCompanies();
//        int totalPages = (int) Math.ceil((double) allCompanies.size() / RequestManager.PAGE_LIMIT);
//
//        int startIndex = (page - 1) * RequestManager.PAGE_LIMIT;
//        int endIndex = Math.min(RequestManager.PAGE_LIMIT, allCompanies.size() - startIndex);
//        List<CompanyDto> data = allCompanies.stream()
//                .skip(startIndex)
//                .limit(endIndex)
//                .map(CompanyDto::mapCompany)
//                .toList();
//
//        return new CompaniesForClient(data, totalPages, data.size(), page);
        return null;
    }

    @Override
    public OverviewCompanyDto getCompanyBySymbol(String symbol) {
        OverviewCompany company = alphaVantageMarket.findCompanyByTicker(symbol);
        BigDecimal price = finnhubMarket.findPriceStockByTicker(symbol);
        return OverviewCompanyDto.mapToDto(company, price);
    }

    @Override
    public List<PiePrice> getPriceStocksByList(List<AccountStockDto> stocks) {
        if (stocks == null) {
            return new ArrayList<>();
        }
        return stocks.stream()
                .map(stock -> {
                    BigDecimal actualStocksPrice = finnhubMarket.findPriceStockByTicker(stock.getSymbol()).multiply(BigDecimal.valueOf(stock.getCountStocks()));
                    return new PiePrice(stock.getSymbol(), actualStocksPrice);
                })
                .toList();
    }

    @Override
    @Cacheable(value = "getGainersMovers")
    public CompaniesForClient getGainersMovers() {
        List<Mover> movers = yhFinanceMarket.getMovers();
        List<OverviewCompanyDto> mostActives = new ArrayList<>();
        for (Mover mover : movers) {
            if (mover.getCanonicalName().equals("DAY_GAINERS")) {
                getCompaniesByMovers(mover, mostActives);
            }
        }
        List<CompanyDto> result = mostActives.stream()
                .map(CompanyDto::mapOverviewCompany)
                .toList();
        return new CompaniesForClient(result);
    }

    @Override
    @Cacheable(value = "getLosersMovers")
    public CompaniesForClient getLosersMovers() {
        List<Mover> movers = yhFinanceMarket.getMovers();
        List<OverviewCompanyDto> mostActives = new ArrayList<>();
        for (Mover mover : movers) {
            if (mover.getCanonicalName().equals("DAY_LOSERS")) {
                getCompaniesByMovers(mover, mostActives);
            }
        }
        List<CompanyDto> result = mostActives.stream()
                .map(CompanyDto::mapOverviewCompany)
                .toList();
        return new CompaniesForClient(result);
    }

    @Override
    @Cacheable(value = "getActivesMovers")
    public CompaniesForClient getActivesMovers() {
        List<Mover> movers = yhFinanceMarket.getMovers();
        List<OverviewCompanyDto> mostActives = new ArrayList<>();
        for (Mover mover : movers) {
            if (mover.getCanonicalName().equals("MOST_ACTIVES")) {
                getCompaniesByMovers(mover, mostActives);
            }
        }
        List<CompanyDto> result = mostActives.stream()
                .map(CompanyDto::mapOverviewCompany)
                .toList();

        return new CompaniesForClient(result);
    }

    private List<OverviewCompanyDto> getCompaniesByMovers(Mover mover, List<OverviewCompanyDto> listByTag) {
        List<YHQuote> quotes = mover.getQuotes();
        for(YHQuote quote : quotes) {
            OverviewCompanyDto companyBySymbol = getCompanyBySymbol(quote.getSymbol());
            listByTag.add(companyBySymbol);
        }
        return listByTag;
    }
    //    @Override
//    public MoversCompanies getMovers() {
//
//
//        List<OverviewCompanyDto> dayGainers = new ArrayList<>();
//        List<OverviewCompanyDto> dayLosers = new ArrayList<>();
//        List<OverviewCompanyDto> mostActives = new ArrayList<>();
//
//
//            else if (mover.getCanonicalName().equals("DAY_GAINERS")) {
//                getCompaniesByMovers(mover, dayGainers);
//            } else if (mover.getCanonicalName().equals("DAY_LOSERS")) {
//                getCompaniesByMovers(mover, dayLosers);
//            }
//        }
//        return new MoversCompanies(dayGainers, dayLosers, mostActives);
//    }

}
