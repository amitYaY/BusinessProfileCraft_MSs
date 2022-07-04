package com.craft.api;

public interface RentalServiceController {

    //ADD_BRANCH	TRUE if the operation succeeds else FALSE
    boolean onboardBranch(String branchId, String vehicleType);
    //ADD_VEHICLE	TRUE if the operation succeeds else FALSE
    boolean onboardVehicle(String branchId, String vehicleType, String vehicleId, int price);
    //BOOK Booking Price, if booking succeeds else -1
    int bookVehicle(String branchId, String vehicleType, int startTime, int endTime);
    //DISPLAY_VEHICLES	Vehicle Ids, comma-separated
    String fetchAvailableVehicles(String branchId, int startTime, int endTime);
}
