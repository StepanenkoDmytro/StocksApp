package com.stock.controllers;

import com.stock.exceptions.ImageNotFoundException;
import com.stock.model.user.User;
import com.stock.repository.account.AccountRepository;
import com.stock.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AccountRepository accountRepository;

    public UserController(UserService userService, AccountRepository accountRepository) {
        this.userService = userService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public String getUserPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail);
        BigDecimal totalBalance = accountRepository.getTotalBalance(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("isAuthenticated", true);
        return "user-main";
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addPhoto(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam("file") MultipartFile file,
                           Model model) {
        String email = userDetails.getUsername();
        User user = userService.getUserByEmail(email);

        try {
            userService.saveUser(user, file);
        } catch (IOException e) {
            userService.saveUser(user);
            throw new ImageNotFoundException("Image not saved");
        }
        return "redirect:/api/v1/user";
    }
}
