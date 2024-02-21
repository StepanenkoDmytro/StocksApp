package com.stock.security.jwt;

import com.stock.model.user.Role;
import com.stock.model.user.Status;
import com.stock.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                mapToGrantedAuthority(user.getRoles()),
                user.getUpdated()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
