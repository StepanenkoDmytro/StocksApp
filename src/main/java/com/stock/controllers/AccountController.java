package com.stock.controllers;

import com.stock.helper.GenAccountNumber;
import com.stock.model.account.Account;
import com.stock.model.account.AccountType;
import com.stock.model.account.Transact;
import com.stock.model.user.User;
import com.stock.repository.account.AccountRepository;
import com.stock.repository.account.TransactRepository;
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
@RequestMapping("/api/v1/user/account")
public class AccountController {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final TransactRepository transactRepository;
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountRepository accountRepository, UserService userService,
                             TransactRepository transactRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.transactRepository = transactRepository;
        this.accountService = accountService;
    }

    @PostMapping("")
    public String createAccount(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam String account_name) {
        Account account = new Account();
        User user = userService.getUserByEmail(userDetails.getUsername());

        account.setAccount_name(account_name);
        account.setAccount_type(AccountType.CryptoWallet);

        account.setAccount_number(GenAccountNumber.generateAccountNumber());

        user.addAccount(account);
        userService.saveUser(user);
        return "redirect:/api/v1/user";
    }

    @GetMapping("/{account_id}")
    public String getData(@PathVariable Long account_id,
                          @AuthenticationPrincipal UserDetails userDetails,
                          Model model) {
        Account account = accountRepository.findById(account_id).get();
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

        accountService.depositToAccount(user, accountId, BigDecimal.valueOf(deposit));
        return "redirect:/api/v1/user/account/" + accountID;
    }
}
