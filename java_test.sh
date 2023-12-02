# run by doing bash java_test.sh from the base directory

# Compile the java files and attach GSON librarybinary to each
javac -cp lib/gson-2.10.jar src/TestATP.java src/ATP.java src/ATPVehicleData.java  src/UserClass.java src/VehicleData.java

# Run the testATP code with the compiled classes in the src package and the compiled GSON binary
java -cp .:lib/gson-2.10.jar src.TestATP

# Remove the compiled classes
rm src/*.class