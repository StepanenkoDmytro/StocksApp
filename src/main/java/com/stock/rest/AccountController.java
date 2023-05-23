package com.stock.rest;

import com.stock.dto.DepositDTO;
import com.stock.dto.NewAccountDto;
import com.stock.dto.accountDtos.AccountDto;
import com.stock.model.user.User;
import com.stock.service.AccountService;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/account/")
@CrossOrigin
public class AccountController {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public AccountController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @PostMapping("create")
    public ResponseEntity createAccount(@RequestBody NewAccountDto accountDto,
                                        @AuthenticationPrincipal UserDetails userDetails){
        User user = userService.getUserByUsername(userDetails.getUsername());
        accountService.createAccount(accountDto.getNewAccountName(), user);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("deposit")
    @Transactional
    public ResponseEntity<AccountDto> deposit(@RequestBody DepositDTO deposit,
                                  @AuthenticationPrincipal UserDetails userDetails){
        User user = userService.getUserByUsername(userDetails.getUsername());
        AccountDto accountDto = accountService.depositToAccountById(user, deposit.getAccountId(), BigDecimal.valueOf(deposit.getDepositAmount()));
        //дописати методу accountService.depositToAccountById якийсь exception, щоб користувач дізнався про помилку
        return ResponseEntity.ok(accountDto);
    }
}
