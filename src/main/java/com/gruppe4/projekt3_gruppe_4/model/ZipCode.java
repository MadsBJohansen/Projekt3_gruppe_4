package com.gruppe4.projekt3_gruppe_4.model;

public class ZipCode {
    private int id;
    private String zipCode;
    private String city;

    public ZipCode(int id, String zipCode, String city) {
        this.id = id;
        this.zipCode = zipCode;
        this.city = city;

    }

    public int getZipCodeId() {
        return id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setZipCodeId(int zipCodeId) {
        this.id = zipCodeId;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }
}