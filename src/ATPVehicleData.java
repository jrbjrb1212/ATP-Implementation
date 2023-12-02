package src;

import com.google.gson.Gson;
import java.util.ArrayList;

public class ATPVehicleData {
    private String vehicleID;
    private ArrayList<String> availableTimes;
    private ArrayList<String> bookedTimes;


    public ATPVehicleData() {
    }

    public ATPVehicleData(String vehicleID, ArrayList<String> availableTimes, ArrayList<String> bookedTimes) {
        this.vehicleID = vehicleID;
        this.availableTimes = availableTimes;
        this.bookedTimes = bookedTimes;
    }

    public String getVehicleID() {
        return this.vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public ArrayList<String> getAvailableTimes() {
        return this.availableTimes;
    }

    public void setAvailableTimes(ArrayList<String> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public ArrayList<String> getBookedTimes() {
        return this.bookedTimes;
    }

    public void setBookedTimes(ArrayList<String> bookedTimes) {
        this.bookedTimes = bookedTimes;
    }
    

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void deserializeFromString(String vehicleDataString){
        Gson gson = new Gson();
        ATPVehicleData temp = gson.fromJson(vehicleDataString, ATPVehicleData.class);

        this.vehicleID = temp.vehicleID;
        this.availableTimes = temp.availableTimes;
        this.bookedTimes = temp.bookedTimes;
    }
}
