package org.parking.models.Payment;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Payment implements Serializable {

    /* ── Encapsulated state ────────────────── */
    private long            id;          // unique
    private long            bookingId;
    private String          username;    // owner
    private double          amount;
    private LocalDateTime paidAt;
    private PaymentStatus   status;
    private final String    method;      // CardPayment / CashPayment

    protected Payment(){
        this.method = getClass().getSimpleName();
    }

    protected Payment(long id,long bookingId,String user,double amount){
        this.id        = id;
        this.bookingId = bookingId;
        this.username  = user;
        this.amount    = amount;
        this.status    = PaymentStatus.PENDING;
        this.method    = getClass().getSimpleName();
    }

    /* getters / setters */
    public long  getId() {
        return id;
    }

    public void setStatus(PaymentStatus newStatus) {
        this.status = newStatus;
    }
    public long  getBookingId() {
        return bookingId;
    }
    public String  getUsername()  {
        return username;
    }
    public double  getAmount() {
        return amount;
    }
    public LocalDateTime getPaidAt() {
        return paidAt;
    }
    public PaymentStatus getStatus()  {
        return status;
    }
    public String  getMethod()   {
        return method;
    }

    /* POLYMORPHIC behaviour */
    public abstract boolean processPayment();

    /* called by service */
    public void markProcessed(boolean ok){
        this.status = ok? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
        this.paidAt = LocalDateTime.now();
    }
}
