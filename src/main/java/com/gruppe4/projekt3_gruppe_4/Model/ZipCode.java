package com.gruppe4.projekt3_gruppe_4.Model;

public class ZipCode {
    private int zipCodeId;
    private String zipCode;
    private String city;


    public ZipCode (int zipCodeId, String zipCode, String city){
        this.zipCodeId = zipCodeId;
        this.zipCode = zipCode;
        this.city = city;
    }

    public int getZipCodeId() {
        return zipCodeId;
    }

    public void setZipCodeId(int zipCodeId) {
        this.zipCodeId = zipCodeId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
