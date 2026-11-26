package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.model.Contract;
import com.gruppe4.projekt3_gruppe_4.repository.ContractRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ContractService {
    private final ContractRepo contractRepo;

    public ContractService(ContractRepo contractRepo) {
        this.contractRepo = contractRepo;
    }

    public LocalDate calculateEndDate(Contract contract) {
        int period = contract.getPeriod();
        LocalDate startDate = contract.getStartDate();
        return startDate.plusMonths(period);
    }

    public boolean isContractEnded(Contract contract) {
        return !calculateEndDate(contract).isAfter(LocalDate.now());
    }
}
