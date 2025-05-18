package org.parking.models.Payment;

public class CardPayment extends Payment{

    private String maskedCard;          // optional extra info

    public CardPayment(long id,long bId,String user,double amount,String cardNum){
        super(id,bId,user,amount);
        this.maskedCard = cardNum.replaceAll("\\d{12}(\\d{4})", "************$1");
    }
    public String getMaskedCard(){ return maskedCard; }

    /* very silly success rule for demo */
    @Override public boolean processPayment(){
        return maskedCard.endsWith("0") || maskedCard.endsWith("2")
                || maskedCard.endsWith("4") || maskedCard.endsWith("6")
                || maskedCard.endsWith("8");            // even card last digit → OK
    }
}
