package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.exceptions.CustomerException;
import com.gruppe4.projekt3_gruppe_4.model.Customer;
import com.gruppe4.projekt3_gruppe_4.model.ZipCode;
import com.gruppe4.projekt3_gruppe_4.service.CustomerService;
import com.gruppe4.projekt3_gruppe_4.service.ZipCodeService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                               Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            return "redirect:/";
        }

        Customer customer = customerService.findById(customerId);
        model.addAttribute("zipCodes", zipCodeService.findAllZipCodes());
        model.addAttribute("customer", customer);
        model.addAttribute("zipCode", zipCodeService.findById(customer.getZipCodeId()));
        return "customer";
    }

    @PostMapping("/customer/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer,
                                 Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            return "redirect:/";
        }
        try {
            customerService.createCustomer(customer);
            return "redirect:/customer/showCustomers";

        } catch (CustomerException e){
            model.addAttribute("error", e.getMessage());
            return "customers";
        }
    }

    @PostMapping("/customer/updateCustomer")
    public String updateCustomer(@ModelAttribute Customer customer, Model model, HttpSession session){

        if(session.getAttribute("user") == null){
            return "redirect:/";
        }

        try {
            customerService.updateCustomer(customer);
            return "redirect:/customer/" + customer.getId() + "/showCustomer";

        } catch (CustomerException e) {
            if (session.getAttribute("user") == null){
            model.addAttribute("error", e.getMessage());
            }
            return "customer";
        }
    }

    @PostMapping("/customer/archiveCustomer")
    public String archiveCustomer(@RequestParam int customerId, Model model, HttpSession session){
        if (session.getAttribute("user") == null){
            return "redirect:/";
        }
        try {
            Customer customer = customerService.findById(customerId);
            customerService.archiveCustomer(customer);
            return "redirect:/customer/" + customerId +"/showCustomer";

        }catch (CustomerException e){
            Customer customer = customerService.findById(customerId);
            model.addAttribute("zipCodes", zipCodeService.findAllZipCodes());
            model.addAttribute("customer", customer);
            model.addAttribute("zipCode", zipCodeService.findById(customer.getZipCodeId()));
            model.addAttribute("error", e.getMessage());
            return "customer";
        }

    }

    @GetMapping("/customer/showCustomers")
    public String showCustomers(Model model, HttpSession session){
        if (session.getAttribute("user") == null){
            return "redirect:/";
        }

        try {
            model.addAttribute("customers", customerService.findAllCustomers());
            model.addAttribute("customer", new Customer());
            model.addAttribute("zipCodes", zipCodeService.findAllZipCodes());
            return "customers";

        } catch (CustomerException e) {
            model.addAttribute("error", e.getMessage());
            return "customers";
        }

    }

//    @GetMapping("/customer/showDeletedCustomers")
//    public String showDeletedCustomers(Model model, HttpSession session){
//        if (session.getAttribute("user") == null){
//            return "redirect:/";
//        }
//
//        try {
//            model.addAttribute("customers", customerService.findAllDeletedCustomers());
//            model.addAttribute("customer", new Customer());
//            model.addAttribute("zipCodes", zipCodeService.findAllZipCodes());
//            return "customers";
//
//        } catch (CustomerException e) {
//            model.addAttribute("error", e.getMessage());
//            return "customers";
//        }
//
//    }
}