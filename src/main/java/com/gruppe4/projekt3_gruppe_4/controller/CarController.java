package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.model.Car;
import com.gruppe4.projekt3_gruppe_4.repository.LocationRepo;
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
    private final LocationRepo locationRepo;

    public CarController(CarService carService, LocationRepo locationRepo) {
        this.carService = carService;
        this.locationRepo = locationRepo;
    }

    @GetMapping("/car/{carId}/showCar")
    public String showCar(Model model, @PathVariable int carId){

        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "car";
    }

    @PostMapping("/car/editCar")
    public String editCar(@ModelAttribute Car car, Model model){
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
        model.addAttribute("locations", locationRepo.findAll());
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

        return "redirect:/car/user-page";
    }
}
