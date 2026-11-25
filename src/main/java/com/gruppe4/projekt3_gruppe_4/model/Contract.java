package com.gruppe4.projekt3_gruppe_4.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Contract {

private int id;
private int customerId;
private int carId;
private int locationId;
private int userId;
private LocalDate startDate;
private LocalDate carReturnDate;
private LocalDateTime canceledAt;
private int period;
private BigDecimal monthlyPrice;

    public Contract(int id, int customerId, int carId, int locationId, int userId, LocalDate startDate,
                LocalDate carReturnDate, LocalDateTime canceledAt, int period, BigDecimal monthlyPrice) {
    this.id = id;
    this.customerId = customerId;
    this.carId = carId;
    this.locationId = locationId;
    this.userId = userId;
    this.startDate = startDate;
    this.carReturnDate = carReturnDate;
    this.canceledAt = canceledAt;
    this.period = period;
    this.monthlyPrice = monthlyPrice;
    }

    public Contract() {
    }

    public LocalDate getContractEndDate() {
        return startDate.plusMonths(period);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getCarReturnDate() {
        return carReturnDate;
    }

    public void setCarReturnDate(LocalDate carReturnDate) {
        this.carReturnDate = carReturnDate;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(LocalDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
}
