package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.enums.CarStatus;
import com.gruppe4.projekt3_gruppe_4.model.Car;
import com.gruppe4.projekt3_gruppe_4.repository.CarRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Service
public class CarService {

    private final CarRepo carRepo;

    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    public List<Car> findAllCars() {
        return carRepo.findAllCars();
    }

    public void updateCar(Car car) {
        carRepo.update(car);
    }

    public void changeStatus(int carId, CarStatus status) {
        carRepo.saveStatus(carId, status.name());
    }

    public Car findById(int id) {
        Car car = carRepo.findById(id);
        if (car == null) throw new IllegalArgumentException("Car not found");
        return car;
    }

    public String getCurrentStatus(int carId) {
        return carRepo.findCurrentStatus(carId);
    }


    @Transactional
    public void createCar(Car car) {
        validateRequiredFields(car);
        validateCarDetails(car);
        checkDuplicates(car);

        carRepo.saveCar(car);
        carRepo.saveInitialStatus(car.getId());
    }

    private void validateRequiredFields(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car information is required.");
        }

        if (isMissing(car.getVin())) {
            throw new IllegalArgumentException("VIN is required.");
        }

        if (isMissing(car.getLicensePlate())) {
            throw new IllegalArgumentException("License plate is required.");
        }

        if (isMissing(car.getBrand())) {
            throw new IllegalArgumentException("Brand is required.");
        }

        if (isMissing(car.getModel())) {
            throw new IllegalArgumentException("Model is required.");
        }

        if (isMissing(car.getTrimLevel())) {
            throw new IllegalArgumentException("Trim level is required.");
        }

        if (car.getCurrentLocationId() <= 0) {
            throw new IllegalArgumentException("A location must be selected.");
        }
    }

    private void validateCarDetails(Car car) {
        if (car.getVin().length() != 17) {
            throw new IllegalArgumentException(
                    "VIN must contain exactly 17 characters."
            );
        }

        if (!car.getLicensePlate().matches("[A-Z]{2}[0-9]{5}")) {
            throw new IllegalArgumentException("License plate must contain 2 letters and 5 digits."
            );
        }

        if (car.getYear() < Year.now().getValue() - 5) {
            throw new IllegalArgumentException("The car must not be more than 5 years old.");
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
    }

    private void checkDuplicates(Car car) {
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
    }

    private boolean isMissing(String value) {
        return value == null || value.isBlank();
    }
}
