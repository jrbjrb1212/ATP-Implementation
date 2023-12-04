import src.ATP;
import src.ATPVehicleData;
import src.BookedRideData;
import src.UserReqClass;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class ATPJUnit {

    private ATP atp;

    @Before
    public void setUp() {
        atp = new ATP();
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

    assertEquals("Expected success message", result);
}


@Test
public void testReturnAllAvailableRides() {
    //user request
    UserReqClass userReq = new UserReqClass(/* parameters for the user's request */);

    // Get available rides
    ArrayList<ATPVehicleData> availableRides = atp.returnAllAvailableRides(userReq);

    // Check if the returned list is not null and contains expected rides
    assertNotNull(availableRides);
    assertFalse(availableRides.isEmpty());

    // Further assertions can be made based on expected data
    // For instance, check if a known available ride is included
}


@Test
public void testDeleteRide() {
    // First, create and reserve a ride
    String vehicleID = "testVehicleID";
    UserReqClass req = new UserReqClass(/* parameters for the ride */);
    atp.reserveRide(vehicleID, req);

    // Create a BookedRideData instance representing the booked ride
    BookedRideData rideToDelete = new BookedRideData(/* parameters representing the ride */);

    // Attempt to delete the ride
    String result = atp.deleteRide(rideToDelete);

    // Check that the deletion was successful
    assertEquals("Ride Deletion successful", result);
    }
}


