package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.enums.CarStatus;
import com.gruppe4.projekt3_gruppe_4.model.Car;
import com.gruppe4.projekt3_gruppe_4.repository.CarRepo;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {
    private final CarRepo carRepo;

    public StatisticsService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    public List<Car> getCarsByStatus(String status) {
        return carRepo.findCarsByStatus(status);
    }

    public Map<CarStatus, Integer> getCarCountPerStatus() {
        Map<CarStatus, Integer> result = new EnumMap<>(CarStatus.class);
        for (CarStatus status : CarStatus.values()) {
            result.put(status, carRepo.countCarsByStatus(status.name()));
        }
        return result;
    }

    public List<Car> getAllCars() {
        return carRepo.findAllCars();
    }
}
