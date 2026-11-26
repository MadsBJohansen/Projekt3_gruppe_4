package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.model.ZipCode;
import com.gruppe4.projekt3_gruppe_4.repository.ZipCodeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ZipCode> findAllZipCodes(){
        return zipCodeRepo.findAllZipCodes();
    }
}
