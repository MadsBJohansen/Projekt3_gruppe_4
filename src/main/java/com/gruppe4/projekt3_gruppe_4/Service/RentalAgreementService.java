package com.gruppe4.projekt3_gruppe_4.Service;

import com.gruppe4.projekt3_gruppe_4.Model.RentalAgreement;
import com.gruppe4.projekt3_gruppe_4.Repo.RentalAgreementRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class RentalAgreementService {

    private final RentalAgreementRepo rentalAgreementRepo;

    public RentalAgreementService(RentalAgreementRepo rentalAgreementRepo) {
        this.rentalAgreementRepo = rentalAgreementRepo;
    }

    public void createRentalAgreement(int customerId,
                                      int carId,
                                      int locationId,
                                      int userId,
                                      int rentalPeriodeId,
                                      LocalDate startDate,
                                      BigDecimal monthlyPrice){
        rentalAgreementRepo.saveRentalAgreement(new RentalAgreement(
                customerId,
                carId,
                locationId,
                userId,
                rentalPeriodeId,
                startDate,
                monthlyPrice)
        );
    }

//    public boolean validateRentalAgreement(int carId){
//
//        for(RentalAgreement ra : rentalAgreementRepo.findAll()){
//            if (ra.getStartDate().equals())
//
//        }
//    }
//
//    public LocalDate calculateEndDate(){
//
//    }


}
