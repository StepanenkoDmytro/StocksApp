package com.stock.controllers;

import com.stock.api.RequestHelper;
import com.stock.dto.CoinDto;
import com.stock.dto.CoinsByFilter;
import com.stock.service.CoinService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
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
        int totalPages;
        int totalItems;
        List<CoinDto> coins;
        boolean isAuthenticated = userDetails != null;

        System.out.println(principal);
        if(StringUtils.isEmpty(filter)){
            coins = coinService.getAllCoins(currentPage);
            totalPages = RequestHelper.MAX_PAGES;
            totalItems = RequestHelper.MAX_ELEMENTS;
        } else {
            CoinsByFilter coinsByFilter = coinService.getCoinsByFilter(currentPage, filter);
            coins = coinsByFilter.getData();
            totalItems = coinsByFilter.getTotalItems();

            totalPages = (int) Math.ceil((double) totalItems / RequestHelper.PAGE_LIMIT);
            System.out.println(totalItems + " / " + totalPages);
        }

        model.addAttribute("coins", coins);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
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
