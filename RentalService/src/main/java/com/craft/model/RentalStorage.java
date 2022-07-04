package com.craft.model;

import java.util.HashMap;
import java.util.Map;

public class RentalStorage {

    private final Map<String, RentalBranch> rentalDataStorage = new HashMap<>();

    private final static RentalStorage rentalStorage = new RentalStorage();

    private RentalStorage() {
        if(rentalStorage != null) {
            throw new RuntimeException("Rental Storage already Initialized");
        }
    }

    public static RentalStorage getRentalStorage() {
        return rentalStorage;
    }

    public Map<String, RentalBranch> getRentalDataStorage() {
        return rentalDataStorage;
    }
}
