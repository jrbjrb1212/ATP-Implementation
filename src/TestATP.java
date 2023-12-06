package src;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestATP {
    public static void main(String[] args) {
        ATP atp = new ATP();
        reserveRideTest(atp);
    }

    private static void reserveRideTest(ATP atp){
        String testVehicleID = "V123";
        UserReqClass testUserReqData = new UserReqClass();
        String directoryPath = "src/test_users/user_req_U123.json"; 

        File file = new File(directoryPath);
        try (FileReader reader = new FileReader(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            int character;

            // add all characters from JSON file to a long string
            while ((character = reader.read()) != -1) {
                stringBuilder.append((char) character);
            }

            String userDataString = stringBuilder.toString();
            
            // Send the data to a vehicle data POJO and fill class members from string
            testUserReqData = new UserReqClass();
            testUserReqData.deserializeFromString(userDataString);
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getName());
            e.printStackTrace();
        }

        // System.out.println(testUserReqData.toString());
        atp.reserveRide(testVehicleID, testUserReqData);
    }
}
