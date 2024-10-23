package com.stock.model.user.service;


import com.stock.model.user.portfolio.entities.Portfolio;
import com.stock.model.role.Role;
import com.stock.model.role.Status;
import com.stock.model.user.User;
import com.stock.model.role.repository.RoleRepository;
import com.stock.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getFullUserByEmail(String email) {
        return userRepository.findFullByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email: %s not found", email)));
    }

    public User getUserByEmailWithException(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email: %s not found", email)));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public boolean isUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void registration(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);

        user.addPortfolio(new Portfolio());
        user.setStatus(Status.ACTIVE);
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
