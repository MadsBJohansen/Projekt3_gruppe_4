package com.gruppe4.projekt3_gruppe_4.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/user-page")
    public String showUserPage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }

        return "redirect:/car/user-page";
    }
}
