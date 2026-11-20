package com.gruppe4.projekt3_gruppe_4.Model;

import java.time.LocalDate;

public class DamageReport {
    private int damageReportId;
    private int rentalAgreementId;
    private int userId;
    private LocalDate reportDate;

    public int getDamageReportId() {
        return damageReportId;
    }

    public void setDamageReportId(int damageReportId) {
        this.damageReportId = damageReportId;
    }

    public int getRentalAgreementId() {
        return rentalAgreementId;
    }

    public void setRentalAgreementId(int rentalAgreementId) {
        this.rentalAgreementId = rentalAgreementId;
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

    public DamageReport(int damageReportId, int rentalAgreementId, int userId, LocalDate reportDate) {
        this.damageReportId = damageReportId;
        this.rentalAgreementId = rentalAgreementId;
        this.userId = userId;
        this.reportDate = reportDate;
    }
}
