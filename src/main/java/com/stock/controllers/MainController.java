package com.stock.controllers;

import com.stock.api.CoinMarket;
import com.stock.dto.CoinDto;
import com.stock.service.CoinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/main")
public class MainController {
    private final CoinService coinService;

    public MainController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping("/")
    public String main(@RequestParam Optional<Integer> page,
                       Model model) {
        int currentPage = page.orElse(1);
        List<CoinDto> coins = coinService.getAllCoins(currentPage);

        model.addAttribute("coins", coins);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", CoinMarket.MAX_PAGES);
        model.addAttribute("totalItems", CoinMarket.MAX_ELEMENTS);
        return "main-list";
    }

    @GetMapping("/{coin_id}")
    public String coin(@PathVariable(value = "coin_id") String coin_id,
                       Model model){
        CoinDto coin = coinService.getByTicker(coin_id);
        System.out.println(coin);
        model.addAttribute("coin", coin);
        return "coin";
    }
}
