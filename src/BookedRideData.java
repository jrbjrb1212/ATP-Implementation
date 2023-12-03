package src;

import com.google.gson.Gson;

public class BookedRideData {
    private String rideID;
    private String vehicleID;
    private String userReqID;
    private String startTime;
    private String endTime;


    public BookedRideData() {
    }

    public BookedRideData(String rideID, String vehicleID, String userReqID, String startTime, String endTime) {
        this.rideID = rideID;
        this.vehicleID = vehicleID;
        this.userReqID = userReqID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getRideID() {
        return this.rideID;
    }

    public void setRideID(String rideID) {
        this.rideID = rideID;
    }

    public String getVehicleID() {
        return this.vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getUserReqID() {
        return this.userReqID;
    }

    public void setUserReqID(String userReqID) {
        this.userReqID = userReqID;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    
    
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void deserializeFromString(String bookedRideDataString){

        Gson gson = new Gson();
        BookedRideData temp = gson.fromJson(bookedRideDataString, BookedRideData.class);

        this.rideID = temp.rideID;
        this.vehicleID = temp.vehicleID;
        this.userReqID = temp.userReqID;
    }
}
