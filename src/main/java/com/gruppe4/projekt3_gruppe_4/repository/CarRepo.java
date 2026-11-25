package com.gruppe4.projekt3_gruppe_4.repository;

import com.gruppe4.projekt3_gruppe_4.model.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepo {

    private final JdbcTemplate jdbcTemplate;

    public CarRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByLicensePlate(String licensePlate) {
        String sql = "SELECT COUNT(*) FROM car WHERE license_plate = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, licensePlate
        );

        return count != null && count > 0;
    }

    public boolean existsByVin(String vin) {
        String sql = "SELECT COUNT(*) FROM car WHERE vin = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, vin
        );

        return count != null && count > 0;
    }

    public void saveCar(Car car) {
        String sql = """
                INSERT INTO car (
                    vin, trim_level, mileage, base_price, co2_emission,
                    license_plate, current_location_id, brand, model, year
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                car.getVin(),
                car.getTrimLevel(),
                car.getMileage(),
                car.getBasePrice(),
                car.getCo2Emission(),
                car.getLicensePlate(),
                car.getCurrentLocationId(),
                car.getBrand(),
                car.getModel(),
                car.getYear()
        );

        Integer id = jdbcTemplate.queryForObject(
                "SELECT id FROM car WHERE vin = ?",
                Integer.class,
                car.getVin()
        );
        if (id == null) {
            throw new IllegalStateException(
                    "Could not retrieve the car ID after saving."
            );
        }

        car.setId(id);
    }public void saveInitialStatus(int carId) {
        String sql = """
            INSERT INTO car_status_log (car_id, status)
            VALUES (?, ?)
            """;

        jdbcTemplate.update(sql, carId, "AVAILABLE");
    }
}