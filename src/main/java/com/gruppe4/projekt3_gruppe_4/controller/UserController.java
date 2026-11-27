package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.model.Car;
import com.gruppe4.projekt3_gruppe_4.model.Customer;
import com.gruppe4.projekt3_gruppe_4.model.ZipCode;
import com.gruppe4.projekt3_gruppe_4.service.*;
import com.gruppe4.projekt3_gruppe_4.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final CarService carService;
    private final CustomerService customerService;
    private final LocationService locationService;
    private final ZipCodeService zipCodeService;

    public UserController(CarService carService,
                          CustomerService customerService,
                          LocationService locationService,
                          ZipCodeService zipCodeService) {
        this.carService = carService;
        this.customerService = customerService;
        this.locationService = locationService;
        this.zipCodeService = zipCodeService;
    }

    @GetMapping("/user/user-page")
    public String showUserPage(@RequestParam(defaultValue = "home") String tab,
                               Model model, HttpSession session) {
        if(session.getAttribute("user") == null){
            return "redirect:/";
        }
        model.addAttribute("tab", tab);
        model.addAttribute("car", new Car());
        model.addAttribute("customer", new Customer());
        model.addAttribute("zipCodes", zipCodeService.findAllZipCodes());
        model.addAttribute("locations", locationService.findAllLocations());
        model.addAttribute("cars", carService.findAllCars());


        if (tab.equals("customers")) {
            model.addAttribute("customers", customerService.findAll());
        }
        return "user-page";
    }
}
