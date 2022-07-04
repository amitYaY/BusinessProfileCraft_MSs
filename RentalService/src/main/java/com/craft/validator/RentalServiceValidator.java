package com.craft.validator;

import com.craft.contants.VehicleType;
import com.craft.model.RentalBranch;
import com.craft.model.RentalStorage;

public class RentalServiceValidator {

    private RentalStorage rentalStorage;

    public RentalServiceValidator() {
        this.rentalStorage = RentalStorage.getRentalStorage();
    }

    public void validateBranchOnboard(String branchId, String vehicleType) {
        if (branchId == null || branchId.length() == 0 || branchId.trim().length() == 0) {
            throw new RuntimeException("Bad Request. BranchId Required Field");
        }

        if (vehicleType == null || vehicleType.length() == 0 || vehicleType.trim().length() == 0) {
            throw new RuntimeException("Bad Request. vehicleType Required Field");
        }

        if (rentalStorage.getRentalDataStorage().containsKey(branchId)) {
            throw new RuntimeException("Bad Request. BranchId Already Exists");
        }
    }

    public void validateSupportedVehicleType(String vehicleType) {
        boolean isValidVehicleType = false;
        for (VehicleType type : VehicleType.values()) {
            if (type.name().equals(vehicleType)) {
                isValidVehicleType = true;
                break;
            }
        }
        if (!isValidVehicleType) {
            throw new RuntimeException("Bad Request. Invalid vehicleType");
        }
    }

    public void validateVehicleOnboard(String branchId, String vehicleType, String vehicleId, int price) {
        if (branchId == null || branchId.length() == 0 || branchId.trim().length() == 0) {
            throw new RuntimeException("Bad Request. BranchId Required Field");
        }

        if (vehicleType == null || vehicleType.length() == 0 || vehicleType.trim().length() == 0) {
            throw new RuntimeException("Bad Request. vehicleType Required Field");
        }

        boolean isValidVehicleType = false;
        for (VehicleType type : VehicleType.values()) {
            if (type.name().equals(vehicleType)) {
                isValidVehicleType = true;
                break;
            }
        }
        if (!isValidVehicleType) {
            throw new RuntimeException("Bad Request. Invalid vehicleType");
        }

        if (vehicleId == null || vehicleId.length() == 0 || vehicleId.trim().length() == 0) {
            throw new RuntimeException("Bad Request. vehicleId Required Field");
        }

        if (price <= 0) {
            throw new RuntimeException("Bad Request. Invalid Rental Price");
        }

        if (!rentalStorage.getRentalDataStorage().containsKey(branchId)) {
            throw new RuntimeException("Bad Request. BranchId Doesn't Exists");
        }

        RentalBranch rentalBranch = rentalStorage.getRentalDataStorage().get(branchId);
        if (rentalBranch == null || !rentalBranch.getVehicleType().contains(VehicleType.valueOf(vehicleType))) {
            throw new RuntimeException("Bad Request. VehicleType not Supported");
        }
    }

    public void validateBookingRequest(String branchId, String vehicleType, int startTime, int endTime) {
        if (branchId == null || branchId.length() == 0 || branchId.trim().length() == 0) {
            throw new RuntimeException("Bad Request. BranchId Required Field");
        }

        if (vehicleType == null || vehicleType.length() == 0 || vehicleType.trim().length() == 0) {
            throw new RuntimeException("Bad Request. vehicleType Required Field");
        }

        boolean isValidVehicleType = false;
        for (VehicleType type : VehicleType.values()) {
            if (type.name().equals(vehicleType)) {
                isValidVehicleType = true;
                break;
            }
        }
        if (!isValidVehicleType) {
            throw new RuntimeException("Bad Request. Invalid vehicleType");
        }

        RentalBranch rentalBranch = rentalStorage.getRentalDataStorage().get(branchId);
        if (rentalBranch == null || !rentalBranch.getVehicleType().contains(VehicleType.valueOf(vehicleType))) {
            throw new RuntimeException("Bad Request. VehicleType not Supported");
        }

        if (startTime < 0 || endTime > 24 || startTime > 23 || endTime <= 0 || startTime >= endTime) {
            throw new RuntimeException("Bad Request. Invalid Booking Slot");
        }
    }

    public void validateFetchVehicleRequest(String branchId, int startTime, int endTime) {
        if (branchId == null || branchId.length() == 0 || branchId.trim().length() == 0) {
            throw new RuntimeException("Bad Request. BranchId Required Field");
        }

        if (startTime < 0 || endTime > 24 || startTime > 23 || endTime <= 0 || startTime >= endTime) {
            throw new RuntimeException("Bad Request. Invalid Booking Slot");
        }
    }
}
