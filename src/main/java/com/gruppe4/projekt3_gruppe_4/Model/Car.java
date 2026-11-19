package com.gruppe4.projekt3_gruppe_4.Model;

import java.math.BigDecimal;

public class Car {

    private int carId;
    private String vin;
    private int carModelId;
    private String trimLevel;
    private int mileage;
    private BigDecimal basePrice;
    private BigDecimal co2Emission;
    private String licensePlate;
    private int currentLocationId;

    public Car(String vin, int carModelId, String trimLevel,
               int mileage, BigDecimal basePrice,
               BigDecimal co2Emission, String licensePlate,
               int currentLocationId) {
        this.vin = vin;
        this.carModelId = carModelId;
        this.trimLevel = trimLevel;
        this.mileage = mileage;
        this.basePrice = basePrice;
        this.co2Emission = co2Emission;
        this.licensePlate = licensePlate;
        this.currentLocationId = currentLocationId;


    }
    public Car() {
    }

    public int getCarId() {
        return carId;
    }

    public String getVin() {
        return vin;
    }

    public int getCarModelId() {
        return carModelId;
    }

    public String getTrimLevel() {
        return trimLevel;
    }

    public int getMileage() {
        return mileage;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getCo2Emission() {
        return co2Emission;
    }

    public int getCurrentLocationId() {
        return currentLocationId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setTrimLevel(String trimLevel) {
        this.trimLevel = trimLevel;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public void setCo2Emission(BigDecimal co2Emission) {
        this.co2Emission = co2Emission;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setCurrentLocationId(int currentLocationId) {
        this.currentLocationId = currentLocationId;
    }
}
