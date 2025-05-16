package org.parking.services;

import org.parking.dao.VehicleDAO;
import org.parking.models.Vehicles.Bike;
import org.parking.models.Vehicles.Car;
import org.parking.models.Vehicles.Truck;
import org.parking.models.Vehicles.Vehicle;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class VehicleService {

    private final VehicleDAO dao = new VehicleDAO();

    /* factory encapsulates polymorphic creation */
    public boolean add(String plate,String color,String type,String owner) throws IOException{
        if(dao.findByPlate(plate).isPresent()) return false;          // duplicate
        Vehicle v = switch(type){
            case "Bike"  -> new Bike(plate,color,owner);
            case "Truck" -> new Truck(plate,color,owner);
            default      -> new Car(plate,color,owner);
        };
        dao.add(v);  return true;
    }

    public List<Vehicle> listOf(String owner) throws IOException { return dao.findByOwner(owner); }

    public Optional<Vehicle> get(String plate) throws IOException { return dao.findByPlate(plate); }

    public void updateColor(String plate,String newColor) throws IOException{
        Optional<Vehicle> vOpt = dao.findByPlate(plate);
        if(vOpt.isPresent()){
            Vehicle v = vOpt.get();
            v.setColor(newColor);
            dao.update(v);
        }
    }

    public void delete(String plate) throws IOException{ dao.delete(plate); }
}
