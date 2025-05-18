package org.parking.models.Bookings;

import java.time.Duration;
import java.time.LocalDateTime;

public class ShortTermBooking extends Booking{

    public ShortTermBooking(long id,
                            String username,
                            String plate,
                            int slotNumber) {
        super(id, username, plate, slotNumber);
    }

    @Override
    public double calculateFee(double hourlyRate,
                               LocalDateTime from,
                               LocalDateTime to) {

        /* minutes → ceil‑hours (at least 1 h) */
        long minutes = Duration.between(from, to).toMinutes();
        long hours   = Math.max(1, (minutes + 59) / 60);   // ceiling divide
        return hours * hourlyRate;
    }
}
