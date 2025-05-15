package org.parking.models.Vehicles;

public class Truck extends Vehicle{

    public Truck(String plate, String color, String owner){
        super(plate,color,owner);
    }


    @Override public String vehicleType() {
        return "Truck";
    }
}
