package org.parking.dao;

import org.parking.models.Bookings.Booking;
import org.parking.models.Bookings.BookingStatus;
import org.parking.models.Bookings.LongTermBooking;
import org.parking.models.Bookings.ShortTermBooking;
import org.parking.repositories.BookingRepository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class    BookingDAO implements BookingRepository {

    private static final Path FILE = Paths.get("data","bookings.txt");
    public BookingDAO(){
        try{
            Files.createDirectories(FILE.getParent());
            if(Files.notExists(FILE)) Files.createFile(FILE);
        }catch(IOException e){ throw new UncheckedIOException(e); }
    }

    /* CREATE */
    public void add(Booking b) throws IOException{
        try(var w = Files.newBufferedWriter(FILE, StandardOpenOption.APPEND)){
            w.write(toCsv(b)); w.newLine();
        }
    }

    /* READ */
    public List<Booking> findAll() throws IOException{
        List<Booking> list=new ArrayList<>();
        for(String l:Files.readAllLines(FILE))
            if(!l.isBlank()) list.add(fromCsv(l));
        return list;
    }
    public Optional<Booking> find(long id) throws IOException{
        return Files.lines(FILE)
                .filter(l->l.startsWith(id+","))
                .map(this::fromCsv).findFirst();
    }

    /* UPDATE */
    public void update(Booking b) throws IOException{
        List<String> lines=Files.readAllLines(FILE);
        for(int i=0;i<lines.size();i++)
            if(lines.get(i).startsWith(b.getId()+",")){
                lines.set(i,toCsv(b)); break;
            }
        Files.write(FILE,lines,StandardOpenOption.TRUNCATE_EXISTING);
    }

    /* DELETE */
    public void delete(long id)throws IOException{
        List<String> lines=Files.readAllLines(FILE);
        lines.removeIf(l->l.startsWith(id+","));
        Files.write(FILE,lines,StandardOpenOption.TRUNCATE_EXISTING);
    }

    /* helpers */
    private String toCsv(Booking b){
        return String.join(",",
                Long.toString(b.getId()),
                b.getUsername(),
                b.getVehiclePlate(),
                Integer.toString(b.getSlotNumber()),
                b.getStartTime().toString(),
                b.getEndTime()==null?"":b.getEndTime().toString(),
                Double.toString(b.getFee()),
                b.getStatus().name(),
                b.getType());
    }
    private Booking fromCsv(String csv){
        String[] p=csv.split(",",-1);
        long id       = Long.parseLong(p[0]);
        String user   = p[1];
        String plate  = p[2];
        int slot      = Integer.parseInt(p[3]);
        Booking b = switch (p[8]){
            case "LongTermBooking"  -> new LongTermBooking(id,user,plate,slot);
            default                 -> new ShortTermBooking(id,user,plate,slot);
        };
        /* reflect persisted state */
        try{
            var sTime = LocalDateTime.parse(p[4]);
            var eTime = p[5].isBlank()?null:LocalDateTime.parse(p[5]);
            var fee   = Double.parseDouble(p[6]);
            var stat  = BookingStatus.valueOf(p[7]);

            var fld   = Booking.class.getDeclaredField("startTime");
            fld.setAccessible(true); fld.set(b,sTime);
            fld = Booking.class.getDeclaredField("endTime");
            fld.setAccessible(true); fld.set(b,eTime);
            fld = Booking.class.getDeclaredField("fee");
            fld.setAccessible(true); fld.set(b,fee);
            fld = Booking.class.getDeclaredField("status");
            fld.setAccessible(true); fld.set(b,stat);
        }catch(Exception ignored){}
        return b;
    }
}
