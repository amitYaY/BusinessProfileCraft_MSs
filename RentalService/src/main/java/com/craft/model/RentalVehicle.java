package com.craft.model;

import com.craft.contants.VehicleType;

public class RentalVehicle {

    private String vehicleId;

    private VehicleType vehicleType;

    private int price;

    private final boolean[] slots = new boolean[25];

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean[] getSlots() {
        return slots;
    }
}
