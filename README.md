# ATP-Implementation
Code and writeup for ATP Portion of CSC-331

## Current Implementation

The Availability-To-Promise service of the Subscription-based ride sharing service supports the ability for users to plan, book, change, and delete rides. All of these operations are supported with a locking mechanism to allow data concurrency and the elimination of the data races.


## Getting started with our implementation
Every operation is reachable and accessible through a single instance of the ATP class in [ATP.java](src/ATP.java). 

**It is important that there is only ever one instance of the ATP Class**
- The ATP class only allows locking of data to enable data concurrency within a object not between objects
- If multiple other services need multiple instances of ATP, let us know and we can create a solution using this implementation. 

We have provided a sample bash script that included directions to compiling our java source files with the GSON library we use for data representation [here](./java_test.sh)

## Publicly Accessible ATP Methods

**Method: `reserveRide`**<br> 
**Return Type: `String`**<br>
**Arguments: `UserReqClass`**<br>
This method will take in a object of type [UserReqClass](src/UserReqClass.java) as an argument. This method will attempt to book the reservation under the specific ATP vehicle using the locking mechanism. If successful, a message of success will be returned. If the unsuccessful, the returnAllAvailableRides method will be invoked to return alternative bookable ride options. After the reservation, no other reservation request will be able to reserve the vehicle at that specific time. The reserve ride request is logged within a [log directory](./src/test_booked_rides/reserved_rides/) for future look up purposes.


**Method: `changeRide`**<br> 
**Return Type: `String`**<br>
**Arguments: `BookedRideData, UserReqClass`**<br>
This method will take in a object of types [BookedRideData](src/BookedRideData.java) and [UserReqClass](src/UserReqClass.java) as arguments. This method will process a users request to change a previously reserve time. It will attempt to book the new reservation using the reserveRide method and only if successful delete the previously booked ride. Because both reserveRide and deleteRide methods are performed with the locking mechanism this method also enables data concurrency. The change ride request is logged within a [log directory](./src/test_booked_rides/changed_rides/) for future look up purposes.


**Method: `returnAllAvailableRides`**<br> 
**Return Type: `ArrayList<ATPVehicleData>`**<br>
**Arguments: `UserReqClass`**<br>
This method will take in a object of type [UserReqClass](src/UserReqClass.java) as an argument. The method will search the ATP object representation of vehicles and their available times to find all vehicles that are available during a user requested time. The available vehicles are then sorted in order of user vehicle preferences and returned.


**Method: `deleteRide`**<br> 
**Return Type: `String`**<br>
**Arguments: `BookedRideData`**<br>
This method will take in a object of type [BookedRideData](src/BookedRideData.java) as an argument. With the locking mechanism, the ride information contained within the passed argument is erased from ATP records and remade available as free ride time for other user requests. The delete ride request is logged within a [log directory](./src/test_booked_rides/delete_rides/) for future look up purposes.
