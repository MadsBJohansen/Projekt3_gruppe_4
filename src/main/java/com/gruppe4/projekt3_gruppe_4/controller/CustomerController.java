package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.exceptions.CustomerException;
import com.gruppe4.projekt3_gruppe_4.model.ZipCode;
import com.gruppe4.projekt3_gruppe_4.service.CustomerService;
import com.gruppe4.projekt3_gruppe_4.service.ZipCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer/createCustomer")
    public String createCustomer(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String email,
                                 @RequestParam String phoneNumber,
                                 @RequestParam String streetAddress,
                                 @RequestParam String zipCode, Model model){
        try {
            customerService.createCustomer(
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    streetAddress,
                    zipCode);
            return "redirect:/user-page";
        } catch (CustomerException e){
            model.addAttribute("error", e.getMessage());
            return "redirect:/user-page";
        }
    }
}