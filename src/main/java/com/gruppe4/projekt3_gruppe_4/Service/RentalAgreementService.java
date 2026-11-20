package com.gruppe4.projekt3_gruppe_4.Service;

import com.gruppe4.projekt3_gruppe_4.Model.RentalAgreement;
import com.gruppe4.projekt3_gruppe_4.Repo.RentalAgreementRepo;
import com.gruppe4.projekt3_gruppe_4.exceptions.RentalAgreementException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Service
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

        calculateReturnDate(rentalAgreement);
        if (isOverlappingRentalPeriod(rentalAgreement.getCarId(), rentalAgreement.getStartDate(), rentalAgreement.getReturnDate())){
            throw new RentalAgreementException("Overlapping dates!");
        }else{
            rentalAgreementRepo.saveRentalAgreement(rentalAgreement);
        }

    }
// Checks if the car has overlapping rental dates
    public boolean isOverlappingRentalPeriod(int carId, LocalDate startDate, LocalDate returnDate){
        for(RentalAgreement ra : rentalAgreementRepo.findByCarId(carId)){
            return ra.getStartDate().isAfter(startDate) && ra.getReturnDate().isBefore(returnDate);
        }
        return false;
    }

// Calculates the end date of rental agreement
    public void calculateReturnDate(RentalAgreement rentalAgreement){
        int rentalPeriode = rentalAgreementRepo.findRentalPeriodeById(rentalAgreement.getRentalAgreementId());
        LocalDate returnDate =rentalAgreement.getStartDate().plusMonths(rentalPeriode);
        rentalAgreement.setReturnDate(returnDate);
    }

}