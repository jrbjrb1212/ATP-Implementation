package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class ATPVehicleData {
    private String vehicleID;
    private HashMap<String, ArrayList<FreeRideSlot>> availableTimes;
    private HashMap<String, ArrayList<BookedRideData>> bookedTimes;


    public ATPVehicleData() {
    }

    public ATPVehicleData(String vehicleID) {
        this.vehicleID = vehicleID;
        this.availableTimes = new HashMap<String, ArrayList<FreeRideSlot>>();
        fillAvailableTimes();
        this.bookedTimes = new HashMap<String, ArrayList<BookedRideData>>();
        fillBookedTimes();
    }

    private void fillAvailableTimes() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.YEAR, 2023); // Set the year to 2023
        startCalendar.set(Calendar.MONTH, Calendar.NOVEMBER); // Set the month to November (Calendar.NOVEMBER is a
        startCalendar.set(Calendar.DAY_OF_MONTH, 1); // Set the day of the month to 1

        // Add today's date to the list
        String rep = startCalendar.get(Calendar.YEAR) + "/";
        Integer month = startCalendar.get(Calendar.MONTH) + 1;
        rep += ((month < 10) ? "0" + month.toString() : month.toString()) + "/";
        Integer day = startCalendar.get(Calendar.DAY_OF_MONTH);
        rep += (day < 10) ? "0" + day.toString(): day.toString();

        ArrayList<FreeRideSlot> freeSlots = new ArrayList<>(Arrays.asList(new FreeRideSlot()));
        this.availableTimes.put(rep, freeSlots);

        // Iterate through 365 days
        for (int i = 0; i < 365; i++) {
            // Increment date by one day
            startCalendar.add(Calendar.DAY_OF_YEAR, 1);
            
            rep = startCalendar.get(Calendar.YEAR) + "/";
            month = startCalendar.get(Calendar.MONTH) + 1;
            rep += ((month < 10) ? "0" + month.toString() : month.toString()) + "/";
            day = startCalendar.get(Calendar.DAY_OF_MONTH);
            rep += (day < 10) ? "0" + day.toString(): day.toString();
            freeSlots = new ArrayList<>(Arrays.asList(new FreeRideSlot()));
            this.availableTimes.put(rep, freeSlots);
        }

        // Display all the dates in the ArrayList
        // for (Calendar key : this.availableTimes.keySet()) {
        //     int year = key.get(Calendar.YEAR);
        //     int month = key.get(Calendar.MONTH) + 1; // Adding 1 as months start from 0
        //     int day = key.get(Calendar.DAY_OF_MONTH);

        //     // Print year, month, and day
        //     System.out.println("Year: " + year + " Month: " + month + " Day: " + day);
        // }
    }
    
    private void fillBookedTimes() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.YEAR, 2023); // Set the year to 2023
        startCalendar.set(Calendar.MONTH, Calendar.NOVEMBER); // Set the month to November (Calendar.NOVEMBER is a
        startCalendar.set(Calendar.DAY_OF_MONTH, 1); // Set the day of the month to 1

        // Add today's date to the list
        String rep = startCalendar.get(Calendar.YEAR) + "/";
        Integer month = startCalendar.get(Calendar.MONTH) + 1;
        rep += ((month < 10) ? "0" + month.toString() : month.toString()) + "/";
        Integer day = startCalendar.get(Calendar.DAY_OF_MONTH);
        rep += (day < 10) ? "0" + day.toString(): day.toString();

        ArrayList<BookedRideData> bookedSlot = new ArrayList<>();
        this.bookedTimes.put(rep, bookedSlot);

        // Iterate through 365 days
        for (int i = 0; i < 365; i++) {
            // Increment date by one day
            startCalendar.add(Calendar.DAY_OF_YEAR, 1);
            
            rep = startCalendar.get(Calendar.YEAR) + "/";
            month = startCalendar.get(Calendar.MONTH) + 1;
            rep += ((month < 10) ? "0" + month.toString() : month.toString()) + "/";
            day = startCalendar.get(Calendar.DAY_OF_MONTH);
            rep += (day < 10) ? "0" + day.toString(): day.toString();
            bookedSlot = new ArrayList<>();
            this.bookedTimes.put(rep, bookedSlot);
        }

        // Display all the dates in the ArrayList
        // for (Calendar key : this.availableTimes.keySet()) {
        //     int year = key.get(Calendar.YEAR);
        //     int month = key.get(Calendar.MONTH) + 1; // Adding 1 as months start from 0
        //     int day = key.get(Calendar.DAY_OF_MONTH);

        //     // Print year, month, and day
        //     System.out.println("Year: " + year + " Month: " + month + " Day: " + day);
        // }
    }
    


    public String getVehicleID() {
        return this.vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public HashMap<String,ArrayList<FreeRideSlot>> getAvailableTimes() {
        return this.availableTimes;
    }

    public void setAvailableTimes(HashMap<String,ArrayList<FreeRideSlot>> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public HashMap<String, ArrayList<BookedRideData>> getBookedTimes() {
        return this.bookedTimes;
    }

    public void setBookedTimes(HashMap<String,ArrayList<BookedRideData>> bookedTimes) {
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
