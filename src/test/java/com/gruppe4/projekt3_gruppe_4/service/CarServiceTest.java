
package com.gruppe4.projekt3_gruppe_4.service;

import com.gruppe4.projekt3_gruppe_4.model.Car;
import com.gruppe4.projekt3_gruppe_4.repository.CarRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepo carRepo;

    @InjectMocks
    private CarService carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car(
                "WVWZZZ1JZXW000001",
                "Comfortline",
                25000,
                new BigDecimal("2500.00"),
                new BigDecimal("120.0"),
                "AB12345",
                1,
                "Volkswagen",
                "Golf",
                Year.now().getValue()
        );
    }

    @Test
    void shouldCreateCarWithInitialStatus() {
        // Arrange
        when(carRepo.existsByVin(car.getVin()))
                .thenReturn(false);

        when(carRepo.existsByLicensePlate(car.getLicensePlate()))
                .thenReturn(false);

        doAnswer(_ -> {
            car.setId(1);
            return null;
        }).when(carRepo).saveCar(car);

        // Act
        carService.createCar(car);

        // Assert
        assertEquals(1, car.getId());
        verify(carRepo).saveCar(car);
        verify(carRepo).saveInitialStatus(1);
    }

    @Test
    void shouldRejectDuplicateVin() {
        // Arrange
        when(carRepo.existsByVin(car.getVin()))
                .thenReturn(true);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> carService.createCar(car)
        );

        // Assert
        assertEquals(
                "A car with this VIN is already registered.",
                exception.getMessage()
        );

        verify(carRepo, never()).saveCar(car);
        verify(carRepo, never()).saveInitialStatus(anyInt());
    }

    @Test
    void shouldRejectDuplicateLicensePlate() {
        // Arrange
        when(carRepo.existsByVin(car.getVin()))
                .thenReturn(false);

        when(carRepo.existsByLicensePlate(car.getLicensePlate()))
                .thenReturn(true);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> carService.createCar(car)
        );

        // Assert
        assertEquals(
                "A car with this license plate is already registered.",
                exception.getMessage()
        );

        verify(carRepo, never()).saveCar(car);
        verify(carRepo, never()).saveInitialStatus(anyInt());
    }

    @Test
    void shouldRejectInvalidVin() {
        // Arrange
        car.setVin("12345");

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> carService.createCar(car)
        );

        // Assert
        assertEquals(
                "VIN must contain exactly 17 characters.",
                exception.getMessage()
        );

        verify(carRepo, never()).saveCar(car);
        verify(carRepo, never()).saveInitialStatus(anyInt());
    }
    @Test
    void shouldRejectInvalidLicensePlate() {
        // Arrange
        car.setLicensePlate("TOO-LONG");

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> carService.createCar(car)
        );

        // Assert
        assertEquals(
                "License plate must contain 2 letters and 5 digits.",
                exception.getMessage()
        );

        verify(carRepo, never()).saveCar(car);
        verify(carRepo, never()).saveInitialStatus(anyInt());
    }

    @Test
    void shouldRejectMissingBasePrice() {
        assertInvalidPrice(null);
    }

    @Test
    void shouldRejectZeroBasePrice() {
        assertInvalidPrice(BigDecimal.ZERO);
    }

    @Test
    void shouldRejectNegativeBasePrice() {
        assertInvalidPrice(new BigDecimal("-0.01"));
    }

    private void assertInvalidPrice(BigDecimal price) {
        car.setBasePrice(price);
        assertThrows(IllegalArgumentException.class, () -> carService.createCar(car));
        verify(carRepo, never()).saveCar(any());
        verify(carRepo, never()).saveInitialStatus(anyInt());
    }
}
