package com.gruppe4.projekt3_gruppe_4.Service;

import com.gruppe4.projekt3_gruppe_4.Model.User;
import com.gruppe4.projekt3_gruppe_4.Repo.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean createUser(User user, String rawPassword) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            return false;
        }

        if (user.getUsername() == null || user.getUsername().isBlank() ||
        rawPassword == null || rawPassword.isBlank()) {
            return false;
        }

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
