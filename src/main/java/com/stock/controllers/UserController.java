package com.stock.controllers;

import com.stock.exceptions.ImageNotFoundException;
import com.stock.model.User;
import com.stock.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public String getUserPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String userEmail = userDetails.getUsername();
        User user = userService.getUserByEmail(userEmail);

        model.addAttribute("user", user);
        model.addAttribute("isAuthenticated", true);
        return "user-main";
    }

    @PostMapping("/upload-photo")
    public String addPhoto(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam("file") MultipartFile file) {
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
