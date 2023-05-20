package com.stock.rest;

import com.stock.dto.CoinsForClient;
import com.stock.dto.CoinDto;
import com.stock.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping({"/", ""})
    public ResponseEntity<CoinsForClient> getAllCoins(@RequestParam(required = false, defaultValue = "") String filter,
                                                      @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        CoinsForClient coins;

        if (StringUtils.isEmpty(filter)) {
            coins = coinService.getAllCoins(currentPage);
        } else {
            coins = coinService.getCoinsByFilter(currentPage, filter);
        }

        return ResponseEntity.ok(coins);
    }

    @GetMapping("/{coin_id}")
    public ResponseEntity<CoinDto> getCoin(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable(value = "coin_id") String coin_id) {
        CoinDto coin = coinService.getByTicker(coin_id);
        return ResponseEntity.ok(coin);
    }
}
