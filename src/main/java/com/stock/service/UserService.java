package com.stock.service;

import com.stock.model.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserByEmail(String email);
    Optional<User> getUserById(long id);
    void registration(User user);
    void saveUser(User user, MultipartFile file) throws IOException;
    void saveUser(User user);
    void deleteUser(long id);
}
