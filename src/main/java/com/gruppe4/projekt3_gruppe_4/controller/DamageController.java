package com.gruppe4.projekt3_gruppe_4.controller;

import com.gruppe4.projekt3_gruppe_4.model.Contract;
import com.gruppe4.projekt3_gruppe_4.model.Damage;
import com.gruppe4.projekt3_gruppe_4.model.DamageReport;
import com.gruppe4.projekt3_gruppe_4.model.User;
import com.gruppe4.projekt3_gruppe_4.service.CarService;
import com.gruppe4.projekt3_gruppe_4.service.ContractService;
import com.gruppe4.projekt3_gruppe_4.service.CustomerService;
import com.gruppe4.projekt3_gruppe_4.service.DamageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DamageController {
    private final DamageService damageService;
    private final ContractService contractService;
    private final CarService carService;
    private final CustomerService customerService;

    public DamageController(DamageService damageService, ContractService contractService, CarService carService, CustomerService customerService) {
        this.damageService = damageService;
        this.contractService = contractService;
        this.carService = carService;
        this.customerService = customerService;
    }

    @GetMapping("/damage-reports")
    public String showDamageReports(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        model.addAttribute("contracts", contractService.findEndedContractsWithoutReport());
        model.addAttribute("reports", damageService.findAllReports());
        return "damage-reports";
    }

    @GetMapping("/damage-reports/{reportId}")
    public String showDamageReport(@PathVariable int reportId, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";
        }
        DamageReport report = damageService.findReportByReportId(reportId);
        if (report == null) {
            return "redirect:/damage-reports";
        }

        addReportPageData(model, report);
        return "damage-report";
    }

    private void addReportPageData(Model model, DamageReport report) {
        int reportId = report.getId();
        Contract contract = contractService.findContractById(report.getContractId());

        Damage damage = new Damage();
        damage.setDamageReportId(reportId);

        model.addAttribute("report", report);
        model.addAttribute("contract", contract);
        model.addAttribute("car", carService.findById(contract.getCarId()));
        model.addAttribute("customer", customerService.findById(contract.getCustomerId()));
        model.addAttribute("damages", damageService.findDamagesByReportId(reportId));
        model.addAttribute("totalCost", damageService.calculateTotalCost(reportId));
        model.addAttribute("damage", damage);
        model.addAttribute("pendingContracts", contractService.findEndedContractsWithoutReport());
        model.addAttribute("endDate", contractService.calculateEndDate(contract));
    }

    @PostMapping("/damage-reports/create")
    public String createDamageReport(@RequestParam int contractId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        try {
            int reportId = damageService.createDamageReport(contractId, user.getId());
            return "redirect:/damage-reports/" + reportId;
        } catch (IllegalStateException e) {
            return "redirect:/damage-reports";
        }

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
            return "redirect:/damage-reports/" + damage.getDamageReportId();
        }
    }
}
