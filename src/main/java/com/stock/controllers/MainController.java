package com.stock.controllers;

import com.stock.dto.CoinDto;
import com.stock.dto.CoinsForClient;
import com.stock.service.CoinService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/main")
public class MainController {
    private final CoinService coinService;

    public MainController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping(value = {"/", ""})
    public String main(@AuthenticationPrincipal UserDetails userDetails,
                       @RequestParam(required = false, defaultValue = "") String filter,
                       @RequestParam Optional<Integer> page,
                       Model model,
                       Principal principal) {
        int currentPage = page.orElse(1);
        CoinsForClient coins;
        boolean isAuthenticated = userDetails != null;

        System.out.println(principal);
        if(StringUtils.isEmpty(filter)){
            coins = coinService.getAllCoins(currentPage);
        } else {
            coins = coinService.getCoinsByFilter(currentPage, filter);
        }

        model.addAttribute("coins", coins);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", coins.getTotalPages());
        model.addAttribute("totalItems", coins.getTotalItems());
        model.addAttribute("filter", filter);
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "main-list";
    }

    @GetMapping("/{coin_id}")
    public String coin(@AuthenticationPrincipal UserDetails userDetails,
                       @PathVariable(value = "coin_id") String coin_id,
                       Model model) {
        CoinDto coin = coinService.getByTicker(coin_id);
        boolean isAuthenticated = userDetails != null;

        model.addAttribute("coin", coin);
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "coin";
    }
}
