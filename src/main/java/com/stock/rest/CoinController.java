package com.stock.rest;

import com.stock.api.AlphaVantageMarket;
import com.stock.api.entity.FearGreedIndex.FearGreedIndex;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.coins.*;
import com.stock.dto.forCharts.CandlesDto;
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
    private final AlphaVantageMarket alphaVantageMarket;

    @Autowired
    public CoinController(CoinService coinService, AccountService accountService, AlphaVantageMarket alphaVantageMarket) {
        this.coinService = coinService;
        this.accountService = accountService;
        this.alphaVantageMarket = alphaVantageMarket;
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
    public ResponseEntity<CoinDetails> getCoinWithCandles(@AuthenticationPrincipal UserDetails userDetails,
                                                          @PathVariable(value = "coin_id") String coinID) {
        CoinDto coin = coinService.getByTicker(coinID);
        List<CandlesDto> candles = alphaVantageMarket.findCandlesById(coin.getSymbol());
        return ResponseEntity.ok(new CoinDetails(coin, candles));
    }

    @PostMapping("/{coin_id}")
    public ResponseEntity<AccountDto> buyCoin(@RequestBody CoinBuy coinBuy) {
        BigDecimal amountUSD = CoinBuyHelper.convertToUSD(coinBuy.getAmount());
        if (amountUSD.compareTo(BigDecimal.TEN) > 0) {
            Account account = accountService.getAccountById(coinBuy.getAccountID());
            CoinDto coin = coinService.getByTicker(coinBuy.getCoinId());


            AccountDto updatedAccount = accountService.processCoinBuy(amountUSD, coin, account);
            return ResponseEntity.ok(updatedAccount);
        }
        //коли буде працювати фронт - зробити просто валідацію amount
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/fear-and-greed-index")
    public ResponseEntity<FearGreedIndexDto> getFearGreedIndexResponse() {
        FearGreedIndexDto fearGreedIndex = coinService.getFearGreedIndex();
        return ResponseEntity.ok(fearGreedIndex);
    }
}
