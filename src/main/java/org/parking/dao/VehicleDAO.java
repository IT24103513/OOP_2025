package org.parking.dao;

import org.parking.models.Vehicles.Bike;
import org.parking.models.Vehicles.Car;
import org.parking.models.Vehicles.Truck;
import org.parking.models.Vehicles.Vehicle;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleDAO {

    private static final Path VEH_FILE = Paths.get("data","vehicles.txt");

    public VehicleDAO(){
        try{
            Files.createDirectories(VEH_FILE.getParent());
            if(Files.notExists(VEH_FILE)) Files.createFile(VEH_FILE);
        }catch(IOException e){ throw new UncheckedIOException(e); }
    }

    /* CREATE */
    public void add(Vehicle v) throws IOException {
        try(var bw = Files.newBufferedWriter(VEH_FILE, StandardOpenOption.APPEND)){
            bw.write(toCsv(v)); bw.newLine();
        }
    }

    /* READ */
    public List<Vehicle> findByOwner(String owner) throws IOException{
        List<Vehicle> res = new ArrayList<>();
        for(String line: Files.readAllLines(VEH_FILE)){
            if(line.isBlank()) continue;
            Vehicle v = fromCsv(line);
            if(v.getOwnerUsername().equals(owner)) res.add(v);
        }
        return res;
    }
    public Optional<Vehicle> findByPlate(String plate) throws IOException{
        return Files.readAllLines(VEH_FILE).stream()
                .filter(l->l.startsWith(plate+","))
                .map(this::fromCsv).findFirst();
    }

    /* UPDATE */
    public void update(Vehicle updated) throws IOException{
        List<String> lines = Files.readAllLines(VEH_FILE);
        for(int i=0;i<lines.size();i++){
            if(lines.get(i).startsWith(updated.getPlateNumber()+",")){
                lines.set(i, toCsv(updated));
                break;
            }
        }
        Files.write(VEH_FILE, lines, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /* DELETE */
    public void delete(String plate) throws IOException{
        List<String> lines = Files.readAllLines(VEH_FILE);
        lines.removeIf(l -> l.startsWith(plate+","));
        Files.write(VEH_FILE, lines, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /* helpers */
    private String toCsv(Vehicle v){
        return String.join(",", v.getPlateNumber(), v.getColor(),
                v.getOwnerUsername(), v.vehicleType());
    }
    private Vehicle fromCsv(String csv){
        String[] p = csv.split(",",-1);
        return switch (p[3]){
            case "Car"   -> new Car(p[0],p[1],p[2]);
            case "Bike"  -> new Bike(p[0],p[1],p[2]);
            default      -> new Truck(p[0],p[1],p[2]);
        };
    }
}
