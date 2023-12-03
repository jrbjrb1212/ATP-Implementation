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

    public ATP() {
        this.activeTransportVehicles = new HashMap<>();
        this.activeATPVehicles = new HashMap<>();
        fillActiveTransportVehicles();
        fillActiveATPVehicles();
        addTransportVehiclesToATP(activeTransportVehicles, activeATPVehicles);
    }

    public String reserveRide(){
        return "reserveRide: Not working yet";
    }

    public String changeRide(){
        return "changeRide: Not working yet";
    }

    public String returnAllAvailableRides(){


        // sortAvailableRides(availableVehicles, userReqData);
        // return availableVehicles;
        return "returnAllAvailableRides: Not working yet";
    }

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

    public String deleteRide(){
        return "deleteRide: Not working yet";
    }

    // processes all vehicles in the saved vehicle directory
    // transform each saved JSON file into a POJO (Plain Old Java Object)
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

    // processes all vehicles in the saved atp_vehicle directory
    // transform each saved JSON file into a POJO (Plain Old Java Object)
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
