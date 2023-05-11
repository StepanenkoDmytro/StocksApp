package com.stock.controllers;

import com.stock.dto.CoinDto;
import com.stock.helper.CoinBuyHelper;
import com.stock.model.account.Account;
import com.stock.repository.account.AccountCoinRepository;
import com.stock.repository.account.AccountRepository;
import com.stock.repository.account.TransactRepository;
import com.stock.service.CoinService;
import com.stock.service.UserService;
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
    @Transactional
    public String buyCoin(@RequestParam("coin_id") String coin_id,
                          @RequestParam("amount") String amount,
                          @AuthenticationPrincipal UserDetails userDetails) {

        Account account = accountRepository.findById(1L).get();
        CoinDto coin = coinService.getByTicker(coin_id);
        BigDecimal amountUSD = CoinBuyHelper.convertToUSD(amount);
        coinService.updateCoinUser(amountUSD, coin, account);

        return "redirect:/api/v1/main/" + coin.getId();
    }
}
