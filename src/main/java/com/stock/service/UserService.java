package com.stock.service;

import com.stock.model.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getFullUserByEmail(String email);
    User getUserByEmail(String email);
    Optional<User> getUserById(long id);
    boolean isUserExistByEmail(String email);
    void registration(User user);
    void saveUser(User user);
    void deleteUser(long id);
}
