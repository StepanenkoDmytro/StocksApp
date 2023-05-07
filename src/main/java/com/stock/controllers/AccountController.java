package com.stock.controllers;

import com.stock.model.account.Account;
import com.stock.model.account.AccountType;
import com.stock.model.user.Status;
import com.stock.model.user.User;
import com.stock.repository.account.AccountRepository;
import com.stock.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/user/account")
public class AccountController {

    private final AccountRepository accountRepository;
    private final UserService userService;

    public AccountController(AccountRepository accountRepository,UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    @GetMapping("")
    public Account createAccount(@AuthenticationPrincipal UserDetails userDetails){
        Account account = new Account();
        User user = userService.getUserByEmail(userDetails.getUsername());

        account.setAccount_name("Test name account");
        account.setAccount_type(AccountType.CryptoWallet);
        BigDecimal balance = BigDecimal.valueOf(0);
        account.setBalance(balance);
        account.setAccount_number("213712487618");
        account.setStatus(Status.ACTIVE);

        user.addAccount(account);
        userService.saveUser(user);
        return account;
    }
}
