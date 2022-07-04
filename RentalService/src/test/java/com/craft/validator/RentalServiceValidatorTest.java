package com.craft.validator;

import com.craft.service.RentalServiceManager;
import com.craft.service.impl.RentalServiceManagerImpl;
import org.junit.jupiter.api.Test;

class RentalServiceValidatorTest {

    @Test
    void validateBranchOnboard() {
        RentalServiceValidator rsv = new RentalServiceValidator();
        rsv.validateBranchOnboard("B2", "CAR,BIKE,VAN");
    }

    @Test
    void validateSupportedVehicleType() {
        RentalServiceValidator rsv = new RentalServiceValidator();
        rsv.validateSupportedVehicleType("CAR");
    }

    @Test
    void validateVehicleOnboard() {
        RentalServiceManager rsm = new RentalServiceManagerImpl();
        rsm.onboardBranch("B1", "VAN");
        RentalServiceValidator rsv = new RentalServiceValidator();
        rsv.validateVehicleOnboard("B1", "VAN", "V1", 300);
    }

    @Test
    void validateBookingRequest() {
        RentalServiceManager rsm = new RentalServiceManagerImpl();
        rsm.onboardBranch("B3", "CAR");
        rsm.onboardVehicle("B3", "CAR", "V1", 300);
        RentalServiceValidator rsv = new RentalServiceValidator();
        rsv.validateBookingRequest("B3", "CAR", 1, 3);
    }

    @Test
    void validateFetchVehicleRequest() {
        RentalServiceValidator rsv = new RentalServiceValidator();
        rsv.validateFetchVehicleRequest("B1", 1, 3);
    }
}