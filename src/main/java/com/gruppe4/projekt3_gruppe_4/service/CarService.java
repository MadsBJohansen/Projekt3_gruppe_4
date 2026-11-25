package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.model.Car;
import com.gruppe4.projekt3_gruppe_4.repository.CarRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CarService {

    private final CarRepo carRepo;

    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @Transactional
    public void createCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car information is required.");
        }

        if (car.getVin() == null || car.getVin().isBlank()) {
            throw new IllegalArgumentException("VIN is required.");
        }

        if (car.getVin().length() != 17) {
            throw new IllegalArgumentException(
                    "VIN must contain exactly 17 characters."
            );
        }

        if (car.getLicensePlate() == null || car.getLicensePlate().isBlank()) {
            throw new IllegalArgumentException("License plate is required.");
        }

        if (car.getBrand() == null || car.getBrand().isBlank()) {
            throw new IllegalArgumentException("Brand is required.");
        }

        if (car.getModel() == null || car.getModel().isBlank()) {
            throw new IllegalArgumentException("Model is required.");
        }

        if (car.getTrimLevel() == null || car.getTrimLevel().isBlank()) {
            throw new IllegalArgumentException("Trim level is required.");
        }

        if (car.getCurrentLocationId() <= 0) {
            throw new IllegalArgumentException("A location must be selected.");
        }

        if (car.getMileage() < 0) {
            throw new IllegalArgumentException("Mileage must not be negative.");
        }

        if (car.getBasePrice() == null
                || car.getBasePrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Base price must be greater than zero.");
        }

        if (car.getCo2Emission() == null
                || car.getCo2Emission().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("CO2 emission must not be negative.");
        }

        if (carRepo.existsByVin(car.getVin())) {
            throw new IllegalArgumentException(
                    "A car with this VIN is already registered."
            );
        }

        if (carRepo.existsByLicensePlate(car.getLicensePlate())) {
            throw new IllegalArgumentException(
                    "A car with this license plate is already registered."
            );
        }

        carRepo.saveCar(car);
        carRepo.saveInitialStatus(car.getId());
    }
}
