package org.parking.services;

import org.parking.dao.ParkingSlotDAO;
import org.parking.models.Slots.CoveredSlot;
import org.parking.models.Slots.OpenSlot;
import org.parking.models.Slots.ParkingSlot;
import org.parking.models.Slots.SlotStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ParkingSlotService {

    private final ParkingSlotDAO dao = new ParkingSlotDAO();

    /* defineSlot (CREATE) */
    public boolean defineSlot(int number, String type) throws IOException {
        if(dao.findByNumber(number).isPresent()) return false;     // duplicate #
        ParkingSlot s = "Covered".equalsIgnoreCase(type)
                ? new CoveredSlot(number)
                : new OpenSlot(number);
        dao.add(s);  return true;
    }

    /* viewSlotStatus (READ) */
    public List<ParkingSlot> listAll() throws IOException {
        return dao.findAll();
    }

    /* markSlotOccupied / Vacant (UPDATE) */
    public void setOccupied(int num, boolean occupied) throws IOException{
        Optional<ParkingSlot> opt = dao.findByNumber(num);
        if(opt.isPresent()){
            ParkingSlot s = opt.get();
            s.setStatus(occupied? SlotStatus.OCCUPIED : SlotStatus.AVAILABLE);
            dao.update(s);
        }
    }

    /* deleteSlot (DELETE) */
    public void delete(int num) throws IOException{ dao.delete(num); }
}
