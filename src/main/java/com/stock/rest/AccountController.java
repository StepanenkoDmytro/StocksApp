package com.stock.rest;

import com.stock.dto.UserDto;
import com.stock.model.user.User;
import com.stock.service.AccountService;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin
public class AccountController {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public AccountController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

//    @GetMapping("")
//    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal UserDetails userDetails) {
//        User user = userService.getUserByUsername(userDetails.getUsername());
//        return ResponseEntity.ok(new UserDto(user.getUsername()));
//    }
}
