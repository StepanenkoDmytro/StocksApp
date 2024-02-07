package com.stock.rest;

import com.stock.dto.portfolio.spending.SpendingDTO;
import com.stock.model.portfolio.Portfolio;
import com.stock.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile/")
@CrossOrigin
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("spendings-list/{portfolioID}")
    public ResponseEntity getSpendings(@PathVariable Long portfolioID) {
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioID);
        List<SpendingDTO> spendingsList = portfolio.getSpendingsList().stream().map(SpendingDTO::mapToDTO).toList();
        return ResponseEntity.ok().body(spendingsList);
    }

    @PostMapping("{portfolioID}/add-spending")
    public ResponseEntity addSpending(@PathVariable Long portfolioID, @RequestBody SpendingDTO spending) {
        //TODO add @AuthenticationPrincipal UserDetails userDetails for find user portfolio
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioID);
        portfolio.addSpending(SpendingDTO.mapFromDTO(spending));
        portfolioService.savePortfolio(portfolio);
        spending.setSaved(true);
        return ResponseEntity.ok().body(spending);
    }

    @DeleteMapping("delete-spending/{id}")
    public ResponseEntity deleteSpending(@PathVariable String id) {
        portfolioService.deleteSpending(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
