package com.gruppe4.projekt3_gruppe_4.model;

public class Location {

    private int id;
    private String name;
    private String streetAddress;
    private int zipCodeId;

    public Location(int id, String name, String streetAddress, int zipCodeId) {
        this.id = id;
        this.name = name;
        this.streetAddress = streetAddress;
        this.zipCodeId = zipCodeId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public int getZipCodeId() {
        return zipCodeId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setZipCodeId(int zipCodeId) {
        this.zipCodeId = zipCodeId;
    }
}
