package com.gruppe4.projekt3_gruppe_4.model;

import java.time.LocalDate;

public class DamageReport {
    private int id;
    private int contractId;
    private int userId;
    private LocalDate reportDate;

    public DamageReport(int id, int contractId, int userId, LocalDate reportDate) {
        this.id = id;
        this.contractId = contractId;
        this.userId = userId;
        this.reportDate = reportDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
}
