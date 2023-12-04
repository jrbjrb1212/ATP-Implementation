package src;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ATP {

    private HashMap<String, VehicleData> activeTransportVehicles;
    private HashMap<String, ATPVehicleData> activeATPVehicles;


    /*
    *
    * On ATP object initialization, fill the representation of booked and available rides
    * using pre-saved JSON files
    *
    */
    public ATP() {
        this.activeTransportVehicles = new HashMap<>();
        this.activeATPVehicles = new HashMap<>();
        fillActiveTransportVehicles();
        fillActiveATPVehicles();
        addTransportVehiclesToATP(activeTransportVehicles, activeATPVehicles);
    }



    public String reserveRide(String vehicleID, UserReqClass newUserReq){

        synchronized (activeATPVehicles) {
            // all code done in here will have the locking mechanism enabled for the ATP hashmap of vehicles
        
            ATPVehicleData associatedVehicle = activeATPVehicles.get(vehicleID);
            if (associatedVehicle == null) {
                return "Ride Creation Unsuccessful";
            }
    
            // Rest of your code...
    
            // Example of null-check before use
        
            // add the interval to the list of booked times
            ArrayList<String> currBookedTimes = associatedVehicle.getBookedTimes();
            if (currBookedTimes == null) {
                currBookedTimes = new ArrayList<>();
                associatedVehicle.setBookedTimes(currBookedTimes);
            }
            // TODO: On successful reserveRide
            // update the stored JSON file in test_atp_vehicles
            // String fileName = "atp_vehicle_" + vehicleID + ".json";
            // String logFilePath = "src/test_atp_vehicles/" + fileName
            // // update the file saves of test_atp_vehicles to reflect the active data
            // writeToLog(logFilePath, updatedVehicle.toString());

            // finds the start and end time of the ride and puts it into a string interval
            int lengthOfRideInInt = Integer.parseInt(newUserReq.getLengthOfRideInMinutes());
            String endHour = Integer.toString(Integer.parseInt(newUserReq.getHour()) + (lengthOfRideInInt/60));
            String endMinute = Integer.toString(Integer.parseInt(newUserReq.getMinute()) + (lengthOfRideInInt%60));
            String interval = newUserReq.getHour() + ":" + newUserReq.getMinute() + "-" + endHour + ":" + endMinute;

            int sizeBefore = associatedVehicle.getBookedTimes().size();
       
            currBookedTimes.add(interval);
            // update the interval list
            associatedVehicle.setBookedTimes(currBookedTimes); 


            //remove the interval from the list of available times
            ArrayList<String> currAvailableTimes = associatedVehicle.getAvailableTimes();
            currAvailableTimes.remove(interval);
            // update the interval list
            associatedVehicle.setAvailableTimes(currAvailableTimes);
            

            if (sizeBefore != associatedVehicle.getBookedTimes().size()){
                // ride creation successful
                
                String fileName = "atp_vehicle_" + vehicleID + ".json";
                String logFilePath = "src/test_atp_vehicles/" + fileName;
                // update the file saves of test_atp_vehicles to reflect the active data
                writeToLog(logFilePath, associatedVehicle.toString());
                

                String rideID = "ride_" + vehicleID + "_" + "111";
                // need to change final number into a unique identifier for the ride

                // Log the user request:
                String bookedFileName = "booked_" + rideID + ".json";
                String bookedLogFilePath = "src/test_booked_vehicles/reserve_rides/" + bookedFileName; 
                // update the log of booked rides
                writeToLog(bookedLogFilePath, associatedVehicle.toString());

                return "Ride Creation Successful";
            }
            else{
                return "Ride Creation Unsuccessful";
            }
            
        }

        //below is unreachable
        //return "reserveRide: Not working yet";
    }
    
    
    public String changeRide(BookedRideData rideToChange, String vehicleID, UserReqClass newUserReq){
        // attempt to reserve a ride
        // if reserve a ride is successful then call delete ride on the old ride
        
        String s = reserveRide(vehicleID, newUserReq);


        if(s.equals("Ride Creation Successful")){
            deleteRide(rideToChange);
        }
        

        return "changeRide: Not working yet";
    }



    public ArrayList<ATPVehicleData> returnAllAvailableRides(UserReqClass newUserReq){
        // TODO: searching algorithm for open times slices goes here

        ArrayList<ATPVehicleData> availableVehicles = null;
        ATPVehicleData examinedVehicle;

        // finds the start and end time of the ride and puts it into a string interval
        int lengthOfRideInInt = Integer.parseInt(newUserReq.getLengthOfRideInMinutes());
        String endHour = Integer.toString(Integer.parseInt(newUserReq.getHour()) + (lengthOfRideInInt/60));
        String endMinute = Integer.toString(Integer.parseInt(newUserReq.getMinute()) + (lengthOfRideInInt%60));
        String interval = newUserReq.getHour() + ":" + newUserReq.getMinute() + "-" + endHour + ":" + endMinute;


        for(HashMap.Entry<String,ATPVehicleData> entry : activeATPVehicles.entrySet()){
            examinedVehicle = entry.getValue();

            ArrayList<String> currTimes = examinedVehicle.getAvailableTimes();

            if(currTimes.contains(interval)) // not entirely sure how the time works, does contain work?
                availableVehicles.add(examinedVehicle); // also unsure why this is a bug
        }


        sortAvailableRides(availableVehicles, newUserReq);
        return availableVehicles;


        //return "returnAllAvailableRides: Not working yet";

    }


    /*
    *
    * Vehicles that are found to be available during a specific user request are sorted
    * by how close their specifications are to the user preferences
    *
    */
    private ArrayList<ATPVehicleData> sortAvailableRides(ArrayList<ATPVehicleData> availableVehicles, UserReqClass userReq){
        String userPrefMake = userReq.getVehicleMakePref();
        String userPrefModel = userReq.getVehicleModelPref();
        String userPrefYear = userReq.getVehicleYearPref();
        
        ArrayList<Pair<Integer, ATPVehicleData>> rankedAvailableVehicles = new ArrayList<>(availableVehicles.size());

        // Compute a ranking for each available vehicle 
        for (int i=0; i<availableVehicles.size(); i++){
            ATPVehicleData availableVehicle = availableVehicles.get(i);
            VehicleData vehicleSpecs = this.activeTransportVehicles.get(availableVehicle.getVehicleID());
            Integer currRank = 0;

            if (vehicleSpecs.getVehicleMake() == userPrefMake)
                currRank++;
            if (vehicleSpecs.getVehicleModel() == userPrefModel)
                currRank++;
            if (vehicleSpecs.getVehicleYear() == userPrefYear)
                currRank++;
            
            Pair<Integer, ATPVehicleData> temp = new Pair<Integer,ATPVehicleData>(currRank, availableVehicle);
            rankedAvailableVehicles.set(i, temp);
        }

        // Sort the rankedAvailableVehicles list based on the first element of the Pair (the rank)
        Collections.sort(rankedAvailableVehicles, Comparator.comparing(Pair::getFirst));

        for (int i=0; i<availableVehicles.size(); i++)
            availableVehicles.set(i, rankedAvailableVehicles.get(i).getSecond());

        return availableVehicles;
    }


    /*
    *
    * Reserved time on a specific vehicles is freed and made
    * available to another user request
    *
    */
    public String deleteRide(BookedRideData toDelete){
        // Lock activeATPVehicles to prevent concurrent modification
        synchronized (activeATPVehicles) {
            // Get the vehicle ID from the ride data
            String vehicleID = toDelete.getVehicleID();
            ATPVehicleData associatedVehicle = activeATPVehicles.get(vehicleID);

            String start = toDelete.getStartTime();
            String end = toDelete.getEndTime();
            String interval = start + "-" + end;

            int sizeBefore = associatedVehicle.getBookedTimes().size();
            // remove the interval from the list
            ArrayList<String> currTimes = associatedVehicle.getBookedTimes();
            currTimes.remove(interval);

            // update the interval list
            associatedVehicle.setAvailableTimes(currTimes); // should you remove from bookedTime?

            if (sizeBefore != associatedVehicle.getBookedTimes().size()){
                return "Ride Deletion successful";
            }
            else{
                return "Ride Deletion unsuccessful";
            }
        }
    }


    /*
    *
    * Processes all vehicles in the saved vehicle directory
    * transform each saved JSON file into a POJO (Plain Old Java Object)
    *
    */
    private void fillActiveTransportVehicles(){
        // TODO: ArrayList is only used for testing
        ArrayList<VehicleData> vehicleList = new ArrayList<>();
        
        String directoryPath = "src/test_vehicles"; 
        File directory = new File(directoryPath);

        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        if (files != null) {            
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    int character;

                    // add all characters from JSON file to a long string
                    while ((character = reader.read()) != -1) {
                        stringBuilder.append((char) character);
                    }

                    String vehicleDataString = stringBuilder.toString();
                    
                    // Send the data to a vehicle data POJO and fill class members from string
                    VehicleData vehicleData = new VehicleData();
                    vehicleData.deserializeFromString(vehicleDataString);
                    vehicleList.add(vehicleData);
                    this.activeTransportVehicles.put(vehicleData.getVehicleID(), vehicleData);
                } catch (IOException e) {
                    System.err.println("Error reading file: " + file.getName());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No JSON files found in the directory.");
        }
        
        System.out.println("Transport vehicles in test_vehicles directory: ");
        for (VehicleData temp : vehicleList)
            System.out.println(temp.toString());
        
        System.out.println("Size of activeTransportVehicles: " + activeTransportVehicles.size());
        System.out.println(" ");
    }


    /*
    *
    * processes all vehicles in the saved atp_vehicle directory
    * transform each saved JSON file into a POJO (Plain Old Java Object)
    *
    */
    private void fillActiveATPVehicles(){
        ArrayList<ATPVehicleData> atpVehicleList = new ArrayList<>();

        String directoryPath = "src/test_atp_vehicles"; 
        File directory = new File(directoryPath);

        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        if (files != null) {            
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    int character;

                    // add all characters from JSON file to a long string
                    while ((character = reader.read()) != -1) {
                        stringBuilder.append((char) character);
                    }

                    String atpVehicleDataString = stringBuilder.toString();
                    
                    // Send the data to a vehicle data POJO and fill class members from string
                    ATPVehicleData atpVehicleData = new ATPVehicleData();
                    atpVehicleData.deserializeFromString(atpVehicleDataString);
                    atpVehicleList.add(atpVehicleData);
                    activeATPVehicles.put(atpVehicleData.getVehicleID(), atpVehicleData);
                } catch (IOException e) {
                    System.err.println("Error reading file: " + file.getName());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No JSON files found in the directory.");
        }
        
        System.out.println("ATP vehicles in test_atp_vehicles directory: ");
        for (ATPVehicleData temp : atpVehicleList)
            System.out.println(temp.toString());

        System.out.println("Size of activeATPVehiclesMap: " + activeATPVehicles.size());
        System.out.println(" ");
    }


    /*
    *
    * All vehicles obtained from Transport service have not been seen by ATP before
    * are added to ATP to be made available for ride requests
    *
    */
    private void addTransportVehiclesToATP(HashMap<String, VehicleData> transportVehicles, HashMap<String, ATPVehicleData> atpVehicles){
        
        for (String transportID : transportVehicles.keySet()) {
            // vehicle is already in the atp_vehicles map and does need to be added
            if (atpVehicles.containsKey(transportID))
                continue;
                
            String fileName = "atp_vehicle_" + transportID + ".json";
            ATPVehicleData newATPVehicle = new ATPVehicleData(transportID);

            atpVehicles.put(transportID, newATPVehicle);
            writeToLog("src/test_atp_vehicles/" + fileName, atpVehicles.get(transportID).toString());
        }
    }


    /*
    *
    * Write a JSON string to a filePath
    * - Used to store updated information about ATP vehicles
    * - Used to log ride deletion requests
    * - Used to log ride reservation requests
    * - Used to log ride change requests
    *
    */
    private void writeToLog(String filePath, String jsonString) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(jsonString);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + filePath);
            e.printStackTrace();
        }
    }
}
