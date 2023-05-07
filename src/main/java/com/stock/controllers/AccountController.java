package com.stock.controllers;

import com.stock.helper.GenAccountNumber;
import com.stock.model.account.Account;
import com.stock.model.account.AccountType;
import com.stock.model.user.Status;
import com.stock.model.user.User;
import com.stock.repository.account.AccountRepository;
import com.stock.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

//        BigDecimal balance = BigDecimal.valueOf(0);
//        account.setBalance(balance);

        account.setAccount_number(GenAccountNumber.generateAccountNumber());
        account.setStatus(Status.ACTIVE);

        user.addAccount(account);
        userService.saveUser(user);
        return account;
    }

    @GetMapping("/{account_id}")
    public String getData(@PathVariable Long account_id,
                          @AuthenticationPrincipal UserDetails userDetails) {
        StringBuilder builder = new StringBuilder();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Account account = accountRepository.findById(account_id).get();
        BigDecimal accountBalance = accountRepository.getAccountBalance(user.getId(), account_id);
        BigDecimal totalBalance = accountRepository.getTotalBalance(user.getId());
        builder.append(account.getAccount_name() + "\n");
        builder.append("Balance from account" + account.getBalance() + "\n");
        builder.append("Balance from method rep" + accountBalance + "\n");
        builder.append("Total balance from method rep" + totalBalance + "\n");
        BigDecimal newBalance = BigDecimal.valueOf(1000);
        accountRepository.changeAccountBalanceById(newBalance,account_id);

        accountBalance = accountRepository.getAccountBalance(user.getId(), account_id);
        totalBalance = accountRepository.getTotalBalance(user.getId());
        builder.append("Balance from method rep after change" + accountBalance + "\n");
        builder.append("Total balance from method rep after change" + totalBalance + "\n");

        return builder.toString();
    }
}
