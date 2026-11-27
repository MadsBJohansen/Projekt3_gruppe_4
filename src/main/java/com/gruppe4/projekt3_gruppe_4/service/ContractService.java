package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.enums.CarStatus;
import com.gruppe4.projekt3_gruppe_4.exceptions.ContractException;
import com.gruppe4.projekt3_gruppe_4.model.Contract;
import com.gruppe4.projekt3_gruppe_4.repository.CarRepo;
import com.gruppe4.projekt3_gruppe_4.repository.ContractRepo;
import com.gruppe4.projekt3_gruppe_4.repository.DamageRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContractService {
    private final ContractRepo contractRepo;
    private final DamageRepo damageRepo;
    private final CarRepo carRepo;
    private final CarService carService;


    @Transactional
    public void createContract (Contract contract) {
        if (contract.getCustomerId() <= 0 ) {
            throw new ContractException("A customer must be chosen");
        }
        if (contract.getCarId() <= 0) {
            throw new ContractException("A car must be chosen");
        }
        if (contract.getLocationId() <= 0 ) {
            throw new ContractException("A location must be chosen");
        }
        if (contract.getUserId() <= 0 ) {
            throw new ContractException("Must be logged in");
        }
        if (contract.getStartDate() == null) {
            throw new ContractException("Start date is required");
        }
        if (contract.getPeriod() != 3 && contract.getPeriod() != 6 && contract.getPeriod() != 12) {
            throw new ContractException("Period must be 3, 6 or 12 months");
        }
        if (contract.getMonthlyPrice() == null || contract.getMonthlyPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new ContractException("Monthly price cannot be negative");
        }

        String status = carService.getCurrentStatus(contract.getCarId());
        if (!"AVAILABLE".equals(status)) {
            throw new ContractException("The car is not available");
        }

        contractRepo.saveContract(contract);
        carService.changeStatus(contract.getCarId(), CarStatus.RENTED);
    }

    public ContractService(ContractRepo contractRepo, DamageRepo damageRepo, CarRepo carRepo, CarService carService) {
        this.contractRepo = contractRepo;
        this.damageRepo = damageRepo;
        this.carRepo = carRepo;
        this.carService = carService;
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
}
