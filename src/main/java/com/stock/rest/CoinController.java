package com.stock.rest;

import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.coins.CoinBuy;
import com.stock.dto.coins.CoinsForClient;
import com.stock.dto.coins.CoinDto;
import com.stock.dto.forCharts.PieCoinsPrice;
import com.stock.dto.accountDtos.AccountCoinDto;
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
import java.util.List;
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
//        Account account = null;
//        if(coinBuy.getAccountID() != null) {

        Account account = accountService.getAccountById(coinBuy.getAccountID());
        CoinDto coin = coinService.getByTicker(coinBuy.getCoinId());
        BigDecimal amountUSD = CoinBuyHelper.convertToUSD(coinBuy.getAmount());

        AccountDto updatedAccount = accountService.processCoinBuy(amountUSD, coin, account);
        return ResponseEntity.ok(updatedAccount);
//        }
//        return ResponseEntity.badRequest().body(AccountDto.mapAccount(account));
    }

    @GetMapping("/price-for-list")
    public ResponseEntity getPriceCoinsById(@RequestBody List<AccountCoinDto> coinsList) {
        List<PieCoinsPrice> priceCoins = coinService.getPriceCoinsByList(coinsList);
        return ResponseEntity.ok(priceCoins);
    }
}
