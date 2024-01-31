package com.stock.rest;

import com.stock.model.profile.Portfolio;
import com.stock.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio/")
@CrossOrigin
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("save")
    public ResponseEntity savePortfolio(@RequestBody Portfolio portfolioData) {
        portfolioService.savePortfolio(portfolioData);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
