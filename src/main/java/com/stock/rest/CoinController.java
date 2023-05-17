package com.stock.rest;

import com.stock.api.CoinMarket;
import com.stock.dto.ClientCoin;
import com.stock.dto.CoinDto;
import com.stock.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/coins")
@CrossOrigin
public class CoinController {
    private final CoinService coinService;

    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping(value = {"/", ""})
    public ResponseEntity<ClientCoin> main(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam(required = false, defaultValue = "") String filter,
                               @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int totalPages;
        int totalItems;
        List<CoinDto> coins;

        if(filter == null || filter.length() < 2){
            coins = coinService.getAllCoins(currentPage, null);
            totalPages = CoinMarket.MAX_PAGES;
            totalItems = CoinMarket.MAX_ELEMENTS;
        } else {
            coins = coinService.getAllCoins(currentPage, filter);
            totalPages = 1;
            totalItems = coins.size();
        }
        ClientCoin clientCoin = new ClientCoin(coins, totalPages, totalItems, currentPage);
        return ResponseEntity.ok().body(clientCoin);
    }

    @GetMapping("/{coin_id}")
    public ResponseEntity<CoinDto> coin(@AuthenticationPrincipal UserDetails userDetails,
                       @PathVariable(value = "coin_id") String coin_id,
                       Model model) {
        CoinDto coin = coinService.getByTicker(coin_id);
//        boolean isAuthenticated = userDetails != null;
//
//        model.addAttribute("coin", coin);
//        model.addAttribute("isAuthenticated", isAuthenticated);
        return ResponseEntity.ok().body(coin);
    }
}
