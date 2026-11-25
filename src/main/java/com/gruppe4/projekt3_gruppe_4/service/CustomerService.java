package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.exceptions.CustomerException;
import com.gruppe4.projekt3_gruppe_4.model.Customer;
import com.gruppe4.projekt3_gruppe_4.repository.CustomerRepo;
import org.springframework.stereotype.Service;

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

    public void createCustomer(String firstName,
                               String lastName,
                               String email,
                               String phoneNumber,
                               String streetAddress,
                               String zipCode) throws CustomerException {
        if (doesEmailExist(email)){
            throw new CustomerException("Email already exists!");
        }
        if (zipCodeService.findByZipCode(zipCode) == null){
            throw new CustomerException("INVALID zip code!");
        }
        int zipCodeId = zipCodeService.findByZipCode(zipCode).getZipCodeId();
        customerRepo.saveCustomer(
                new Customer(
                        firstName,
                        lastName,
                        email,phoneNumber,
                        streetAddress,
                        zipCodeId));
        }


    public boolean doesEmailExist(String email){
        return customerRepo.findByEmail(email) != null;
    }
}