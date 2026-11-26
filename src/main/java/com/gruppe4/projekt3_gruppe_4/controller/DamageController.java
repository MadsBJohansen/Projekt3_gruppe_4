package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.model.Damage;
import com.gruppe4.projekt3_gruppe_4.model.DamageReport;
import com.gruppe4.projekt3_gruppe_4.model.User;
import com.gruppe4.projekt3_gruppe_4.service.ContractService;
import com.gruppe4.projekt3_gruppe_4.service.DamageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DamageController {
    private final DamageService damageService;
    private final ContractService contractService;

    public DamageController(DamageService damageService, ContractService contractService) {
        this.damageService = damageService;
        this.contractService = contractService;
    }

    @GetMapping("/damage-reports")
    public String showDamageReports(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        model.addAttribute("contracts", contractService.findAllEndedContracts());
        model.addAttribute("reports", damageService.findAllReports());
        return "damage-reports";
    }

    @GetMapping("/damage-reports/{reportId}")
    public String showDamageReport(@PathVariable int reportId, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        DamageReport report = damageService.findReportById(reportId);
        if (report == null) {
            return "redirect:/damage-reports";
        }
        model.addAttribute("report", damageService.findReportById(reportId));
        model.addAttribute("damages", damageService.findDamagesByReportId(reportId));
        return "damage-report";
    }

    @PostMapping("/damage-reports/create")
    public String createDamageReport(@RequestParam int contractId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        try {
            int reportId = damageService.createDamageReport(contractId, user.getId());
            return "redirect:/damage/add/" + reportId;
        } catch (IllegalStateException e) {
            return "redirect:/damage-reports";
        }

    }

    @GetMapping("/damage/add/{reportId}")
    public String showDamageForm(@PathVariable int reportId, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        if (damageService.findReportById(reportId) == null) {
            return "redirect:/damage-reports";
        }
        Damage damage = new Damage();
        damage.setDamageReportId(reportId);
        model.addAttribute("damage", damage);
        return "add-damage";
    }

    @PostMapping("/damage/add")
    public String addDamage(@ModelAttribute("damage")Damage damage, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        try {
            damageService.addDamage(damage);
            return "redirect:/damage-reports/" + damage.getDamageReportId();
        } catch (IllegalArgumentException e ) {
            model.addAttribute("error", e.getMessage());
            return "add-damage";
        }
    }
}
