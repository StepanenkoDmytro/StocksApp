package com.stock.rest;

import com.stock.api.RequestHelper;
import com.stock.dto.ClientCoin;
import com.stock.dto.CoinDto;
import com.stock.dto.CoinsByFilter;
import com.stock.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public ResponseEntity<ClientCoin> getAllCoins(@RequestParam(required = false, defaultValue = "") String filter,
                                           @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int totalPages;
        int totalItems;
        List<CoinDto> coins;

        if(StringUtils.isEmpty(filter)) {
            coins = coinService.getAllCoins(currentPage);
            totalPages = RequestHelper.MAX_PAGES;
            totalItems = RequestHelper.MAX_ELEMENTS;
        } else {
            CoinsByFilter coinsByFilter = coinService.getCoinsByFilter(currentPage, filter);
            coins = coinsByFilter.getData();
            totalItems = coinsByFilter.getTotalItems();
            totalPages = (int) Math.ceil((double) totalItems / RequestHelper.PAGE_LIMIT);
        }
        ClientCoin clientCoin = new ClientCoin(coins, totalPages, totalItems, currentPage);
        return ResponseEntity.ok(clientCoin);
    }

    @GetMapping("/{coin_id}")
    public ResponseEntity<CoinDto> getCoin(@AuthenticationPrincipal UserDetails userDetails,
                       @PathVariable(value = "coin_id") String coin_id) {
        CoinDto coin = coinService.getByTicker(coin_id);
        return ResponseEntity.ok(coin);
    }
}
