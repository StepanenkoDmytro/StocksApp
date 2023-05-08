package com.stock.controllers;

import com.stock.helper.GenAccountNumber;
import com.stock.model.account.Account;
import com.stock.model.account.AccountType;
import com.stock.model.account.Transact;
import com.stock.model.user.Status;
import com.stock.model.user.User;
import com.stock.repository.account.AccountRepository;
import com.stock.repository.account.TransactRepository;
import com.stock.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/user/account")
public class AccountController {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final TransactRepository transactRepository;

    public AccountController(AccountRepository accountRepository, UserService userService,
                             TransactRepository transactRepository) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.transactRepository = transactRepository;
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
    public String deposit(@RequestParam("deposit_amount") String depositAmount,
                          @RequestParam("account_id") String accountID,
                          @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        Long deposit = Long.parseLong(depositAmount);
        Long accountId = Long.parseLong(accountID);

        BigDecimal balance = accountRepository.getAccountBalance(user.getId(), accountId);
        BigDecimal newBalance = balance.add( BigDecimal.valueOf(deposit) );
        Account account = accountRepository.findById(accountId).get();

        accountRepository.changeAccountBalanceById(newBalance, accountId);
        Transact transact = new Transact();
        transact.setTransaction_type("deposit");
        transact.setAmount(newBalance);
        transact.setSource("online");
        transact.setStatus("success");
        transact.setReason_code("Deposit Transaction Successful");
        transact.setAccount(account);
        account.addTransact(transact);
        transactRepository.save(transact);

        return "redirect:/api/v1/user/account/" + accountID;
    }
}
