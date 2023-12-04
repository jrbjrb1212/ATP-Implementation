import src.ATP;
import src.ATPVehicleData;
import src.BookedRideData;
import src.UserReqClass;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ATPJUnit {

    private ATP atp;

      @Before
    public void setUp() {
        atp = new ATP();
    /*     vehicleID = "testVehicleID";


        bookedRide = new BookedRideData(vehicleID, "10:00", "11:00");

        ArrayList<String> availableTimes = new ArrayList<>(Arrays.asList("9:00-10:00", "11:00-12:00"));
        ArrayList<String> bookedTimes = new ArrayList<>(Arrays.asList("10:00-11:00"));
        ATPVehicleData vehicleData = new ATPVehicleData(vehicleID, availableTimes, bookedTimes);
        

        atp.activeATPVehicles.put(vehicleID, vehicleData); */
    }

    @After
    public void tearDown() {
    }

@Test
public void testReserveRide_Success() {
    // Assuming you have a valid vehicle ID
    String vehicleID = "validVehicleID";

    // Create a UserReqClass instance with necessary data
    UserReqClass userReq = new UserReqClass("userID", "John", "Doe", 4, true, "2023-12-24",
                                            "15", "30", "AM", "Business", 
                                            new UserReqClass.Location("123 Main St", "", "City", "State", "12345"), 
                                            new UserReqClass.Location("456 Elm St", "", "City", "State", "12345"), 
                                            "Toyota", "Camry", "2020", "60");

    // Call the reserveRide method with the vehicle ID and UserReqClass instance
    String result = atp.reserveRide(vehicleID, userReq);
    assertEquals("Ride Creation Successful", result);
}

@Test
public void testReserveRide_Failure() {
    // Assuming you have a vehicleID that is not available
    String vehicleID = "unavailableVehicleID";

    // Create a UserReqClass instance with necessary data
    UserReqClass newUserReq = new UserReqClass("userID", "John", "Doe", 4, true, "2023-12-24",
                                               "15", "30", "AM", "Business", 
                                               new UserReqClass.Location("123 Main St", "", "City", "State", "12345"), 
                                               new UserReqClass.Location("456 Elm St", "", "City", "State", "12345"), 
                                               "Toyota", "Camry", "2020", "60");

    // Call the reserveRide method with the vehicle ID and UserReqClass instance
    String result = atp.reserveRide(vehicleID, newUserReq);

    // Expecting the ride creation to be unsuccessful
    assertEquals("Ride Creation Unsuccessful", result);
}

@Test
public void testChangeRide() {

    String originalVehicleID = "originalVehicleID";
    UserReqClass originalReq = new UserReqClass(/* parameters for the original ride */);

    atp.reserveRide(originalVehicleID, originalReq);

    BookedRideData rideToChange = new BookedRideData(/* parameters representing the original ride */);

    // Set up new conditions for the ride
    String newVehicleID = "newVehicleID";
    UserReqClass newReq = new UserReqClass(/* parameters for the new ride */);

    String result = atp.changeRide(rideToChange, newVehicleID, newReq);

    assertEquals("Not working yet", result);
}
/*   private HashMap<String, VehicleData> activeTransportVehicles;
    private ArrayList<ATPVehicleData> availableVehicles;
    private UserReqClass userReq;

    @Before
    public void setUp() {
        // Initialize the activeTransportVehicles with some dummy data
        activeTransportVehicles = new HashMap<>();
        activeTransportVehicles.put("V1", new VehicleData("Make1", "Model1", "2020"));
        activeTransportVehicles.put("V2", new VehicleData("Make2", "Model2", "2021"));
        activeTransportVehicles.put("V3", new VehicleData("Make1", "Model1", "2022"));

        // Initialize availableVehicles
        availableVehicles = new ArrayList<>();
        availableVehicles.add(new ATPVehicleData("V1"));
        availableVehicles.add(new ATPVehicleData("V2"));
        availableVehicles.add(new ATPVehicleData("V3"));

        // Set user preferences
        userReq = new UserReqClass("Make1", "Model1", "2020");
    } */
/*@Test
public void testSortAvailableRides() {
    SortAvailableRides sortAvailableRides = new SortAvailableRides(activeTransportVehicles);
    ArrayList<ATPVehicleData> sortedVehicles = sortAvailableRides.sortAvailableRides(availableVehicles, userReq);

    assertNotNull(sortedVehicles);
    assertEquals(3, sortedVehicles.size());
    assertEquals("V1", sortedVehicles.get(0).getVehicleID()); // V1 should be ranked highest based on user preferences
    assertEquals("V3", sortedVehicles.get(1).getVehicleID()); // V3 should be next
    assertEquals("V2", sortedVehicles.get(2).getVehicleID()); // V2 should be last
}*/

@Test
public void testReturnAllAvailableRides() {
    //user request
    UserReqClass userReq = new UserReqClass(/* parameters for the user's request */);
//No idea what to do with this
    // Get available rides
    ArrayList<ATPVehicleData> availableRides = atp.returnAllAvailableRides(userReq);

    // Check if the returned list is not null and contains expected rides
    assertNotNull(availableRides);
    assertFalse(availableRides.isEmpty());

}


@Test
public void testDeleteRide_Success() {
    // Delete the ride
    // String result = atp.deleteRide(bookedRide);

    // Assert that the deletion was successful
    assertEquals("Ride Deletion successful", result);
    
    // Optionally, assert that the ride is no longer in the booked times
    ATPVehicleData vehicleData = atp.activeATPVehicles.get(vehicleID);
    /*assertFalse(vehicleData.getBookedTimes().contains("time"));*/
}
@Test
    public void testDeleteRide_Failure() {
        // Create a ride data for a non-existent ride
   /*   BookedRideData nonExistentRide = new BookedRideData(vehicleID, "12:00", "13:00"); */

     /*    String result = atp.deleteRide(nonExistentRide); */

        // Assert
        assertEquals("Ride Deletion unsuccessful", result);
    }
}


