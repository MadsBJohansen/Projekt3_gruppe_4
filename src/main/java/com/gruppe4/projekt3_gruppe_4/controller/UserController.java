package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.model.Customer;
import com.gruppe4.projekt3_gruppe_4.model.User;
import com.gruppe4.projekt3_gruppe_4.service.CarService;
import com.gruppe4.projekt3_gruppe_4.service.CustomerService;
import com.gruppe4.projekt3_gruppe_4.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;
    private final CarService carService;
    private final CustomerService customerService;

    public UserController(UserService userService, CarService carService, CustomerService customerService) {
        this.userService = userService;
        this.carService = carService;
        this.customerService = customerService;
    }

    @GetMapping("/user/user-page")
    public String showUserPage(@RequestParam(defaultValue = "home") String tab,
                               Model model) {
        model.addAttribute("tab", tab);
        if (tab.equals("cars")) {
            model.addAttribute("cars", carService.findAllCars());
        } else if (tab.equals("customers")) {
            model.addAttribute("customers", customerService.findAll());
        }
        return "user-page";
    }
}
