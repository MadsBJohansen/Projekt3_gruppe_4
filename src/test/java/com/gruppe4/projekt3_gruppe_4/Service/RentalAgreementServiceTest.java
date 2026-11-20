package com.gruppe4.projekt3_gruppe_4.Service;

import com.gruppe4.projekt3_gruppe_4.Model.RentalAgreement;
import com.gruppe4.projekt3_gruppe_4.Model.User;
import com.gruppe4.projekt3_gruppe_4.Repo.RentalAgreementRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalAgreementServiceTest {

    @Mock
    private RentalAgreementRepo rentalAgreementRepo;

    @InjectMocks
    private RentalAgreementService rentalAgreementService;

    RentalAgreement rentalAgreement = rentalAgreementRepo.findById(1);

}