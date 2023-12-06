package src;

import com.google.gson.Gson;

public class FreeRideSlot{
    public String startTime;
    public String endTime;


    public FreeRideSlot() {
        this.startTime = "00:00";
        this.endTime = "23:59";
    }

    public FreeRideSlot(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
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

    public void deserializeFromString(String freeRideSlotString){

        Gson gson = new Gson();
        FreeRideSlot temp = gson.fromJson(freeRideSlotString, FreeRideSlot.class);

        this.startTime = temp.startTime;
        this.endTime = temp.endTime;
    }
}
