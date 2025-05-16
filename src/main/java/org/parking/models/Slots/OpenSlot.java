package org.parking.models.Slots;

public class OpenSlot extends ParkingSlot{

    public OpenSlot(int number){

        super(number);
    }
    @Override public double getChargePerHour(){
        return 150.0;
    }
}
