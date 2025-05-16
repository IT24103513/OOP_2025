package org.parking.models.Bookings;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Booking implements Serializable {

    /* ENCLOSED STATE */
    private long id;            // unique
    private String   username;
    private String vehiclePlate;
    private int  slotNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;       // null until exit
    private double fee;           // calculated on completion
    private BookingStatus status;
    private final String type;          // ShortTerm / LongTerm

    protected Booking(){
        this.type=getClass().getSimpleName();
    }

    protected Booking(long id,String user,String plate,int slot){
        this.id          = id;
        this.username    = user;
        this.vehiclePlate= plate;
        this.slotNumber  = slot;
        this.startTime   = LocalDateTime.now();
        this.status      = BookingStatus.ACTIVE;
        this.type        = getClass().getSimpleName();
    }

    /* getters */
    public long getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getVehiclePlate(){
        return vehiclePlate;
    }
    public int getSlotNumber(){
        return slotNumber;
    }
    public LocalDateTime getStartTime(){
        return startTime;
    }
    public LocalDateTime getEndTime(){
        return endTime;
    }
    public double getFee(){
        return fee;
    }
    public BookingStatus getStatus(){
        return status;
    }
    public String getType(){
        return type;
    }



    /* --- POLYMORPHIC BEHAVIOUR --- */
    public abstract double calculateFee(double chargePerHour,
                                        LocalDateTime from,
                                        LocalDateTime to);

    /* called by service when exiting */
    public void complete(double chargePerHour){
        this.endTime = LocalDateTime.now();
        this.fee     = calculateFee(chargePerHour,startTime,endTime);
        this.status  = BookingStatus.COMPLETED;
    }


}
