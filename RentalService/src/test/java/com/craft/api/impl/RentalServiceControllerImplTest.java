package com.craft.api.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RentalServiceControllerImplTest {

    @Test
    public void onboardBranch() {
        RentalServiceControllerImpl rsc = new RentalServiceControllerImpl();
        boolean res = rsc.onboardBranch("B1", "CAR,BIKE,VAN");
        assertTrue(res);
    }

    @Test
    public void onboardBranch_Failure() {
        RentalServiceControllerImpl rsc = new RentalServiceControllerImpl();
        boolean res = rsc.onboardBranch("B2", "TRUCK");
        assertFalse(res);
    }

    @Test
    public void onboardVehicle() {
        RentalServiceControllerImpl rsc = new RentalServiceControllerImpl();
        rsc.onboardBranch("B3", "CAR,BIKE,VAN");
        boolean res = rsc.onboardVehicle("B3", "CAR", "V1", 500);
        assertTrue(res);
    }

    @Test
    public void onboardVehicle_Failure() {
        RentalServiceControllerImpl rsc = new RentalServiceControllerImpl();
        rsc.onboardBranch("B4", "CAR,BIKE,VAN");
        boolean res = rsc.onboardVehicle("B4", "BUS", "V1", 500);
        assertFalse(res);
    }

    @Test
    public void bookVehicle() {
        RentalServiceControllerImpl rsc = new RentalServiceControllerImpl();
        rsc.onboardBranch("B5", "CAR,BIKE,VAN");
        rsc.onboardVehicle("B5", "CAR", "V1", 500);
        int res = rsc.bookVehicle("B5", "CAR", 1, 3);
        assertEquals(1000, res);
    }

    @Test
    public void bookVehicle_failure() {
        RentalServiceControllerImpl rsc = new RentalServiceControllerImpl();
        rsc.onboardBranch("B6", "CAR,BIKE,VAN");
        rsc.onboardVehicle("B6", "VAN", "V1", 500);
        int res = rsc.bookVehicle("B6", "CAR", 1, 3);
        assertEquals(-1, res);
    }

    @Test
    public void fetchAvailableVehicles() {
        RentalServiceControllerImpl rsc = new RentalServiceControllerImpl();
        rsc.onboardBranch("B7", "CAR,BIKE,VAN");
        rsc.onboardVehicle("B7", "VAN", "V1", 500);
        String res = rsc.fetchAvailableVehicles("B7", 1, 5);
        assertEquals("V1", res);
    }
}