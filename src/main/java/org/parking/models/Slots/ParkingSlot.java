package org.parking.models.Slots;

import java.io.Serializable;

public abstract class ParkingSlot implements Serializable {

    /* encapsulated state */
    private int         number;        // unique per slot
    private SlotStatus  status;
    private final String type;         // Open / Covered

    protected ParkingSlot() {

        this.type = getClass().getSimpleName();
    }

    protected ParkingSlot(int number) {
        this.number = number;
        this.status = SlotStatus.AVAILABLE;
        this.type   = getClass().getSimpleName();
    }

    /* getters / setters (ENCAPSULATION) */
    public int        getNumber() {
        return number;
    }
    public SlotStatus getStatus() {
        return status;
    }
    public String     getType()  {
        return type;
    }

    public void setStatus(SlotStatus s)  {
        this.status = s;
    }

    /* POLYMORPHIC – each subclass prices differently */
    public abstract double getChargePerHour();

    
}
