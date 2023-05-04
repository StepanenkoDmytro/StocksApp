package com.stock.controllers;

import com.stock.api.entity.Coin;
import com.stock.dto.CoinDto;
import com.stock.service.CoinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/main")
public class MainController {
    private final CoinService coinService;

    public MainController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    public String main(Model model) {
        List<Coin> list = coinService.getAllCoins();
        List<CoinDto> coins = list.stream().map(CoinDto::mapCoinToDto).collect(Collectors.toList());
        model.addAttribute("coins", coins);
        System.out.println(coins.size());
        return "main";
    }
}
