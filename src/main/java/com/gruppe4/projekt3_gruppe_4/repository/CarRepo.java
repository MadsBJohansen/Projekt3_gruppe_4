package com.gruppe4.projekt3_gruppe_4.repository;

import com.gruppe4.projekt3_gruppe_4.model.Car;
import com.gruppe4.projekt3_gruppe_4.model.Location;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Car findById(int id) {
        String sql = "SELECT * FROM car WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, carRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Car car){
        String sql = """
                        UPDATE car
                        SET trim_level = ?,
                            mileage = ?,
                            base_price = ?,
                            co2_emission = ?,
                            license_plate = ?,
                            current_location_id = ?,
                            brand = ?,
                            model = ?,
                            year = ?
                        WHERE id = ?
                     """;
        jdbcTemplate.update(sql,
                car.getTrimLevel(),
                car.getMileage(),
                car.getBasePrice(),
                car.getCo2Emission(),
                car.getLicensePlate(),
                car.getCurrentLocationId(),
                car.getBrand(),
                car.getModel(),
                car.getYear());
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

    public void saveStatus(int carId, String status) {
        String sql = """
                INSERT INTO car_status_log (car_id, status)
                VALUES (?, ?)
                """;
        jdbcTemplate.update(sql, carId, status);
    }

    public List<Car> findAllCars() {
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
                    resultSet.getInt("id"),
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

            car.setStatus(resultSet.getString("status"));
            return car;
        });
    }

    public List<Car> findCarsByStatus(String status) {
        String sql = """
                        SELECT c.* FROM car AS c JOIN car_status_log AS l ON l.car_id = c.id
                        WHERE l.status = ?
                        AND l.changed_at = (SELECT MAX(l2.changed_at)
                        FROM car_status_log AS l2 WHERE l2.car_id = c.id)
                    """;

        return jdbcTemplate.query(sql, carRowMapper, status);
    }

    public String findCurrentStatus(int carId) {
        String sql = """
            SELECT status FROM car_status_log
            WHERE car_id = ?
            ORDER BY changed_at DESC, id DESC
            LIMIT 1
            """;
        try {
            return jdbcTemplate.queryForObject(sql, String.class, carId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int countCarsByStatus(String status) {
        String sql = """
                        SELECT COUNT(*) FROM car AS c JOIN car_status_log AS l ON l.car_id = c.id
                        WHERE l.status = ?
                        AND l.changed_at = (SELECT MAX(l2.changed_at)
                        FROM car_status_log AS l2 WHERE l2.car_id = c.id)
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, status);
        return count != null ? count : 0;
    }
}
