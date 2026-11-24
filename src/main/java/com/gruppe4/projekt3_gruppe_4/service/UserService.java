package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.model.User;
import com.gruppe4.projekt3_gruppe_4.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String hashPassword(String rawpassword) {
        return encoder.encode(rawpassword);
    }

    public boolean createUser(User user, String rawPassword) {

        user.setPasswordHash(hashPassword(rawPassword));
        userRepo.saveUser(user);

        return true;
    }

    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (user != null && encoder.matches(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }
}
