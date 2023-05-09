package com.stock.rest;

import com.stock.api.CoinMarket;
import com.stock.api.entity.Coin;
import com.stock.dto.CoinDto;
import com.stock.helper.CoinBuyHelper;
import com.stock.model.account.Account;
import com.stock.model.account.AccountCoin;
import com.stock.model.account.Transact;
import com.stock.model.user.User;
import com.stock.repository.account.AccountCoinRepository;
import com.stock.repository.account.AccountRepository;
import com.stock.repository.account.TransactRepository;
import com.stock.service.CoinService;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/coin")
public class CoinPaymentController {
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final CoinService coinService;
    private final AccountCoinRepository accountCoinRepository;
    private final TransactRepository transactRepository;

    @Autowired
    public CoinPaymentController(AccountRepository accountRepository, UserService userService, CoinService coinService, AccountCoinRepository accountCoinRepository,
                                 TransactRepository transactRepository) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.coinService = coinService;
        this.accountCoinRepository = accountCoinRepository;
        this.transactRepository = transactRepository;
    }

    @PostMapping("")
    public String buyCoin(@RequestParam("coin_id") String coin_id,
                          @RequestParam("amount") String amount,
                          @AuthenticationPrincipal UserDetails userDetails) {

        Account account = accountRepository.findById(1L).get();
//        User user = userService.getUserByEmail(userDetails.getUsername());
        CoinDto coin = coinService.getByTicker(coin_id);
        BigDecimal amountUSD = CoinBuyHelper.convertToUSD(amount);
        coinService.updateCoinUser(amountUSD, coin, account);





        return "redirect:/api/v1/main/" + coin.getId();
    }
}
