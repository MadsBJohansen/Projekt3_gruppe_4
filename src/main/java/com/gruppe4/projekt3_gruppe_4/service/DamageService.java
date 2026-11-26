package com.gruppe4.projekt3_gruppe_4.service;
import com.gruppe4.projekt3_gruppe_4.model.Contract;
import com.gruppe4.projekt3_gruppe_4.model.Damage;
import com.gruppe4.projekt3_gruppe_4.model.DamageReport;
import com.gruppe4.projekt3_gruppe_4.repository.ContractRepo;
import com.gruppe4.projekt3_gruppe_4.repository.DamageRepo;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private final JdbcTemplate jdbcTemplate;

    public DamageService(DamageRepo damageRepo, ContractService contractService, ContractRepo contractRepo, JdbcTemplate jdbcTemplate) {
        this.damageRepo = damageRepo;
        this.contractService = contractService;
        this.contractRepo = contractRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int createDamageReport(int contractId, int UserId) {
        Contract contract = contractRepo.findContractById(contractId);
        if (contract == null ||!contractService.isContractEnded(contract)) {
            throw new RuntimeException("Contract has not ended");
        }
        DamageReport damageReport = new DamageReport(contractId, UserId, LocalDate.now());
        return damageRepo.saveReport(damageReport);
    }

    public void addDamage(Damage damage) {

        if (damage.getRepairCost() == null || damage.getRepairCost().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Repair cost cannot be negative");
        }
        damageRepo.saveDamage(damage);
    }

    public DamageReport findReportById(int id) {
        return damageRepo.findReportByContractId(id);
    }

    public List<Damage> findDamagesByReportId(int reportId) {
        return damageRepo.findDamagesByReportId(reportId);
    }

    public List<DamageReport> findAllReports() {
        return damageRepo.findAllReports();
    }
}
