package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.model.Damage;
import com.gruppe4.projekt3_gruppe_4.repository.ContractRepo;
import com.gruppe4.projekt3_gruppe_4.repository.DamageRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DamageServiceTest {
    @Mock private DamageRepo damageRepo;
    @Mock private ContractRepo contractRepo;
    @Mock private ContractService contractService;
    @InjectMocks private DamageService damageService;

    @Test
    void shouldSaveDamageWithPositiveCost() {
        Damage damage = validDamage();
        damageService.addDamage(damage);
        verify(damageRepo).saveDamage(damage);
    }

    @Test
    void shouldRejectMissingRepairCost() {
        assertInvalidCost(null);
    }

    @Test
    void shouldRejectZeroRepairCost() {
        assertInvalidCost(BigDecimal.ZERO);
    }

    @Test
    void shouldRejectNegativeRepairCost() {
        assertInvalidCost(new BigDecimal("-0.01"));
    }

    private void assertInvalidCost(BigDecimal cost) {
        Damage damage = validDamage();
        damage.setRepairCost(cost);
        assertThrows(IllegalArgumentException.class, () -> damageService.addDamage(damage));
        verify(damageRepo, never()).saveDamage(any());
    }

    private Damage validDamage() {
        return new Damage(0, 1, "Scratch on door", new BigDecimal("400.00"));
    }
}
