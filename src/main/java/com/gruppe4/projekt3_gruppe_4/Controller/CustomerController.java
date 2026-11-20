package com.gruppe4.projekt3_gruppe_4.Controller;


import com.gruppe4.projekt3_gruppe_4.Model.Customer;
import com.gruppe4.projekt3_gruppe_4.Service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public String showCreateCustomerForm(Model model) {
        model.addAttribute("zipCodes", customerService.getAllZipCodes());
        return "createCustomer";
    }

    @PostMapping("/customer")
    public String createCustomer(
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone_number") String phoneNumber,
            @RequestParam("street_address") String streetAddress,
            @RequestParam("zip_code") String zipCode,
            Model model) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setStreetAddress(streetAddress);

        try {
            customerService.createCustomer(customer, zipCode);
            model.addAttribute("success", "Customer created");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("zipCodes", customerService.getAllZipCodes());
        return "createCustomer";
    }
}



