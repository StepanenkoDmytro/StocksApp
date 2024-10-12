package com.stock.rest;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.dto.analytic.RiskResponse;
import com.stock.dto.forCharts.PricesData;
import com.stock.dto.stocks.*;
import com.stock.model.account.Account;
import com.stock.model.stock.analytic.RiskType;
import com.stock.service.AccountService;
import com.stock.service.StockService;
import com.stock.service.analytic.PortfolioAnalyzer;
import com.stock.service.api.producers.entity.yahooFinance.Mover;
import com.stock.service.api.producers.impl.YHFinanceMarketImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/markets/stocks")
@CrossOrigin
public class StockController {
    private final StockService stockService;
    private final AccountService accountService;
    private final PortfolioAnalyzer portfolioAnalyzer;


    @Autowired
    public StockController(StockService stockService, AccountService accountService, PortfolioAnalyzer portfolioAnalyzer) {
        this.stockService = stockService;
        this.accountService = accountService;
        this.portfolioAnalyzer = portfolioAnalyzer;
    }

    @GetMapping
    public ResponseEntity<CompaniesForClient> getAllCompanies(@RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(0);
        CompaniesForClient companiesList = stockService.getCompaniesList(currentPage);
        return ResponseEntity.ok(companiesList);
    }

    //typesOfMover: DAY_GAINERS, DAY_LOSERS, MOST_ACTIVES
    @GetMapping("/movers/{type}")
    public ResponseEntity<List<CompanyDtoWithPrice>> getActivesCompanies(@PathVariable("type") String type) {
        List<CompanyDtoWithPrice> movers = stockService.getMovers(type).stream().map(companyDto -> {
            BigDecimal price = stockService.getPriceBySymbol(companyDto.getSymbol());
            CompanyDtoWithPrice result = CompanyDtoWithPrice.mapCompany(companyDto);
            result.setPrice(price);
            return result;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(movers);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<OverviewCompanyDto> getCompanyBySymbol(@PathVariable("symbol") String symbol) {
        OverviewCompanyDto company = stockService.getOverviewCompanyBySymbol(symbol);
        return ResponseEntity.ok(company);
    }

    @PostMapping("/buyStock")
    public ResponseEntity<AccountDto> buyStock(@RequestBody StockBuyDetails buyDetails) {
        OverviewCompanyDto stock = buyDetails.getActiveStock();
        BigDecimal price = stock.getPrice();
        int countStocks = buyDetails.getData().getCountStocks();
        long accountID = buyDetails.getData().getAccountID();
        Account account = accountService.getAccountById(accountID);

        AccountDto updatedAccount = accountService.processStockBuy(stock, price, countStocks, account);
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/price-list")
    public ResponseEntity<List<PricesData>> priceList(@RequestBody AccountDto account) {
        //зробити метод через веб-сокети
        List<AccountStockDto> stocks = account.getStocks();
        if(!stocks.isEmpty()) {
            List<PricesData> priceStocksByList = stockService.getPriceStocksByList(account);
            return ResponseEntity.ok(priceStocksByList);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/account-risk/{accountID}")
    public ResponseEntity<RiskResponse> getRiskByList(@PathVariable Long accountID) {
        Account account = accountService.getAccountById(accountID);
        RiskType risk = portfolioAnalyzer.getRiskStockPortfolio(account);

        return ResponseEntity.ok(new RiskResponse(risk.name()));
    }
}
