package com.gruppe4.projekt3_gruppe_4.Repo;

import com.gruppe4.projekt3_gruppe_4.Model.Customer;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CustomerRepo {
    private final DataSource dataSource;

    public CustomerRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveCustomer(Customer customer){
        String sql = "INSERT INTO customer (first_name, last_name, email, phone_number, street_address, zip_code_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPhoneNumber());
            statement.setString(5, customer.getStreetAddress());
            statement.setInt(6, customer.getZipCode().getZipCodeId());
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Could not save customer", e);
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM customer WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not check email", e);
        }
    }
}
