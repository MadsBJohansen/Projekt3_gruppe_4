package com.gruppe4.projekt3_gruppe_4.repository;

import com.gruppe4.projekt3_gruppe_4.model.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    }

    public void saveInitialStatus(int carId) {
        String sql = """
            INSERT INTO car_status_log (car_id, status)
            VALUES (?, ?)
            """;

        jdbcTemplate.update(sql, carId, "AVAILABLE");
    }

    public List<Car> findAll() {
        String sql = """
                SELECT car.*,
                    (
                        SELECT status
                        FROM car_status_log
                        WHERE car_id = car.id
                        ORDER BY changed_at DESC, id DESC
                        LIMIT 1
                    ) AS status
                FROM car
                ORDER BY car.id DESC
                """;

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Car car = new Car(
                    resultSet.getString("vin"),
                    resultSet.getString("trim_level"),
                    resultSet.getInt("mileage"),
                    resultSet.getBigDecimal("base_price"),
                    resultSet.getBigDecimal("co2_emission"),
                    resultSet.getString("license_plate"),
                    resultSet.getInt("current_location_id"),
                    resultSet.getString("brand"),
                    resultSet.getString("model"),
                    resultSet.getInt("year")
            );

            car.setId(resultSet.getInt("id"));
            car.setStatus(resultSet.getString("status"));

            return car;
        });
    }
}
