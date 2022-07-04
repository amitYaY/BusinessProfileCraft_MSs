package com.craft.service.impl;

import com.craft.contants.VehicleType;
import com.craft.model.RentalBranch;
import com.craft.model.RentalStorage;
import com.craft.model.RentalVehicle;
import com.craft.service.RentalServiceManager;

import java.util.ArrayList;
import java.util.List;

public class RentalServiceManagerImpl implements RentalServiceManager {

    private final RentalStorage rentalStorage;

    public RentalServiceManagerImpl() {
        this.rentalStorage = RentalStorage.getRentalStorage();
    }

    @Override
    public boolean onboardBranch(String branchId, String vehicleType) {
        if (!rentalStorage.getRentalDataStorage().containsKey(branchId)) {
            RentalBranch rentalBranch = new RentalBranch();
            rentalBranch.setBranchId(branchId);
            rentalBranch.setVehicles(new ArrayList<RentalVehicle>());
            rentalBranch.setVehicleType(new ArrayList<VehicleType>());
            rentalBranch.getVehicleType().add(VehicleType.valueOf(vehicleType));
            rentalStorage.getRentalDataStorage().put(branchId, rentalBranch);
        } else {
            RentalBranch rentalBranch = rentalStorage.getRentalDataStorage().get(branchId);
            rentalBranch.getVehicleType().add(VehicleType.valueOf(vehicleType));
        }
        return true;
    }

    @Override
    public boolean onboardVehicle(String branchId, String vehicleType, String vehicleId, int price) {
        RentalBranch rentalBranch = rentalStorage.getRentalDataStorage().get(branchId);
        RentalVehicle rentalVehicle = new RentalVehicle();
        rentalVehicle.setVehicleId(vehicleId);
        rentalVehicle.setVehicleType(VehicleType.valueOf(vehicleType));
        rentalVehicle.setPrice(price);
        rentalBranch.getVehicles().add(rentalVehicle);
        return true;
    }

    @Override
    public int bookVehicle(String branchId, String vehicleType, int startTime, int endTime) {
        List<RentalVehicle> vehicles = findAllAvailableVehicleList(branchId, startTime, endTime);
        RentalVehicle rentalVehicle = vehicles.stream().filter(vehicle -> vehicle.getVehicleType().name().equals(vehicleType))
                .findFirst().orElse(null);
        if(rentalVehicle != null ) {
            boolean[] slots = rentalVehicle.getSlots();
            for (int i = startTime; i <= endTime; i++) {
                slots[i] = true;
            }
            return rentalVehicle.getPrice() * (endTime - startTime);
        } else {
            return -1;
        }
    }

    @Override
    public String fetchAvailableVehicles(String branchId, int startTime, int endTime) {
        List<RentalVehicle> vehicles = findAllAvailableVehicleList(branchId, startTime, endTime);
        List<String> vehicleIds = vehicles.stream().map(RentalVehicle::getVehicleId).toList();
        return vehicleIds.stream().reduce(String::concat).orElse("");
    }

    private List<RentalVehicle> findAllAvailableVehicleList(String branchId, int startTime, int endTime) {
        RentalBranch rentalBranch = rentalStorage.getRentalDataStorage().get(branchId);
        return rentalBranch.getVehicles().stream().filter(vehicle -> {
            boolean[] slots = vehicle.getSlots();
            boolean isTimeSlotFree = true;
            for (int i = startTime; i <= endTime; i++) {
                if (slots[i]) {
                    isTimeSlotFree = false;
                    break;
                }
            }
            return isTimeSlotFree;
        }).toList();
    }
}
