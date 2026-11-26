package com.gruppe4.projekt3_gruppe_4.model;

import java.math.BigDecimal;

public class damage {
    private int id;
    private int damageReportId;
    private String description;
    private BigDecimal repairCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDamageReportId() {
        return damageReportId;
    }

    public void setDamageReportId(int damageReportId) {
        this.damageReportId = damageReportId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(BigDecimal repairCost) {
        this.repairCost = repairCost;
    }

    public damage(int id, int damageReportId, String description, BigDecimal repairCost) {
        this.id = id;
        this.damageReportId = damageReportId;
        this.description = description;
        this.repairCost = repairCost;
    }
}
