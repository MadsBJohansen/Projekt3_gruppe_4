package com.gruppe4.projekt3_gruppe_4.Repo;

import org.springframework.jdbc.core.JdbcTemplate;

public class StatusChangeRepo {
    private final JdbcTemplate jdbcTemplate;

    public StatusChangeRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveStatusChange(){

    }
}
