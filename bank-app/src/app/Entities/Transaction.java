package app.Entities;


import app.annotations.Column;

import java.sql.Timestamp;

public class Transaction {
    @Column
    private long id;
    @Column
    private Timestamp date;
    @Column
    private String message;
    @Column
    private long sender;
    @Column
    private long receiver;
    @Column
    private float amount;


    public String getMessage() { return message; }
    public float getAmount() { return amount; }
    public Timestamp getDate() { return date; }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", amount=" + amount +
                '}';
    }

    public String amountToString() {
        return "Transaction{" +
                "amount=" + amount +
                '}';
    }

    public String idToString(){
        return ""+id;
    }
}
