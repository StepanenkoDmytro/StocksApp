package com.stock.rest;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.coins.CoinDto;
import com.stock.dto.stocks.CompaniesForClient;
import com.stock.dto.stocks.OverviewCompanyDto;
import com.stock.dto.stocks.StockBuyDetails;
import com.stock.model.account.Account;
import com.stock.model.account.AccountStock;
import com.stock.repository.account.AccountRepository;
import com.stock.service.AccountService;
import com.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    private final StockService stockService;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Autowired
    public StockController(StockService stockService, AccountService accountService, AccountRepository accountRepository) {
        this.stockService = stockService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("")
    public ResponseEntity<CompaniesForClient> getAllCompanies(@RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        CompaniesForClient companies = stockService.getAll(currentPage);
//        List<CompanyDto> companyMocks = new ArrayList<>(Arrays.asList(
//                new CompanyDto("AA", "Tesla Company", "NASDAQ", "STOCK"),
//                new CompanyDto("AB", "Tabacco Company", "NASDAQ", "STOCK"),
//                new CompanyDto("AC", "AliBABA Company", "NASDAQ", "STOCK"),
//                new CompanyDto("AD", "NASDAQ Company", "NASDAQ", "STOCK"),
//                new CompanyDto("AE", "MOTOROLA Company", "NASDAQ", "STOCK"),
//                new CompanyDto("AF", "BURITO Company", "NASDAQ", "STOCK"),
//                new CompanyDto("AG", "HYU Company", "NASDAQ", "STOCK"),
//                new CompanyDto("AD", "PEZDA Company", "NASDAQ", "STOCK"),
//                new CompanyDto("AD", "SKOVORODA Company", "NASDAQ", "STOCK")
//        ));
//        CompaniesForClient mock = new CompaniesForClient(companyMocks, 100, 900, 1);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<OverviewCompanyDto> getCompanyBySymbol(@PathVariable("symbol") String symbol) {
        OverviewCompanyDto company = stockService.getCompanyBySymbol(symbol);
        return ResponseEntity.ok(company);
    }

    @PostMapping("/buyStock")
    public ResponseEntity<AccountDto> buyStock(@RequestBody StockBuyDetails buyDetails) {
        OverviewCompanyDto stock = buyDetails.getActiveStock();
        int countStocks = buyDetails.getData().getCountStocks();
        long accountID = buyDetails.getData().getAccountID();
        Account account = accountService.getAccountById(accountID);

        AccountDto updatedAccount = accountService.processStockBuy(stock, countStocks, account);
        return ResponseEntity.ok(updatedAccount);
    }
}
