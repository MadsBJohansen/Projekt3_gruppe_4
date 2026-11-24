package com.gruppe4.projekt3_gruppe_4.model;

public class ZipCode {
    private int zipCodeId;
    private String zipCode;
    private String city;

    public ZipCode(int zipCodeId, String zipCode, String city) {
        this.zipCodeId = zipCodeId;
        this.zipCode = zipCode;
        this.city = city;
    }

    public int getZipCodeId() {
        return zipCodeId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setZipCodeId(int zipCodeId) {
        this.zipCodeId = zipCodeId;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }
}