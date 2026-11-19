package com.gruppe4.projekt3_gruppe_4.Repo;

import com.gruppe4.projekt3_gruppe_4.Model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserRepo {
    private final DataSource dataSource;

    public UserRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findByUsername(String username) {
       String sql = "SELECT FROM user WHERE username = ?";
       try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {



       } catch (SQLException e) {

       }
       return null;
    }
}
