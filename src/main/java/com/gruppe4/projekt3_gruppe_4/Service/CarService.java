package com.gruppe4.projekt3_gruppe_4.Service;

import com.gruppe4.projekt3_gruppe_4.Repo.CarRepo;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final CarRepo carRepo;

    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    public void changeStatus(int carId){

    }
}
