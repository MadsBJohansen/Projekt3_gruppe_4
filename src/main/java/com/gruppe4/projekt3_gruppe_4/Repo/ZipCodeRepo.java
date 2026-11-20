package com.gruppe4.projekt3_gruppe_4.Repo;


import com.gruppe4.projekt3_gruppe_4.Model.ZipCode;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//@Repository
//public class ZipCodeRepo {
//    private final DataSource dataSource;
//
//    public ZipCodeRepo(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    public ZipCode findByZipCode (String zipCode){
//        String sql = "SELECT * FROM zip_code WHERE zip_code = ?";
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)){
//            statement.setString(1, zipCode);
//            try (ResultSet resultSet = statement.executeQuery())
//        }
//
//    }
//}
