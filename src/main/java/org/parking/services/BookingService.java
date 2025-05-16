package org.parking.services;

import org.parking.dao.BookingDAO;
import org.parking.models.Bookings.Booking;
import org.parking.models.Bookings.BookingStatus;
import org.parking.models.Bookings.LongTermBooking;
import org.parking.models.Bookings.ShortTermBooking;
import org.parking.models.Slots.ParkingSlot;
import org.parking.models.Slots.SlotStatus;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class BookingService {

    private final BookingDAO dao = new BookingDAO();
    private final ParkingSlotService slotSvc = new ParkingSlotService();
    private static final AtomicLong SEQ = new AtomicLong(initSeq());        // simple id generator

    private static long initSeq() {
        try {
            long max = new BookingDAO().findAll().stream()
                    .mapToLong(Booking::getId)
                    .max()
                    .orElse(0L);
            return max;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /* createBooking (CREATE) */
    public boolean createBooking(String username,String plate,int slot,String type)
            throws IOException {

        /* slot must be free */
        Optional<ParkingSlot> sOpt=slotSvc.listAll().stream()
                .filter(s->s.getNumber()==slot && s.getStatus()== SlotStatus.AVAILABLE)
                .findFirst();
        if(sOpt.isEmpty()) return false;

        long id = SEQ.incrementAndGet();
        Booking b = "Long".equalsIgnoreCase(type)
                ? new LongTermBooking(id,username,plate,slot)
                : new ShortTermBooking(id,username,plate,slot);

        dao.add(b);
        slotSvc.setOccupied(slot,true);        // mark slot occupied
        return true;
    }

    /* getActiveBookings (READ) */
    public List<Booking> getActiveOf(String username) throws IOException{
        List<Booking> list=new ArrayList<>();
        for(Booking b:dao.findAll())
            if(b.getUsername().equals(username) && b.getStatus()== BookingStatus.ACTIVE)
                list.add(b);
        return list;
    }
    public List<Booking> getAllOf(String username)throws IOException{
        List<Booking> list=new ArrayList<>();
        for(Booking b:dao.findAll())
            if(b.getUsername().equals(username)) list.add(b);
        return list;
    }

    /* completeBooking (UPDATE) */
    public void complete(long id) throws IOException {
        complete(id, "CASH", null);
    }

    /** new: choose cash vs. card **/
    public void complete(long id, String mode, String cardNumber) throws IOException {
        Optional<Booking> opt = dao.find(id);
        if (opt.isEmpty()) return;

        Booking b = opt.get();
        if (b.getStatus() == BookingStatus.COMPLETED) return;

        // compute fee & mark booking complete
        double rate = slotSvc.listAll().stream()
                .filter(s -> s.getNumber() == b.getSlotNumber())
                .findFirst()
                .map(s -> s.getChargePerHour())
                .orElse(0.0);

        b.complete(rate);
        dao.update(b);
        slotSvc.setOccupied(b.getSlotNumber(), false);

        // trigger appropriate payment
        var paySvc = new PaymentService();
        paySvc.initiate(b.getId(), b.getUsername(), b.getFee(), mode, cardNumber);
    }

    /* deleteBooking (DELETE) – admin only */
    public void delete(long id)throws IOException{ dao.delete(id); }
}
