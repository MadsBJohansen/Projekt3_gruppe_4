package com.gruppe4.projekt3_gruppe_4.Repo;


import com.gruppe4.projekt3_gruppe_4.Model.ZipCode;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZipCodeRepo {
    private final DataSource dataSource;

    public ZipCodeRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ZipCode findByZipCode (String zipCode){
        String sql = "SELECT * FROM zip_code WHERE zip_code = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, zipCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ZipCode(resultSet.getInt("zip_code_id"), resultSet.getString("zip_code"),
                            resultSet.getString("city"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("could not load zip code", e);
        }
    }

    public List<ZipCode> findAll() {
        String sql = "SELECT * FROM zip_code ORDER BY zip_code";
        List<ZipCode> zipCodes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                zipCodes.add(new ZipCode(resultSet.getInt("zip_code_id"),
                        resultSet.getString("zip_code"), resultSet.getString("city")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("could not load zip codes", e);
        }
        return zipCodes;
    }
}
