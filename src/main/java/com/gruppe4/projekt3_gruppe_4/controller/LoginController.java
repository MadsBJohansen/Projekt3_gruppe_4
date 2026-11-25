package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.model.User;
import com.gruppe4.projekt3_gruppe_4.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/user/user-page";
        }
        model.addAttribute("error", "invalid username or password");
        return "index";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
