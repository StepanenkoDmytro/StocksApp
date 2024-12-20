package com.stock.model.coin;

import com.stock.model.coin.api.producers.AlphaVantageMarket;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.dto.coins.*;
import com.stock.service.helpers.CoinBuyHelper;
import com.stock.model.user.account.entities.Account;
import com.stock.model.user.account.service.AccountService;
import com.stock.model.coin.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/markets/coins")
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
    public ResponseEntity<CoinDto> getCoinWithCandles(@PathVariable(value = "coin_id") String coinID) {
        CoinDto coin = coinService.getByTicker(coinID);
//        List<CandlesDto> candles = alphaVantageMarket.findCandlesById(coin.getSymbol());
        return ResponseEntity.ok(coin);
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
