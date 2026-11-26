package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.model.Car;
import com.gruppe4.projekt3_gruppe_4.service.LocationService;
import com.gruppe4.projekt3_gruppe_4.service.CarService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Year;

@Controller
public class CarController {

    private final CarService carService;
    private final LocationService locationService;

    public CarController(CarService carService, LocationService locationService) {
        this.carService = carService;
        this.locationService = locationService;
    }

    @GetMapping("/car/{carId}/showCar")
    public String showCar(Model model, @PathVariable int carId){

        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "car";
    }

    @PostMapping("/car/updateCar")
    public String updateCar(@ModelAttribute Car car, Model model){
        try {
            carService.updateCar(car);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/car/showCar";
        }
        return "redirect:/car/showCar";
    }

    @GetMapping("/car/user-page")
    public String showCreateCarForm(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }


        model.addAttribute("car", new Car());
        model.addAttribute("locations", locationService.findAllLocations());
        model.addAttribute("cars", carService.findAllCars());

        return "user-page";
    }

    @PostMapping("/car/cars/create")
    public String createCar(
            @ModelAttribute("car") Car car,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }



        if (bindingResult.hasErrors()) {
            model.addAttribute("error",
                    "Enter valid numbers for mileage, price, CO2 emission, and year.");
            model.addAttribute("openCreateCar", true);
            model.addAttribute("locations", locationService.findAllLocations());
            model.addAttribute("cars", carService.findAllCars());

            return "user-page";
        }
        try {
            carService.createCar(car);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("openCreateCar", true);
            model.addAttribute("locations", locationService.findAllLocations());
            model.addAttribute("cars", carService.findAllCars());

            return "user-page";
        }

        return "redirect:/car/user-page";
    }
}
