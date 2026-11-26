package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.model.Location;
import com.gruppe4.projekt3_gruppe_4.repository.LocationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepo locationRepo;

    public LocationService(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    public List<Location> findAllLocations() {
        return locationRepo.findAllLocations();
    }

    public Location findById(int id){
        return locationRepo.findById(id);
    }
}