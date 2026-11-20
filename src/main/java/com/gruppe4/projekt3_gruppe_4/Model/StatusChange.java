package com.gruppe4.projekt3_gruppe_4.Model;

import java.time.LocalDate;

public class StatusChange {
    private int statusChangeId;
    private int carId;
    private int carStatusId;
    private LocalDate changedAt;

    public StatusChange(int statusChangeId, int carId, int carStatusId, LocalDate changedAt) {
        this.statusChangeId = statusChangeId;
        this.carId = carId;
        this.carStatusId = carStatusId;
        this.changedAt = changedAt;
    }

    public int getStatusChangeId() {
        return statusChangeId;
    }

    public int getCarId() {
        return carId;
    }

    public int getCarStatusId() {
        return carStatusId;
    }

    public LocalDate getChangedAt() {
        return changedAt;
    }

    public void setStatusChangeId(int statusChangeId) {
        this.statusChangeId = statusChangeId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setCarStatusId(int carStatusId) {
        this.carStatusId = carStatusId;
    }

    public void setChangedAt(LocalDate changedAt) {
        this.changedAt = changedAt;
    }
}
