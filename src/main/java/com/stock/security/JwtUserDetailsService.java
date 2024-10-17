package com.stock.security;

import com.stock.model.user.User;
import com.stock.model.user.service.UserService;
import com.stock.security.jwt.JwtUserFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException(
                    String.format("User with username: %s not found", username));
        }

        log.info("In loadUserByUsername - user with username {} successfully loaded", username);
        return JwtUserFactory.create(user);
    }
}
