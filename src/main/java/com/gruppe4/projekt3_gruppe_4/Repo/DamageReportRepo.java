package com.gruppe4.projekt3_gruppe_4.Repo;

import com.gruppe4.projekt3_gruppe_4.Model.Damage;
import com.gruppe4.projekt3_gruppe_4.Model.DamageReport;
import com.gruppe4.projekt3_gruppe_4.Model.RentalAgreement;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DamageReportRepo {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<DamageReport> damageReportRowMapper = (rs, rowNum) -> new DamageReport(
            rs.getInt("damage_report_id"),
            rs.getInt("rental_agreement_id"),
            rs.getInt("user_id"),
            rs.getDate("report_date").toLocalDate());

    public DamageReportRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveDamageReport(DamageReport damageReport) {
        String sql = "INSERT INTO damage_report(" +
                "rental_agreement_id, user_id, report_date) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                damageReport.getRentalAgreementId(),
                damageReport.getUserId(),
                damageReport.getReportDate());
    }

    public DamageReport findReportByRentalAgreementId(int rentalAgreementId) {
        String sql = "SELECT * FROM damage_report WHERE rental_agreement_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, damageReportRowMapper, rentalAgreementId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
