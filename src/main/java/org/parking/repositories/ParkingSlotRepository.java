package org.parking.repositories;

import org.parking.models.Slots.ParkingSlot;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ParkingSlotRepository {

    void add(ParkingSlot s) throws IOException;

    Optional<ParkingSlot> findByNumber(int num) throws IOException;

    List<ParkingSlot> findAll() throws IOException;


    void update(ParkingSlot updated) throws IOException;
/** Remove a slot by its number */
    void delete(int num) throws IOException;
}
