package com.gruppe4.projekt3_gruppe_4.Service;

import com.gruppe4.projekt3_gruppe_4.Model.RentalAgreement;
import com.gruppe4.projekt3_gruppe_4.Repo.RentalAgreementRepo;
import com.gruppe4.projekt3_gruppe_4.exceptions.RentalAgreementException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class RentalAgreementService {

    private final RentalAgreementRepo rentalAgreementRepo;

    public RentalAgreementService(RentalAgreementRepo rentalAgreementRepo){
        this.rentalAgreementRepo = rentalAgreementRepo;
    }

    public void createRentalAgreement(int customerId,
                                      int carId,
                                      int locationId,
                                      int userId,
                                      int rentalPeriodeId,
                                      LocalDate startDate,
                                      BigDecimal monthlyPrice) throws Exception {
        RentalAgreement rentalAgreement = new RentalAgreement(
                customerId,
                carId,
                locationId,
                userId,
                rentalPeriodeId,
                startDate,
                monthlyPrice);

        calculateEndDate(rentalAgreement);
        if (validateRentalAgreement(rentalAgreement.getCarId(), rentalAgreement.getStartDate(), rentalAgreement.getReturnDate())){
            rentalAgreementRepo.saveRentalAgreement(rentalAgreement);
        }else{
            throw new RentalAgreementException("Overlapping dates!");
        }

    }
// Checks if the car has overlapping rental dates
    public boolean validateRentalAgreement(int carId, LocalDate startDate, LocalDate returnDate){
        for(RentalAgreement ra : rentalAgreementRepo.findByCarId(carId)){
            return !ra.getStartDate().isAfter(startDate) && !ra.getReturnDate().isBefore(returnDate);
        }
        return false;
    }

// Calculates the end date of rental agreement
    public void calculateEndDate(RentalAgreement rentalAgreement){
        int rentalPeriode = rentalAgreementRepo.findRentalPeriodeById(rentalAgreement.getRentalAgreementId());
        rentalAgreement.setReturnDate(rentalAgreement.getStartDate().plusMonths(rentalPeriode));
    }

}