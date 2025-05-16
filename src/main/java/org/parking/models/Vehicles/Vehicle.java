package org.parking.models.Vehicles;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Vehicle implements Serializable {

    private String plateNumber;
    private String color;
    private String ownerUsername;           // link to User
    private final LocalDateTime registeredAt = LocalDateTime.now();

    protected Vehicle() {}                  // for deserialisation

    protected Vehicle(String plateNumber, String color, String ownerUsername) {
        this.plateNumber  = plateNumber;
        this.color        = color;
        this.ownerUsername= ownerUsername;
    }

    /* Encapsulation */
    public String getPlateNumber() {
        return plateNumber;
    }
    public void   setPlateNumber(String plateNumber){
        this.plateNumber = plateNumber;
    }

    public String getColor() {
        return color;
    }
    public void   setColor(String color){
        this.color = color;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public LocalDateTime getRegisteredAt(){
        return registeredAt;
    }

    /* --- Polymorphic behaviour --- */
    public abstract String vehicleType();           // e.g., "Car", "Bike"
    public String displayInfo() {
        return "%s (%s, %s)".formatted(vehicleType(), plateNumber, color);
    }
}
