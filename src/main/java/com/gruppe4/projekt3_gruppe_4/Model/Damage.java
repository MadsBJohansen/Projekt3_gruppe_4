package com.gruppe4.projekt3_gruppe_4.Model;

import java.math.BigDecimal;

public class Damage {
    private int damageId;
    private int damageReportId;
    private String description;
    private BigDecimal repairCost;

    public Damage(int damageId, int damageReportId, String description, BigDecimal repairCost) {
        this.damageId = damageId;
        this.damageReportId = damageReportId;
        this.description = description;
        this.repairCost = repairCost;
    }

    public BigDecimal getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(BigDecimal repairCost) {
        this.repairCost = repairCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDamageReportId() {
        return damageReportId;
    }

    public void setDamageReportId(int damageReportId) {
        this.damageReportId = damageReportId;
    }

    public int getDamageId() {
        return damageId;
    }

    public void setDamageId(int damageId) {
        this.damageId = damageId;
    }
}
