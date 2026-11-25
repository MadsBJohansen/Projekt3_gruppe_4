package com.gruppe4.projekt3_gruppe_4.repository;

import com.gruppe4.projekt3_gruppe_4.enums.Role;
import com.gruppe4.projekt3_gruppe_4.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class UserRepo {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper = (rs, rowNum) ->
            new User(rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    Role.valueOf(rs.getString("role")));

    public UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void saveUser(User user) {
        String sql = "INSERT INTO user(first_name, last_name, username, password_hash, role) VALUES (?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUsername(),
                    user.getPasswordHash(),
                    user.getRole());
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Could not save user", e);
        }
    }
}
