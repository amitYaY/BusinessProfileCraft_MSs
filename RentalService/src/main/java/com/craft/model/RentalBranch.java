package com.craft.model;

import com.craft.contants.VehicleType;

import java.util.List;

public class RentalBranch {

    private String branchId;

    private List<VehicleType> vehicleType;

    private List<RentalVehicle> vehicles;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public List<VehicleType> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(List<VehicleType> vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<RentalVehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<RentalVehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
