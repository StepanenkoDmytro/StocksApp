package com.stock.rest;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.accountDtos.AccountStockDto;
import com.stock.dto.forCharts.PiePrice;
import com.stock.dto.stocks.CompanyDto;
import com.stock.dto.stocks.OverviewCompanyDto;
import com.stock.dto.stocks.StockBuyDetails;
import com.stock.model.account.Account;
import com.stock.model.account.AccountStock;
import com.stock.service.AccountService;
import com.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    private final StockService stockService;
    private final AccountService accountService;

    @Autowired
    public StockController(StockService stockService, AccountService accountService) {
        this.stockService = stockService;
        this.accountService = accountService;
    }

    @GetMapping("/movers/{type}")
    public ResponseEntity<List<CompanyDto>> getActivesCompanies(@PathVariable("type") String type) {
        List<CompanyDto> movers = stockService.getMovers(type);
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
    public ResponseEntity<List<PiePrice>> priceList(@RequestBody AccountDto account) {
        //зробити метод через веб-сокети
        List<AccountStockDto> stocks = account.getStocks();
        if(!stocks.isEmpty()) {
            List<PiePrice> priceStocksByList = stockService.getPriceStocksByList(stocks);
            return ResponseEntity.ok(priceStocksByList);
        }
        return ResponseEntity.badRequest().build();
    }
}
