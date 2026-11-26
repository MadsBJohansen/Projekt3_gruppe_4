package com.gruppe4.projekt3_gruppe_4.repository;

import com.gruppe4.projekt3_gruppe_4.model.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationRepo {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Location> locationRowMapper = (rs, rowNum) ->
            new Location(rs.getInt("id"),
    rs.getString("name"),
    rs.getString("street_address"),
            rs.getInt("zip_code_id"));

    public LocationRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Location> findAllLocations() {
        String sql = "SELECT * FROM location";
        return jdbcTemplate.query(sql, locationRowMapper);
    }

    public Location findById(int id){
        String sql = "SELECT * FROM location WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, locationRowMapper ,id);
    }
}
