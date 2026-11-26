package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.model.ZipCode;
import com.gruppe4.projekt3_gruppe_4.repository.ZipCodeRepo;
import org.springframework.stereotype.Service;

@Service
public class ZipCodeService {
    private final ZipCodeRepo zipCodeRepo;

    public ZipCodeService(ZipCodeRepo zipCodeRepo) {
        this.zipCodeRepo = zipCodeRepo;
    }

    public ZipCode findByZipCode(String zipCode){
        return zipCodeRepo.findByZipCode(zipCode);
    }

    public ZipCode findById(int id){
        return zipCodeRepo.findById(id);
    }
}
