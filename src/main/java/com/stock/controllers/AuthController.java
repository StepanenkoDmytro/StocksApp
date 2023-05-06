package com.stock.controllers;

import com.stock.model.User;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, Model model, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "registration";
        }
        Optional<User> userFromDb = userService.getUserByEmail(user.getEmail());
        if (userFromDb.isPresent()) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        userService.registration(user);
        return "redirect:/api/v1/auth/login";
    }
}
