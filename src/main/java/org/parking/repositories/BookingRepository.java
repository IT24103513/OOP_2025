package org.parking.repositories;

import org.parking.models.Bookings.Booking;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {

    void add(Booking b) throws IOException;

    List<Booking> findAll() throws IOException;

    Optional<Booking> find(long id) throws IOException;

    void update(Booking b) throws IOException;

    void delete(long id)throws IOException;
}
