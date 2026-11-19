package com.gruppe4.projekt3_gruppe_4.Repo;

import com.gruppe4.projekt3_gruppe_4.Model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepo {
    private final DataSource dataSource;

    public UserRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRoleId(rs.getInt("role_id"));
                    return user;
                }
                return null;


            }

        } catch (SQLException e) {
            throw new RuntimeException("Kunne ikke hente bruger", e);
        }
    }

    public void saveUser(User user) {
        String sql ="INSERT INTO user(first_name, last_name, username, password_hash, role_id) VALUES(?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPasswordHash());
            ps.setInt(5, user.getRoleId());
            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
