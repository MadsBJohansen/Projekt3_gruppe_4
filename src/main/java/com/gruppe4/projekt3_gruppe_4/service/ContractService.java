package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.model.Contract;
import com.gruppe4.projekt3_gruppe_4.repository.ContractRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

//    public List<Contract> findAllEndedContractsWithoutDamageReport() {
//        List<Contract> withoutContract = new ArrayList<>();
//        for (Contract c : findAllEndedContracts()) {
//            if (c.)
//        }
//    }
}
