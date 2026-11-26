package com.gruppe4.projekt3_gruppe_4.repository;

import com.gruppe4.projekt3_gruppe_4.model.Damage;
import com.gruppe4.projekt3_gruppe_4.model.DamageReport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DamageRepo {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Damage> damageRowMapper = ((rs, rowNum) ->
            new Damage(
                    rs.getInt("id"),
                    rs.getInt("damage_report_id"),
                    rs.getString("description"),
                    rs.getBigDecimal("repair_cost")));
    private final RowMapper<DamageReport> damageReportRowMapper = (rs, rowNum) ->
            new DamageReport(
                    rs.getInt("id"),
                    rs.getInt("contract_id"),
                    rs.getInt("user_id"),
                    rs.getDate("report_date").toLocalDate());

    public DamageRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveDamage(Damage damage) {
        String sql = "INSERT INTO damage (damage_report_id, description, repair_cost) VALUES ?,?,?";
        jdbcTemplate.update(sql,
                    damage.getDamageReportId(),
                    damage.getDescription(),
                    damage.getRepairCost());
        }
}
