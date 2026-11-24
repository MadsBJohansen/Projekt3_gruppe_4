package com.gruppe4.projekt3_gruppe_4.repository;

import com.gruppe4.projekt3_gruppe_4.model.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepo {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> customerRowMapper = (rs, numRow) -> new Customer(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("phone_number"),
            rs.getString("street_address"),
            rs.getInt("zip_code_id"));

    public CustomerRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveCustomer(Customer customer){
        String sql = """
                        INSERT INTO customer(
                                             first_name,
                                             last_name,
                                             email,
                                             phone_number,
                                             street_address,
                                             zip_code_id)
                        VALUES (?,?,?,?,?,?)
                        """;
        jdbcTemplate.update(sql,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getStreetAddress(),
                customer.getZipCodeId());
    }

    public Customer findByEmail(String email){
        String sql = "SELECT * FROM customer WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, customerRowMapper, email);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}