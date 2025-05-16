package org.parking.models.Slots;

public class CoveredSlot extends ParkingSlot{

    public CoveredSlot(int number){
        super(number);
    }
    @Override public double getChargePerHour(){
        return 250.0;
    }
}
