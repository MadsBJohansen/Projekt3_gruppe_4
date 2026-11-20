
package com.gruppe4.projekt3_gruppe_4.Repo;

import com.gruppe4.projekt3_gruppe_4.Model.Car;
import com.gruppe4.projekt3_gruppe_4.enums.CarStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CarRepo {

    private final JdbcTemplate jdbcTemplate;

    public CarRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateCarStatus(int carId, CarStatus status){
        String sql = """
                    INSERT INTO status_change(car_id, car_status_id, changed_at)
                    SELECT ?, car_status_id, NOW()
                    FROM car_status
                    WHERE status_code = ?
                    """;
        jdbcTemplate.update(sql,carId, status);

    }

    public int saveCar(Car car) {

        String sql = """
                INSERT INTO car (
                    vin,
                    car_model_id,
                    trim_level,
                    mileage,
                    base_price,
                    co2_emission,
                    license_plate,
                    current_location_id
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, car.getVin());
            ps.setInt(2, car.getCarModelId());
            ps.setString(3, car.getTrimLevel());
            ps.setInt(4, car.getMileage());
            ps.setBigDecimal(5, car.getBasePrice());
            ps.setBigDecimal(6, car.getCo2Emission());
            ps.setString(7, car.getLicensePlate());
            ps.setInt(8, car.getCurrentLocationId());

            return ps;

        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new IllegalStateException(
                    "No generated car ID returned"
            );
        }

        return key.intValue();
    }
}
