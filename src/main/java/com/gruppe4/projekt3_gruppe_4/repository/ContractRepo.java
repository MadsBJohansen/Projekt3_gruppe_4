package com.gruppe4.projekt3_gruppe_4.repository;


import com.gruppe4.projekt3_gruppe_4.model.Contract;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Repository
public class ContractRepo {
    private final JdbcTemplate jdbcTemplate;

    public ContractRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Contract> contractRowMapper = (rs, rowNum) ->
            new Contract (rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getInt("car_id"),
                    rs.getInt("location_id"),
                    rs.getInt("user_id"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getObject("car_return_date", LocalDate.class),
                    rs.getObject("canceled_at", LocalDateTime.class),
                    rs.getInt("period"),
                    rs.getBigDecimal("monthly_price"));

    public void saveContract(Contract contract){
        String sql = "INSERT INTO contract (customer_id, car_id, location_id, user_id, start_date, period, monthly_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                contract.getCustomerId(),
                contract.getCarId(),
                contract.getLocationId(),
                contract.getUserId(),
                contract.getStartDate(),
                contract.getPeriod(),
                contract.getMonthlyPrice());

    }

    public Contract findContractById(int id) {
        String sql = "SELECT * FROM contract WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, contractRowMapper, id);
    }

}
