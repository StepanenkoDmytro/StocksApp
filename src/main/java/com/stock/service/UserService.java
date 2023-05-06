package com.stock.service;

import com.stock.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(long id);
    void registration(User user);
    void saveUser(User user);
    void deleteUser(long id);
}
