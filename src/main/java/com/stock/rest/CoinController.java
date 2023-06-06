package com.stock.rest;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.coins.CoinBuy;
import com.stock.dto.coins.CoinsForClient;
import com.stock.dto.coins.CoinDto;
import com.stock.helper.CoinBuyHelper;
import com.stock.model.account.Account;
import com.stock.service.AccountService;
import com.stock.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/coins")
@CrossOrigin
public class CoinController {
    private final CoinService coinService;
    private final AccountService accountService;

    @Autowired
    public CoinController(CoinService coinService, AccountService accountService) {
        this.coinService = coinService;
        this.accountService = accountService;
    }

    @GetMapping("")
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

    @PostMapping("/{coin_id}")
    public ResponseEntity<AccountDto> buyCoin(@RequestBody CoinBuy coinBuy) {
        BigDecimal amountUSD = CoinBuyHelper.convertToUSD(coinBuy.getAmount());
        if(amountUSD.compareTo(BigDecimal.TEN) > 0) {
            Account account = accountService.getAccountById(coinBuy.getAccountID());
            CoinDto coin = coinService.getByTicker(coinBuy.getCoinId());


            AccountDto updatedAccount = accountService.processCoinBuy(amountUSD, coin, account);
            return ResponseEntity.ok(updatedAccount);
        }
        //коли буде працювати фронт - зробити просто валідацію amount
        return ResponseEntity.badRequest().body(null);
    }
}
