package org.parking.models.Vehicles;

public class Bike extends Vehicle{

    public Bike(String plate, String color, String owner){
        super(plate,color,owner);
    }

    @Override public String vehicleType() {
        return "Bike";
    }

}
