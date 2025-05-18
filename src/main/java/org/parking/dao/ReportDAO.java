package org.parking.dao;

import org.parking.models.Bookings.Booking;
import org.parking.models.Bookings.BookingStatus;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ReportDAO {

    private final BookingDAO bookingDAO = new BookingDAO();

    /** Sum revenue by booking class name (ShortTermBooking, LongTermBooking, etc.) */
    public Map<String, Double> revenueByType() throws IOException {
        List<Booking> all = bookingDAO.findAll();
        return all.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getClass().getSimpleName(),
                        Collectors.summingDouble(Booking::getFee)));
    }

    /** Last N days (including today): map date → total revenue of COMPLETED bookings */
    public Map<LocalDate, Double> dailyRevenue(int days) throws IOException {
        List<Booking> all = bookingDAO.findAll();
        LocalDate today = LocalDate.now();
        // initialize last N days with 0
        Map<LocalDate, Double> rev = new TreeMap<>();
        for(int i = days-1; i >= 0; i--) {
            rev.put(today.minusDays(i), 0.0);
        }
        // accumulate
        for (Booking b : all) {
            if (b.getStatus() != BookingStatus.COMPLETED) continue;
            LocalDate d = b.getEndTime().toLocalDate();
            if (rev.containsKey(d)) {
                rev.put(d, rev.get(d) + b.getFee());
            }
        }
        return rev;
    }
}
