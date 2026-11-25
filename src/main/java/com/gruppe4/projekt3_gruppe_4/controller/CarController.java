package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.model.Car;
import com.gruppe4.projekt3_gruppe_4.repository.LocationRepo;
import com.gruppe4.projekt3_gruppe_4.service.CarService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CarController {

    private final CarService carService;
    private final LocationRepo locationRepo;

    public CarController(CarService carService, LocationRepo locationRepo) {
        this.carService = carService;
        this.locationRepo = locationRepo;
    }

    @GetMapping("/car/user-page")
    public String showCreateCarForm(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("locations", locationRepo.findAll());
        model.addAttribute("cars", carService.findAllCars());

        return "user-page";
    }

    @PostMapping("/car/cars/create")
    public String createCar(
            @ModelAttribute("car") Car car,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error",
                    "Enter valid numbers for mileage, price, CO2 emission, and year.");
            model.addAttribute("openCreateCar", true);
            model.addAttribute("locations", locationRepo.findAll());
            model.addAttribute("cars", carService.findAllCars());

            return "user-page";
        }
        try {
            carService.createCar(car);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("openCreateCar", true);
            model.addAttribute("locations", locationRepo.findAll());
            model.addAttribute("cars", carService.findAllCars());

            return "user-page";
        }

        return "redirect:/user-page";
    }
}
