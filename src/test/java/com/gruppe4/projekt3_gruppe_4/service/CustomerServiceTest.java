package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.exceptions.CustomerException;
import com.gruppe4.projekt3_gruppe_4.model.Customer;
import com.gruppe4.projekt3_gruppe_4.repository.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock private CustomerRepo customerRepo;
    @Mock private ZipCodeService zipCodeService;
    @InjectMocks private CustomerService customerService;

    @Test
    void shouldCreateCustomer() {
        Customer customer = validCustomer();
        when(customerRepo.findByEmail(customer.getEmail())).thenReturn(null);

        customerService.createCustomer(customer);

        verify(customerRepo).saveCustomer(customer);
    }

    @Test
    void shouldRejectDuplicateEmail() {
        Customer customer = validCustomer();
        when(customerRepo.findByEmail(customer.getEmail())).thenReturn(customer);

        CustomerException exception = assertThrows(CustomerException.class,
                () -> customerService.createCustomer(customer));

        assertEquals("Email already exists!", exception.getMessage());
        verify(customerRepo, never()).saveCustomer(any());
    }

    private Customer validCustomer() {
        return new Customer("Test", "Customer", "test@example.invalid",
                "12345678", "Test Street 1", 1);
    }
}
