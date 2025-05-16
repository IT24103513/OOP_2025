package org.parking.models.Bookings;

import java.time.Duration;
import java.time.LocalDateTime;

public class LongTermBooking extends Booking{

    public LongTermBooking(long id,
                           String username,
                           String plate,
                           int slotNumber) {
        super(id, username, plate, slotNumber);
    }

    @Override
    public double calculateFee(double hourlyRate,
                               LocalDateTime from,
                               LocalDateTime to) {

        /* hours → ceil‑days (at least 1 day). 20 % discount. */
        long hours = Duration.between(from, to).toHours();
        long days  = Math.max(1, (hours + 23) / 24);       // ceiling divide
        return days * hourlyRate * 24 * 0.8;               // 20 % off
    }
}
