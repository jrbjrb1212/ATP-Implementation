package src;

import com.google.gson.Gson;

public class VehicleData {
    private String vehicleID;
    private boolean vehicleHealth;
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleYear;
    private String vehicleVIN;
    private String vehicleLicensePlate;
    private String numberOfPassengers;
    private double vehicleCharge;


    public VehicleData() {
    }

    public VehicleData(String vehicleID, boolean vehicleHealth, String vehicleMake, String vehicleModel, String vehicleYear, String vehicleVIN, String vehicleLicensePlate, String numberOfPassengers, double vehicleCharge) {
        this.vehicleID = vehicleID;
        this.vehicleHealth = vehicleHealth;
        this.vehicleMake = vehicleMake;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
        this.vehicleVIN = vehicleVIN;
        this.vehicleLicensePlate = vehicleLicensePlate;
        this.numberOfPassengers = numberOfPassengers;
        this.vehicleCharge = vehicleCharge;
    }

    public String getVehicleID() {
        return this.vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public boolean isVehicleHealth() {
        return this.vehicleHealth;
    }

    public boolean getVehicleHealth() {
        return this.vehicleHealth;
    }

    public void setVehicleHealth(boolean vehicleHealth) {
        this.vehicleHealth = vehicleHealth;
    }

    public String getVehicleMake() {
        return this.vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleModel() {
        return this.vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleYear() {
        return this.vehicleYear;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleVIN() {
        return this.vehicleVIN;
    }

    public void setVehicleVIN(String vehicleVIN) {
        this.vehicleVIN = vehicleVIN;
    }

    public String getVehicleLicensePlate() {
        return this.vehicleLicensePlate;
    }

    public void setVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
    }

    public String getNumberOfPassengers() {
        return this.numberOfPassengers;
    }

    public void setNumberOfPassengers(String numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public double getVehicleCharge() {
        return this.vehicleCharge;
    }

    public void setVehicleCharge(double vehicleCharge) {
        this.vehicleCharge = vehicleCharge;
    }
    

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void deserializeFromString(String vehicleDataString){
        Gson gson = new Gson();
        VehicleData temp = gson.fromJson(vehicleDataString, VehicleData.class);

        this.vehicleID = temp.vehicleID;
        this.vehicleHealth = temp.vehicleHealth;
        this.vehicleMake = temp.vehicleMake;
        this.vehicleModel = temp.vehicleModel;
        this.vehicleYear = temp.vehicleYear;
        this.numberOfPassengers = temp.numberOfPassengers;
        this.vehicleCharge = temp.vehicleCharge;
    }
}
