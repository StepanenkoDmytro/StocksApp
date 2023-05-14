package com.stock.controllers;

import com.stock.dto.CoinDto;
import com.stock.helper.CoinBuyHelper;
import com.stock.model.account.Account;
import com.stock.service.AccountService;
import com.stock.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/coin")
public class CoinPaymentController {
    private final CoinService coinService;
    private final AccountService accountService;

    @Autowired
    public CoinPaymentController(CoinService coinService, AccountService accountService) {
        this.coinService = coinService;
        this.accountService = accountService;
    }

    @PostMapping("")
    @Transactional
    public String buyCoin(@RequestParam("coin_id") String coin_id,
                          @RequestParam("amount") String amount,
                          @AuthenticationPrincipal UserDetails userDetails) {

        Account account = accountService.getAccountById(1L);
        CoinDto coin = coinService.getByTicker(coin_id);
        BigDecimal amountUSD = CoinBuyHelper.convertToUSD(amount);
        accountService.updateCoinUser(amountUSD, coin, account);

        return "redirect:/api/v1/main/" + coin.getId();
    }
}
