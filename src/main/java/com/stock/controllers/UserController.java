package com.stock.controllers;

import com.stock.model.User;
import com.stock.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public String getUserPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email: %s not found", userEmail)));

        model.addAttribute("user",user);
        model.addAttribute("isAuthenticated", true);
        return "user-main";
    }
}
