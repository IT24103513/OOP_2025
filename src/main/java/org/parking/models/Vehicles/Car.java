package org.parking.models.Vehicles;

public class Car extends Vehicle{

    public Car(String plate, String color, String owner){
        super(plate,color,owner);
    }

    @Override public String vehicleType() {
        return "Car";
    }
}
