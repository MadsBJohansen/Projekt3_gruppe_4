package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.model.Contract;
import com.gruppe4.projekt3_gruppe_4.repository.ContractRepo;
import com.gruppe4.projekt3_gruppe_4.repository.DamageRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContractService {
    private final ContractRepo contractRepo;
    private final DamageRepo damageRepo;

    public ContractService(ContractRepo contractRepo, DamageRepo damageRepo) {
        this.contractRepo = contractRepo;
        this.damageRepo = damageRepo;
    }

    public LocalDate calculateEndDate(Contract contract) {
        int period = contract.getPeriod();
        LocalDate startDate = contract.getStartDate();
        return startDate.plusMonths(period);
    }

    public boolean isContractEnded(Contract contract) {
        return !calculateEndDate(contract).isAfter(LocalDate.now());
    }

    public List<Contract> findAllContracts () {
        return contractRepo.findAllContracts();
    }

    public List<Contract> findAllEndedContracts() {
        List<Contract> endedContracts = new ArrayList<>();
        for (Contract c : findAllContracts()) {
            if (isContractEnded(c)) {
                endedContracts.add(c);
            }
        }

        return endedContracts;
    }

    public List<Contract> findEndedContractsWithoutReport() {
        List<Contract> result = new ArrayList<>();
        for (Contract c : findAllEndedContracts()) {
            if (damageRepo.findReportByContractId(c.getId()) == null) {
                result.add(c);
            }
        }
        return result;
    }

    public Contract findContractById(int contractId) {
        return contractRepo.findContractById(contractId);
    }
}
