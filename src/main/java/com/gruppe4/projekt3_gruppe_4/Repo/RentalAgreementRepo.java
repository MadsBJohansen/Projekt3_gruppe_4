package com.gruppe4.projekt3_gruppe_4.Repo;
import com.gruppe4.projekt3_gruppe_4.Model.RentalAgreement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class RentalAgreementRepo {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<RentalAgreement> rentalAgreementRowMapper = (rs, rowNum) ->
            new RentalAgreement (rs.getInt("rental_agreement_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("car_id"),
                    rs.getInt("location_id"),
                    rs.getInt("user_id"),
                    rs.getInt("rental_periode_id"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("return_date").toLocalDate(),
                    rs.getBigDecimal("monthly_price"));

    public RentalAgreementRepo(JdbcTemplate jdbcTemplate, RowMapper<RentalAgreement> rentalAgreementRowMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.rentalAgreementRowMapper = rentalAgreementRowMapper;
    }

    public List<RentalAgreement> findAll(){
        String sql = "SELECT * FROM rental_agreement";
        return jdbcTemplate.query(sql, rentalAgreementRowMapper);
    }

    public int findRentalPeriodeById(int rentalAgreementId){
        String sql = """
                        SELECT rental_period.months
                        FROM rental_agreement
                        JOIN rental_period
                        ON rental_agreement.rental_period_id = rental_period.rental_period_id
                        WHERE rental_agreement.rental_agreement_id = ?
                     """;
        return jdbcTemplate.queryForObject(sql, Integer.class, rentalAgreementId);

    }

    public List<RentalAgreement> findByCarId(int carId){
        String sql = "SELECT * FROM rental_agreement WHERE car_id = ?";
        return jdbcTemplate.query(sql, rentalAgreementRowMapper, carId);
    }

    public RentalAgreement findById(int rentalAgreementId){
        String sql = "SELECT * FROM rental_agreement WHERE rental_agreement_id = ?";
        return jdbcTemplate.queryForObject(sql, rentalAgreementRowMapper, rentalAgreementId);
    }

    public void saveRentalAgreement(RentalAgreement rentalAgreement){
        String sql ="""
                    INSERT INTO rental_agreement(
                                                 customer_id,
                                                 car_id,
                                                 location_id,
                                                 user_id,
                                                 rental_period_id,
                                                 start_date,
                                                 return_date,
                                                 monthly_price)
                    VALUES (?,?,?,?,?,?,?,?)""";
        jdbcTemplate.update(
                sql,
                rentalAgreement.getCustomerId(),
                rentalAgreement.getCarId(),
                rentalAgreement.getLocationId(),
                rentalAgreement.getUserId(),
                rentalAgreement.getRentalPeriodId(),
                rentalAgreement.getStartDate(),
                rentalAgreement.getReturnDate(),
                rentalAgreement.getMonthlyPrice()
        );
    }

}