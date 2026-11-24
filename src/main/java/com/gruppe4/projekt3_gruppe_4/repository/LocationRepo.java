package com.gruppe4.projekt3_gruppe_4.repository;

import com.gruppe4.projekt3_gruppe_4.model.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationRepo {

    private final JdbcTemplate jdbcTemplate;

    public LocationRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Location> findAll() {
        String sql = "SELECT id, name FROM location ORDER BY name";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Location(
                        rs.getInt("id"),
                        rs.getString("name")
                )
        );
    }
}
