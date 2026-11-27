package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.exceptions.CustomerException;
import com.gruppe4.projekt3_gruppe_4.model.Car;
import com.gruppe4.projekt3_gruppe_4.model.Customer;
import com.gruppe4.projekt3_gruppe_4.model.ZipCode;
import com.gruppe4.projekt3_gruppe_4.repository.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final ZipCodeService zipCodeService;

    public CustomerService(CustomerRepo customerRepo, ZipCodeService zipCodeService) {
        this.customerRepo = customerRepo;
        this.zipCodeService = zipCodeService;
    }

    public Customer findById(int id){
        return customerRepo.findById(id);
    }

    public void createCustomer(Customer customer) throws CustomerException {
        if (doesEmailExist(customer.getEmail())){
            throw new CustomerException("Email already exists!");
        }

        customerRepo.saveCustomer(customer);
        }

    public boolean doesEmailExist(String email){
        return customerRepo.findByEmail(email) != null;
    }

    public boolean doesZipCodeExist(String zipCode){
        return zipCodeService.findByZipCode(zipCode) == null;
    }

    public List<Customer> findAll() {
        return customerRepo.findAllCustomers();
    }

    public void updateCustomer(Customer customer){
        customerRepo.update(customer);
    }
}