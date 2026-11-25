package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.enums.CarStatus;
import com.gruppe4.projekt3_gruppe_4.service.StatisticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics/cars")
    public String showCarOverview(@RequestParam(required = false) CarStatus status,
                                  HttpSession session, Model model, SessionStatus sessionStatus) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        if (status != null) {
            model.addAttribute("cars", statisticsService.getCarsByStatus(status.name()));
        } else {
            model.addAttribute("cars", statisticsService.getAllCars());
        }

        model.addAttribute("countPerStatus", statisticsService.getCarCountPerStatus());
        model.addAttribute("statuses", CarStatus.values());
        model.addAttribute("selectedStatus", status);
        return "car-overview";
    }
}
