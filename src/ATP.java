package src;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ATP {
    public String testPrint(){
        UserClass test = new UserClass();
        test.setFirstName("BILLOS");
        return test.toString();
    }

    private HashMap<String, VehicleData> activeVehicles;

    public ATP() {
        fillActiveVehicles();
    }

    // processes all vehicles in the saved vehicle directory
    // transform each saved JSON file into a POJO (Plain Old Java Object)
    private ArrayList<VehicleData> fillActiveVehicles(){
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
                } catch (IOException e) {
                    System.err.println("Error reading file: " + file.getName());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No JSON files found in the directory.");
        }
        
        for (VehicleData temp : vehicleList)
            System.out.println(temp.toString());

        return vehicleList;
    }

}
