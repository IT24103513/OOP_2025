package org.parking.dao;

import org.parking.models.Slots.CoveredSlot;
import org.parking.models.Slots.OpenSlot;
import org.parking.models.Slots.ParkingSlot;
import org.parking.models.Slots.SlotStatus;
import org.parking.repositories.ParkingSlotRepository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingSlotDAO implements ParkingSlotRepository {

    private static final Path FILE = Paths.get("data","slots.txt");

    public ParkingSlotDAO(){
        try{
            Files.createDirectories(FILE.getParent());
            if(Files.notExists(FILE)) Files.createFile(FILE);
        }catch(IOException e){ throw new UncheckedIOException(e); }
    }

    /* CREATE */
    public void add(ParkingSlot s) throws IOException {
        try(var bw = Files.newBufferedWriter(FILE, StandardOpenOption.APPEND)){
            bw.write(toCsv(s)); bw.newLine();
        }
    }

    /* READ */
    public Optional<ParkingSlot> findByNumber(int num) throws IOException {
        return Files.lines(FILE)
                .filter(l -> l.startsWith(num + ","))
                .map(this::fromCsv).findFirst();
    }
    public List<ParkingSlot> findAll() throws IOException {
        List<ParkingSlot> list = new ArrayList<>();
        for(String l : Files.readAllLines(FILE))
            if(!l.isBlank()) list.add(fromCsv(l));
        return list;
    }

    /* UPDATE */
    public void update(ParkingSlot updated) throws IOException{
        List<String> lines = Files.readAllLines(FILE);
        for(int i=0;i<lines.size();i++){
            if(lines.get(i).startsWith(updated.getNumber()+",")){
                lines.set(i, toCsv(updated)); break;
            }
        }
        Files.write(FILE, lines, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /* DELETE */
    public void delete(int num) throws IOException{
        List<String> lines = Files.readAllLines(FILE);
        lines.removeIf(l -> l.startsWith(num+","));
        Files.write(FILE, lines, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /* helpers */
    private String toCsv(ParkingSlot s){
        return String.join(",",
                Integer.toString(s.getNumber()),
                s.getType(),
                s.getStatus().name());
    }
    private ParkingSlot fromCsv(String csv){
        String[] p = csv.split(",",-1);
        int number = Integer.parseInt(p[0]);
        ParkingSlot slot = switch (p[1]) {
            case "CoveredSlot" -> new CoveredSlot(number);
            default            -> new OpenSlot(number);
        };
        slot.setStatus(SlotStatus.valueOf(p[2]));
        return slot;
    }
}
