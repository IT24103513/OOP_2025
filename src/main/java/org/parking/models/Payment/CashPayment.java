package org.parking.models.Payment;

public class CashPayment extends Payment{

    public CashPayment(long id,long bId,String user,double amount){
        super(id,bId,user,amount);
    }
    /* always succeeds */
    @Override public boolean processPayment(){
        return true;
    }
}
