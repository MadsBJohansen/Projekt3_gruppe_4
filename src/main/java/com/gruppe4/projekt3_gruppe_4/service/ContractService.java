package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.repository.ContractRepo;
import org.springframework.stereotype.Service;

@Service
public class ContractService {
    private final ContractRepo contractRepo;

    public ContractService(ContractRepo contractRepo) {
        this.contractRepo = contractRepo;
    }

    
}
