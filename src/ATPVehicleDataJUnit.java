package src;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ATPVehicleDataJUnit {
    private ATPVehicleData vehicleData;
    private final String vehicleID = "V123";
    /*I don't know how this will work */
    private final ArrayList<String> availableTimes = new ArrayList<>(Arrays.asList("9:00-10:00", "11:00-12:00"));
    private final ArrayList<String> bookedTimes = new ArrayList<>(Arrays.asList("10:00-11:00"));

    @Before
    public void setUp() {
        vehicleData = new ATPVehicleData(vehicleID, availableTimes, bookedTimes);
    }

    @Test
    public void testConstructorWithParams() {
        assertEquals(vehicleID, vehicleData.getVehicleID());
        assertEquals(availableTimes, vehicleData.getAvailableTimes());
        assertEquals(bookedTimes, vehicleData.getBookedTimes());
    }

    @Test
    public void testDefaultConstructor() {
        ATPVehicleData defaultVehicleData = new ATPVehicleData();
        assertNull(defaultVehicleData.getVehicleID());
        assertNotNull(defaultVehicleData.getAvailableTimes());
        assertTrue(defaultVehicleData.getBookedTimes().isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        String newVehicleID = "V456";
        ArrayList<String> newAvailableTimes = new ArrayList<>(Arrays.asList("13:00-14:00"));
        ArrayList<String> newBookedTimes = new ArrayList<>(Arrays.asList("14:00-15:00"));

        vehicleData.setVehicleID(newVehicleID);
        vehicleData.setAvailableTimes(newAvailableTimes);
        vehicleData.setBookedTimes(newBookedTimes);

        assertEquals(newVehicleID, vehicleData.getVehicleID());
        assertEquals(newAvailableTimes, vehicleData.getAvailableTimes());
        assertEquals(newBookedTimes, vehicleData.getBookedTimes());
    }

    @Test
    public void testToString() {
        String jsonString = vehicleData.toString();
        assertNotNull(jsonString);
        assertTrue(jsonString.contains(vehicleID));
    }

    @Test
    public void testDeserializeFromString() {
        String jsonData = "{\"vehicleID\":\"V789\",\"availableTimes\":[\"15:00-16:00\"],\"bookedTimes\":[\"16:00-17:00\"]}";
        vehicleData.deserializeFromString(jsonData);

        assertEquals("V789", vehicleData.getVehicleID());
        assertEquals(Arrays.asList("15:00-16:00"), vehicleData.getAvailableTimes());
        assertEquals(Arrays.asList("16:00-17:00"), vehicleData.getBookedTimes());
    }
}
