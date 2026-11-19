package com.gruppe4.projekt3_gruppe_4.Service;

import com.gruppe4.projekt3_gruppe_4.Model.User;
import com.gruppe4.projekt3_gruppe_4.Repo.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final UserRepo userRepo;

    public CarService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean CreateUser(User user) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            return false;
        }

        userRepo.saveUser(user);

        return true;
    }

    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            return null;
        }

        if (user.getPasswordHash().equals(password)) {
            return user;
        }

        return null;
    }
}

