package com.gruppe4.projekt3_gruppe_4.Model;

import java.math.BigDecimal;
import java.util.Date;

public class RentalAgreement {
    private int customerId;
    private int carId;
    private int locationId;
    private int userId;
    private int rentalPeriodId;
    private Date startDate;
    private Date returnDate;
    private BigDecimal monthlyPrice;

    public RentalAgreement(int customerId,
                           int carId,
                           int locationId,
                           int userId,
                           int rentalPeriodId,
                           Date startDate,
                           Date returnDate,
                           BigDecimal monthlyPrice) {
        this.customerId = customerId;
        this.carId = carId;
        this.locationId = locationId;
        this.userId = userId;
        this.rentalPeriodId = rentalPeriodId;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.monthlyPrice = monthlyPrice;
    }

    public RentalAgreement(int customerId,
                           int carId,
                           int locationId,
                           int userId,
                           int rentalPeriodId,
                           Date startDate,
                           BigDecimal monthlyPrice) {
        this.customerId = customerId;
        this.carId = carId;
        this.locationId = locationId;
        this.userId = userId;
        this.rentalPeriodId = rentalPeriodId;
        this.startDate = startDate;
        this.monthlyPrice = monthlyPrice;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRentalPeriodId(int rentalPeriodId) {
        this.rentalPeriodId = rentalPeriodId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCarId() {
        return carId;
    }

    public int getLocationId() {
        return locationId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRentalPeriodId() {
        return rentalPeriodId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }
}
