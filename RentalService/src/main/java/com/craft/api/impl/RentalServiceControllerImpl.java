package com.craft.api.impl;

import com.craft.api.RentalServiceController;
import com.craft.service.RentalServiceManager;
import com.craft.service.impl.RentalServiceManagerImpl;
import com.craft.validator.RentalServiceValidator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RentalServiceControllerImpl implements RentalServiceController {

    Logger log = Logger.getLogger("RentalServiceControllerImpl");

    private RentalServiceManager rentalServiceManager;

    private RentalServiceValidator rentalServiceValidator;

    public RentalServiceControllerImpl() {
        this.rentalServiceManager = new RentalServiceManagerImpl();
        this.rentalServiceValidator = new RentalServiceValidator();
    }

    @Override
    public boolean onboardBranch(String branchId, String vehicleType) {
        try {
            rentalServiceValidator.validateBranchOnboard(branchId, vehicleType);
        } catch (Exception ex) {
            log.log(Level.SEVERE, ex.getMessage());
            return false;
        }
        String[] types = vehicleType.trim().split(",");
        for (String type : types) {
            try {
                rentalServiceValidator.validateSupportedVehicleType(type);
                rentalServiceManager.onboardBranch(branchId, type);
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onboardVehicle(String branchId, String vehicleType, String vehicleId, int price) {
        try {
            rentalServiceValidator.validateVehicleOnboard(branchId, vehicleType, vehicleId, price);
        } catch (Exception ex) {
            //log.log(Level.SEVERE, ex.getMessage());
            return false;
        }
        try {
            rentalServiceManager.onboardVehicle(branchId, vehicleType, vehicleId, price);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public int bookVehicle(String branchId, String vehicleType, int startTime, int endTime) {
        try {
            rentalServiceValidator.validateBookingRequest(branchId, vehicleType, startTime, endTime);
        } catch (Exception ex) {
            ///log.log(Level.SEVERE, ex.getMessage());
            return -1;
        }
        try {
            return rentalServiceManager.bookVehicle(branchId, vehicleType, startTime, endTime);
        } catch (Exception ex) {
            return -1;
        }
    }

    @Override
    public String fetchAvailableVehicles(String branchId, int startTime, int endTime) {
        try {
            rentalServiceValidator.validateFetchVehicleRequest(branchId, startTime, endTime);
        } catch (Exception ex) {
            //log.log(Level.SEVERE, ex.getMessage());
            return "";
        }
        try {
            return rentalServiceManager.fetchAvailableVehicles(branchId, startTime, endTime);
        } catch (Exception ex) {
            return "";
        }
    }
}
