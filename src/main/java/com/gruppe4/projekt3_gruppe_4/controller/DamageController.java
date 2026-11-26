package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.model.Damage;
import com.gruppe4.projekt3_gruppe_4.repository.DamageRepo;
import com.gruppe4.projekt3_gruppe_4.service.DamageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DamageController {
    private DamageService damageService;

    public DamageController(DamageService damageService) {
        this.damageService = damageService;
    }

    @GetMapping("/damage-reports/{reportId}")
    public String showDamageReport(@PathVariable int reportId, Model model) {
        model.addAttribute("report", damageService.findReportById(reportId));
        model.addAttribute("damages", damageService.findDamagesByReportId(reportId));
        return "damage-report";
    }

    @GetMapping("/damage-report")
    public String showDamageReports(Model model) {
        model.addAttribute("reports", damageService.findAllReports());
        return "damage-reports";
    }

    @PostMapping("/damage-reports/create")
    public String createDamageReport(@RequestParam int contractId, HttpSession session) {
        int userId = (int) session.getAttribute("userid");
        int reportId = damageService.createDamageReport(contractId, userId);
        return "redirect:/damage/add/" + reportId;

    }

    @GetMapping("/damage/add/{reportId}")
    public String showDamageForm(@PathVariable int reportId, Model model) {
        Damage damage = new Damage();
        damage.setDamageReportId(reportId);
        model.addAttribute("description", damage);
        return "add-damage";
    }

    @PostMapping("/damage/add")
    public String addDamage(@ModelAttribute("damage")Damage damage, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirecet:/damage-report";
        }
        damageService.addDamage(damage);
        return "redirect:/damage-reports/" + damage.getDamageReportId();
    }

}
