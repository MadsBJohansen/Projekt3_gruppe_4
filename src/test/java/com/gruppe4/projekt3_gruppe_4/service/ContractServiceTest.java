package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.enums.CarStatus;
import com.gruppe4.projekt3_gruppe_4.exceptions.ContractException;
import com.gruppe4.projekt3_gruppe_4.model.Contract;
import com.gruppe4.projekt3_gruppe_4.repository.ContractRepo;
import com.gruppe4.projekt3_gruppe_4.repository.DamageRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @Mock
    private ContractRepo contractRepo;

    @Mock
    private DamageRepo damageRepo;

    @Mock
    private CarService carService;

    private ContractService contractService;

    @BeforeEach
    void setUp() {
        contractService = new ContractService(contractRepo, damageRepo, carService);
    }

    @Test
    void shouldCreateAvailableContract() {
        Contract contract = validContract();
        when(carService.getCurrentStatus(contract.getCarId())).thenReturn("AVAILABLE");

        contractService.createContract(contract);

        verify(contractRepo).saveContract(contract);
        verify(carService).changeStatus(contract.getCarId(), CarStatus.RENTED);
    }

    @Test
    void shouldRejectMissingCustomer() {
        Contract contract = validContract();
        contract.setCustomerId(0);

        assertRejected(contract, "A customer must be chosen");
        verifyNoInteractions(carService);
    }

    @Test
    void shouldRejectMissingCar() {
        Contract contract = validContract();
        contract.setCarId(0);

        assertRejected(contract, "A car must be chosen");
        verifyNoInteractions(carService);
    }

    @Test
    void shouldRejectMissingLocation() {
        Contract contract = validContract();
        contract.setLocationId(0);

        assertRejected(contract, "A location must be chosen");
        verifyNoInteractions(carService);
    }

    @Test
    void shouldRejectMissingUser() {
        Contract contract = validContract();
        contract.setUserId(0);

        assertRejected(contract, "Must be logged in");
        verifyNoInteractions(carService);
    }

    @Test
    void shouldRejectMissingStartDate() {
        Contract contract = validContract();
        contract.setStartDate(null);

        assertRejected(contract, "Start date is required");
        verifyNoInteractions(carService);
    }

    @Test
    void shouldRejectUnsupportedPeriod() {
        Contract contract = validContract();
        contract.setPeriod(9);

        assertRejected(contract, "Period must be 3, 6 or 12 months");
        verifyNoInteractions(carService);
    }

    @Test
    void shouldRejectMissingMonthlyPrice() {
        Contract contract = validContract();
        contract.setMonthlyPrice(null);

        assertRejected(contract, "Monthly price is required");
        verifyNoInteractions(carService);
    }

    @Test
    void shouldRejectNegativeMonthlyPrice() {
        Contract contract = validContract();
        contract.setMonthlyPrice(new BigDecimal("-0.01"));

        assertRejected(contract, "Monthly price cannot be negative");
        verifyNoInteractions(carService);
    }

    @Test
    void shouldRejectUnavailableCar() {
        Contract contract = validContract();
        when(carService.getCurrentStatus(contract.getCarId())).thenReturn("RENTED");

        assertRejected(contract, "The car is not available");

        verify(contractRepo, never()).saveContract(contract);
        verify(carService, never()).changeStatus(contract.getCarId(), CarStatus.RENTED);
    }

    private void assertRejected(Contract contract, String expectedMessage) {
        ContractException exception = assertThrows(
                ContractException.class,
                () -> contractService.createContract(contract)
        );

        assertEquals(expectedMessage, exception.getMessage());
        verify(contractRepo, never()).saveContract(contract);
    }

    private Contract validContract() {
        Contract contract = new Contract();
        contract.setCustomerId(1);
        contract.setCarId(2);
        contract.setLocationId(1);
        contract.setUserId(4);
        contract.setStartDate(LocalDate.of(2026, 11, 27));
        contract.setPeriod(6);
        contract.setMonthlyPrice(new BigDecimal("3999.00"));
        return contract;
    }
}
