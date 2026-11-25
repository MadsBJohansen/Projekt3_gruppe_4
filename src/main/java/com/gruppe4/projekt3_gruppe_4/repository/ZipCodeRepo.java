package com.gruppe4.projekt3_gruppe_4.repository;

import com.gruppe4.projekt3_gruppe_4.model.ZipCode;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ZipCodeRepo {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ZipCode> zipCodeRowMapper = (rs, rowNum) -> (new ZipCode(
            rs.getInt("id"),
            rs.getString("zip_code"),
            rs.getString("city")
    ));

    public ZipCodeRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ZipCode findByZipCode(String zipCode){
        String sql = "SELECT city FROM zip_code WHERE zip_code = ?";
        try {
           return jdbcTemplate.queryForObject(sql, zipCodeRowMapper, zipCode);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
