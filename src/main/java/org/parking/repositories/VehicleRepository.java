package org.parking.repositories;

import org.parking.models.Vehicles.Vehicle;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    void add(Vehicle v) throws IOException;

    List<Vehicle> findByOwner (String owner) throws IOException;

    Optional<Vehicle> findByPlate(String plate) throws IOException;

    void update(Vehicle updated) throws IOException;

    void delete(String plate) throws IOException;
}
