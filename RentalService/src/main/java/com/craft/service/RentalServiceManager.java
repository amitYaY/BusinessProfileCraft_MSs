package com.craft.service;

public interface RentalServiceManager {

    boolean onboardBranch(String branchId, String vehicleType);

    boolean onboardVehicle(String branchId, String vehicleType, String vehicleId, int price);

    int bookVehicle(String branchId, String vehicleType, int startTime, int endTime);

    String fetchAvailableVehicles(String branchId, int startTime, int endTime);
}
