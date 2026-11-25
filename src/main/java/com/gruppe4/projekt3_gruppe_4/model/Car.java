package com.gruppe4.projekt3_gruppe_4.model;

import java.math.BigDecimal;

public class Car {
    private int id;
    private String vin;
    private String trimLevel;
    private int mileage;
    private BigDecimal basePrice;
    private BigDecimal co2Emission;
    private String licensePlate;
    private int currentLocationId;
    private String brand;
    private String model;
    private int year;

    public Car() {
    }

    public Car(String vin, String trimLevel, int mileage,
               BigDecimal basePrice, BigDecimal co2Emission,
               String licensePlate, int currentLocationId,
               String brand, String model, int year) {
        this.vin = vin;
        this.trimLevel = trimLevel;
        this.mileage = mileage;
        this.basePrice = basePrice;
        this.co2Emission = co2Emission;
        this.licensePlate = licensePlate;
        this.currentLocationId = currentLocationId;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public Car(int id, String vin, String trimLevel, int mileage, BigDecimal basePrice, BigDecimal co2Emission,
               String licensePlate, int currentLocationId, String brand, String model, int year) {
        this.id = id;
        this.vin = vin;
        this.trimLevel = trimLevel;
        this.mileage = mileage;
        this.basePrice = basePrice;
        this.co2Emission = co2Emission;
        this.licensePlate = licensePlate;
        this.currentLocationId = currentLocationId;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getVin() {
        return vin;
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

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getCurrentLocationId() {
        return currentLocationId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setTrimLevel(String trimLevel) {
        this.trimLevel = trimLevel;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public void setCo2Emission(BigDecimal co2Emission) {
        this.co2Emission = co2Emission;
    }

    public void setCurrentLocationId(int currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setModel(String model) {
        this.model = model;
    }
}