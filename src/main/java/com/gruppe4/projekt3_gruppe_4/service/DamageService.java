package com.gruppe4.projekt3_gruppe_4.service;
import com.gruppe4.projekt3_gruppe_4.model.Contract;
import com.gruppe4.projekt3_gruppe_4.model.Damage;
import com.gruppe4.projekt3_gruppe_4.model.DamageReport;
import com.gruppe4.projekt3_gruppe_4.repository.ContractRepo;
import com.gruppe4.projekt3_gruppe_4.repository.DamageRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DamageService {
    private final DamageRepo damageRepo;
    private final ContractService contractService;
    private final ContractRepo contractRepo;


    public DamageService(DamageRepo damageRepo, ContractService contractService, ContractRepo contractRepo) {
        this.damageRepo = damageRepo;
        this.contractService = contractService;
        this.contractRepo = contractRepo;
    }

    @Transactional
    public int createDamageReport(int contractId, int UserId) {
        Contract contract = contractRepo.findContractById(contractId);
        if (contract == null ||!contractService.isContractEnded(contract)) {
            throw new IllegalStateException("Contract has not ended");
        }
        DamageReport damageReport = new DamageReport(contractId, UserId, LocalDate.now());
        return damageRepo.saveReport(damageReport);
    }

    public void addDamage(Damage damage) {

        if (damage.getRepairCost() == null || damage.getRepairCost().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Repair cost cannot be negative");
        }
        damageRepo.saveDamage(damage);
    }

    public DamageReport findReportByContractId(int contractId) {
        return damageRepo.findReportByContractId(contractId);
    }

    public DamageReport findReportByReportId(int reportId) {
        return damageRepo.findReportByReportId(reportId);
    }

    public List<Damage> findDamagesByReportId(int reportId) {
        return damageRepo.findDamagesByReportId(reportId);
    }

    public List<DamageReport> findAllReports() {
        return damageRepo.findAllReports();
    }

    public BigDecimal calculateTotalCost(int reportId) {
        BigDecimal total = BigDecimal.ZERO;
        for (Damage d: findDamagesByReportId(reportId)) {
            total = total.add(d.getRepairCost());
        }
        return total;
    }
}
