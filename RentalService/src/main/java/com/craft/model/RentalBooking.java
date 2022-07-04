package com.craft.model;

public class RentalBooking {

    private String bookingId;

    private String vehicleId;

    private int startTime;

    private int endTine;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTine() {
        return endTine;
    }

    public void setEndTine(int endTine) {
        this.endTine = endTine;
    }
}
