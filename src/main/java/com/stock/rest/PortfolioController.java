package com.stock.rest;

import com.stock.dto.portfolio.spending.SpendingDTO;
import com.stock.model.portfolio.Portfolio;
import com.stock.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile/")
@CrossOrigin
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("add-spending")
    public ResponseEntity addSpending(@RequestBody SpendingDTO spending) {
        //TODO add @AuthenticationPrincipal UserDetails userDetails for find user portfolio
        Portfolio portfolio = portfolioService.getPortfolioById(1L);
        portfolio.addSpending(SpendingDTO.mapFromDTO(spending));
        portfolioService.savePortfolio(portfolio);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @DeleteMapping("delete-spending/{id}")
    public ResponseEntity deleteSpending(@PathVariable String id) {
        portfolioService.deleteSpending(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
