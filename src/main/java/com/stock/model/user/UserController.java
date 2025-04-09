package com.stock.model.user;

import com.stock.dto.accountDtos.UserShortDto;
import com.stock.model.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<Void> changeUserData(@RequestBody UserShortDto userShortDto,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getFullUserByEmail(userDetails.getUsername());

        user.setName(userShortDto.getUsername());
        if(!userShortDto.getEmail().equals(user.getEmail())) {
            user.setEmail(userShortDto.getEmail());
            user.setConfirmEmail(false);
        }
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }
}
