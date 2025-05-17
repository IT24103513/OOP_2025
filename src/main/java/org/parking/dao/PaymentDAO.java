package org.parking.dao;

import org.parking.models.Payment.CardPayment;
import org.parking.models.Payment.CashPayment;
import org.parking.models.Payment.Payment;
import org.parking.models.Payment.PaymentStatus;
import org.parking.repositories.PaymentRepository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PaymentDAO implements PaymentRepository {

    private static final Path FILE = Paths.get("data","payments.txt");

    public PaymentDAO(){
        try{
            Files.createDirectories(FILE.getParent());
            if(Files.notExists(FILE)) Files.createFile(FILE);
        }catch(IOException e){ throw new UncheckedIOException(e); }
    }

    /* CREATE */
    public void add(Payment p)throws IOException{
        try(var w=Files.newBufferedWriter(FILE, StandardOpenOption.APPEND)){
            w.write(toCsv(p)); w.newLine();
        }
    }

    /* READ */
    public List<Payment> findAll()throws IOException{
        List<Payment> list=new ArrayList<>();
        for(String l:Files.readAllLines(FILE))
            if(!l.isBlank()) list.add(fromCsv(l));
        return list;
    }
    public Optional<Payment> find(long id)throws IOException{
        return Files.lines(FILE)
                .filter(l->l.startsWith(id+","))
                .map(this::fromCsv).findFirst();
    }

    /* UPDATE */
    public void update(Payment p)throws IOException{
        List<String> lines=Files.readAllLines(FILE);
        for(int i=0;i<lines.size();i++)
            if(lines.get(i).startsWith(p.getId()+",")){
                lines.set(i,toCsv(p));
                break;
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
    private String toCsv(Payment p){
        String extras = (p instanceof CardPayment c)? c.getMaskedCard() : "";
        return String.join(",",
                Long.toString(p.getId()),
                Long.toString(p.getBookingId()),
                p.getUsername(),
                Double.toString(p.getAmount()),
                p.getStatus().name(),
                p.getMethod(),
                Objects.toString(p.getPaidAt(),""),
                extras);
    }
    private Payment fromCsv(String csv){
        String[] p=csv.split(",",-1);
        long id = Long.parseLong(p[0]);
        long bid= Long.parseLong(p[1]);
        String user=p[2];
        double amt=Double.parseDouble(p[3]);
        Payment pay = switch(p[5]){
            case "CardPayment" -> new CardPayment(id,bid,user,amt,p.length>7?p[7]:"****");
            default            -> new CashPayment(id,bid,user,amt);
        };
        try{
            var stat= PaymentStatus.valueOf(p[4]);
            var fld=Payment.class.getDeclaredField("status");
            fld.setAccessible(true); fld.set(pay,stat);
            if(!p[6].isBlank()){
                var dt = java.time.LocalDateTime.parse(p[6]);
                fld=Payment.class.getDeclaredField("paidAt");
                fld.setAccessible(true); fld.set(pay,dt);
            }
        }catch(Exception ignored){}
        return pay;
    }
}
