package com.stock.controllers;

import com.stock.model.account.Account;
import com.stock.model.user.User;
import com.stock.service.AccountService;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/api/user/account")
public class AccountOldController {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public AccountOldController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @PostMapping("")
    public String createAccount(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam String accountName) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        accountService.createAccount(accountName, user);
        return "redirect:/api/v1/user";
    }

    @GetMapping("/{accountID}")
    public String getData(@PathVariable Long accountID,
                          @AuthenticationPrincipal UserDetails userDetails,
                          Model model) {
        Account account = accountService.getAccountById(accountID);
        model.addAttribute("account", account);
        model.addAttribute("isAuthenticated", true);
        return "account";
    }

    @PostMapping("/deposit")
    @Transactional
    public String deposit(@RequestParam("deposit_amount") String depositAmount,
                          @RequestParam("account_id") String accountID,
                          @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        long deposit = Long.parseLong(depositAmount);
        long accountId = Long.parseLong(accountID);

//        accountService.depositToAccountById(user, accountId, BigDecimal.valueOf(deposit));
        return "redirect:/api/v1/user/account/" + accountID;
    }
}
