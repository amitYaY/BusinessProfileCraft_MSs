package com.craft;

import com.craft.api.RentalServiceController;
import com.craft.api.impl.RentalServiceControllerImpl;
import com.craft.contants.Commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DriverTest {

    private String INPUT_FILE = "input.txt";

    private RentalServiceController rentalServiceController;

    public static void main(String[] args) {
        DriverTest driverTest = new DriverTest();
        driverTest.executeCommands();
    }

    private void executeCommands() {
        rentalServiceController = new RentalServiceControllerImpl();
        URL inputFile = getClass().getClassLoader().getResource(INPUT_FILE);
        File file = null;
        try {
            file = new File(inputFile.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try (FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr)) {
            String in = null;
            while((in = br.readLine()) != null) {
                String[] ins = in.trim().split(" ");
                switch (ins[0]) {
                    case Commands.ADD_BRANCH : {
                        System.out.println(addNewBranch(ins));
                        break;
                    }
                    case Commands.ADD_VEHICLE : {
                        System.out.println(addVehicleToBranch(ins));
                        break;
                    }
                    case Commands.BOOK : {
                        System.out.println(bookVehicle(ins));
                        break;
                    }
                    case Commands.DISPLAY_VEHICLES : {
                        System.out.println(findAvailableVehicles(ins));
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String findAvailableVehicles(String[] ins) {
        String branchId = ins[1];
        int startTime = Integer.parseInt(ins[2]);
        int endTime = Integer.parseInt(ins[3]);
        return rentalServiceController.fetchAvailableVehicles(branchId, startTime, endTime);
    }

    private int bookVehicle(String[] ins) {
        String branchId = ins[1];
        String vehicleType = ins[2];
        int startTime = Integer.parseInt(ins[3]);
        int endTime = Integer.parseInt(ins[4]);
        return rentalServiceController.bookVehicle(branchId, vehicleType, startTime, endTime);
    }

    private boolean addVehicleToBranch(String[] ins) {
        String branchId = ins[1];
        String vehicleType = ins[2];
        String vehicleId = ins[3];
        int price = Integer.parseInt(ins[4]);
        return rentalServiceController.onboardVehicle(branchId, vehicleType, vehicleId, price);
    }

    private boolean addNewBranch(String[] ins) {
        String branchId = ins[1];
        String vehicleTypes = ins[2];
        return rentalServiceController.onboardBranch(branchId, vehicleTypes);
    }


}
