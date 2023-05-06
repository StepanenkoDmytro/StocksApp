package com.stock.controllers;

import com.stock.api.CoinMarket;
import com.stock.dto.CoinDto;
import com.stock.service.CoinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/main")
public class MainController {
    private final CoinService coinService;

    public MainController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping(value = {"/", ""})
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       @RequestParam Optional<Integer> page,
                       Model model) {
        int currentPage = page.orElse(1);

//        List<CoinDto> coins = filter.isEmpty() ?
//                coinService.getAllCoins(currentPage) :
//                ));
        List<CoinDto> coins;
        int totalPages;
        int totalItems;
        if(StringUtils.isEmpty(filter)){
            coins = coinService.getAllCoins(currentPage);
            totalPages = CoinMarket.MAX_PAGES;
            totalItems = CoinMarket.MAX_ELEMENTS;
        } else {
            coins = new ArrayList<>(Arrays.asList(coinService.getByTicker(filter)));
            totalPages = 1;
            totalItems = coins.size();
        }

        model.addAttribute("coins", coins);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("filter", filter);
        return "main-list";
    }

    @GetMapping("/{coin_id}")
    public String coin(@PathVariable(value = "coin_id") String coin_id,
                       Model model) {
        CoinDto coin = coinService.getByTicker(coin_id);
        System.out.println(coin);
        model.addAttribute("coin", coin);
        return "coin";
    }
}
