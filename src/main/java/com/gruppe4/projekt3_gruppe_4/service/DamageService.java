package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.repository.DamageRepo;
import org.springframework.stereotype.Service;

@Service
public class DamageService {
    private final DamageRepo damageRepo;

    public DamageService(DamageRepo damageRepo) {
        this.damageRepo = damageRepo;
    }

    public void createDamageReport () {
        
    }
}
