package com.stock.model.user.portfolio;

import com.stock.dto.portfolio.category.CategoryDTO;
import com.stock.dto.portfolio.spending.SpendingDTO;
import com.stock.model.user.portfolio.entities.Portfolio;
import com.stock.model.user.portfolio.service.PortfolioService;
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

    @GetMapping("categories-list/{portfolioID}")
    public ResponseEntity getCategories(@PathVariable Long portfolioID) {
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioID);
        List<CategoryDTO> categoriesList = portfolio.getCategoryList().stream().map(CategoryDTO::mapToDTO).toList();
        return ResponseEntity.ok().body(categoriesList);
    }

    @PostMapping("{portfolioID}/add-category")
    public ResponseEntity addCategory(@PathVariable Long portfolioID, @RequestBody CategoryDTO category) {
        //TODO add @AuthenticationPrincipal UserDetails userDetails for find user portfolio
//        System.out.println(category);
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioID);
        portfolio.addCategory(CategoryDTO.mapFromDTO(category));
        portfolioService.savePortfolio(portfolio);
        category.setSaved(true);
        return ResponseEntity.ok().body(category);
    }

    @DeleteMapping("delete-category/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id) {
        portfolioService.deleteCategory(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
