package org.parking.services;

import org.parking.dao.PaymentDAO;
import org.parking.models.Payment.CardPayment;
import org.parking.models.Payment.CashPayment;
import org.parking.models.Payment.Payment;
import org.parking.models.Payment.PaymentStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class PaymentService {

    private final PaymentDAO dao = new PaymentDAO();
    private static final AtomicLong SEQ = new AtomicLong();

    /* initiatePayment() – CREATE */
    public Payment initiate(long bookingId, String user, double amount,
                            String mode, String cardNumber) throws IOException {

        long id = SEQ.incrementAndGet();
        Payment p = "CARD".equalsIgnoreCase(mode)
                ? new CardPayment(id,bookingId,user,amount,cardNumber)
                : new CashPayment(id,bookingId,user,amount);

        boolean ok = p.processPayment();
        p.markProcessed(ok);
        dao.add(p);
        return p;
    }

    /* viewTransactionDetails() – READ */
    public Optional<Payment> get(long id)throws IOException{ return dao.find(id); }
    public List<Payment> listOf(String user)throws IOException{
        return dao.findAll().stream()
                .filter(p->p.getUsername().equals(user)).toList();
    }

    /* updatePaymentStatus() – UPDATE  (manual override) */
    public void overrideStatus(long id, PaymentStatus newStatus) throws IOException {
        Optional<Payment> opt = dao.find(id);
        if (opt.isPresent()) {
            Payment p = opt.get();
            p.setStatus(newStatus);
            dao.update(p);
        }
    }


    /* deletePaymentRecord() – DELETE */
    public void delete(long id)throws IOException{ dao.delete(id); }
}
