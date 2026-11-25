package com.gruppe4.projekt3_gruppe_4.repository;

import com.gruppe4.projekt3_gruppe_4.model.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepo {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Car> carRowMapper = ((rs, rowNum) ->
            new Car(rs.getInt("id"),
                    rs.getString("vin"),
                    rs.getString("trim_level"),
                    rs.getInt("mileage"),
                    rs.getBigDecimal("base_price"),
                    rs.getBigDecimal("co2_emission"),
                    rs.getString("license_plate"),
                    rs.getInt("current_location_id"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getInt("year")));

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

    public List<Car> findAllCars() {
        String sql = "SELECT * FROM car ORDER BY id";
        return jdbcTemplate.query(sql, carRowMapper);
    }

    public List<Car> findCarsByStatus(String status) {
        String sql = "SELECT c.* FROM car AS c JOIN car_status AS l ON l.car_id = c.id" +
                " WHERE l.status = ?" +
                " AND l.changed_at = (SELECT MAX(l2.changed_at)" +
                " FROM car_status_log AS l2 WHERE l2.car_id = c.id)";

        return jdbcTemplate.query(sql, carRowMapper, status);
    }

    public int countCarsByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM car AS c JOIN car_status AS l ON l.car_id = c.id" +
                " WHERE l.status = ?" +
                " AND l.changed_at = (SELECT MAX(l2.changed_at)" +
                " FROM car_status_log AS l2 WHERE l2car.car_id = c.id";

        return jdbcTemplate.queryForObject(sql, Integer.class, status);
    }
}
