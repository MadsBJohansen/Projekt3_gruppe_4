package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.exceptions.CustomerException;
import com.gruppe4.projekt3_gruppe_4.model.Customer;
import com.gruppe4.projekt3_gruppe_4.model.ZipCode;
import com.gruppe4.projekt3_gruppe_4.service.CustomerService;
import com.gruppe4.projekt3_gruppe_4.service.ZipCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final ZipCodeService zipCodeService;

    public CustomerController(CustomerService customerService, ZipCodeService zipCodeService) {
        this.customerService = customerService;
        this.zipCodeService = zipCodeService;
    }

    @GetMapping("/customer/{customerId}/showCustomer")
    public String showCustomer(@PathVariable int customerId,
                               Model model){
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);
        model.addAttribute("zipCode", zipCodeService.findById(customer.getZipCodeId()));
        return "customer";
    }
    @PostMapping("/customer/createCustomer")
    public String createCustomer(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String email,
                                 @RequestParam String phoneNumber,
                                 @RequestParam String streetAddress,
                                 @RequestParam String zipCode,
                                 Model model){
        try {
            customerService.createCustomer(
                    firstName,
                    lastName,
                    email,
                    phoneNumber,
                    streetAddress,
                    zipCode);
            return "redirect:/user/user-page";

        } catch (CustomerException e){
            model.addAttribute("error", e.getMessage());
            return "redirect:/user/user-page";
        }
    }
}