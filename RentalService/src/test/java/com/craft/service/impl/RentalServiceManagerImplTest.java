package com.craft.service.impl;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RentalServiceManagerImplTest {

    @Test
    void onboardBranch() {
        RentalServiceManagerImpl rsc = new RentalServiceManagerImpl();
        boolean res = rsc.onboardBranch("B1", "CAR");
        assertTrue(res);
    }

    @Test
    @Order(2)
    public void onboardBranch_Failure() {
        RentalServiceManagerImpl rsc = new RentalServiceManagerImpl();
        try {
            rsc.onboardBranch("B2", "TRUCK");
        } catch (Exception ex) {
            assertEquals("No enum constant com.craft.contants.VehicleType.TRUCK", ex.getMessage());
        }
    }

    @Test
    @Order(3)
    public void onboardVehicle() {
        RentalServiceManagerImpl rsc = new RentalServiceManagerImpl();
        rsc.onboardBranch("B3", "CAR");
        boolean res = rsc.onboardVehicle("B3", "CAR", "V1", 500);
        assertTrue(res);
    }

    @Test
    @Order(5)
    public void bookVehicle() {
        RentalServiceManagerImpl rsc = new RentalServiceManagerImpl();
        rsc.onboardBranch("B5", "CAR");
        rsc.onboardVehicle("B5", "CAR", "V1", 500);
        int res = rsc.bookVehicle("B5", "CAR", 1, 3);
        assertEquals(1000, res);
    }

    @Test
    @Order(6)
    public void bookVehicle_failure() {
        RentalServiceManagerImpl rsc = new RentalServiceManagerImpl();
        rsc.onboardBranch("B6", "VAN");
        rsc.onboardVehicle("B6", "VAN", "V1", 500);
        int res = rsc.bookVehicle("B6", "CAR", 1, 3);
        assertEquals(-1, res);
    }

    @Test
    @Order(7)
    public void fetchAvailableVehicles() {
        RentalServiceManagerImpl rsc = new RentalServiceManagerImpl();
        rsc.onboardBranch("B7", "VAN");
        rsc.onboardVehicle("B7", "VAN", "V1", 500);
        String res = rsc.fetchAvailableVehicles("B7", 1, 5);
        assertEquals("V1", res);
    }
}