
package com.gruppe4.projekt3_gruppe_4.repo;

import com.gruppe4.projekt3_gruppe_4.Model.Car;
import com.gruppe4.projekt3_gruppe_4.Repo.CarRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@Import(CarRepo.class)
@Transactional
class CarRepoTest {

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldSaveCarAndReturnGeneratedId() {

        // Arrange
        Car car = new Car(
                "TEST1234567890123",
                1,
                "Comfort",
                25000,
                new BigDecimal("250000.00"),
                new BigDecimal("120.0"),
                "ZZ98765",
                1
        );

        // Act
        int carId = carRepo.saveCar(car);

        // Assert
        assertTrue(carId > 0);

        String savedVin = jdbcTemplate.queryForObject(
                "SELECT vin FROM car WHERE car_id = ?",
                String.class,
                carId
        );

        assertEquals(car.getVin(), savedVin);
    }
}
