package app.Entities;

import app.annotations.Column;

import java.time.LocalDate;

public class Account {
    @Column
    private long number;
    @Column
    private String name;
    @Column
    private float balance;
    @Column
    private String type;
    @Column
    private String owner;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + number +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public float getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public long getNumber() {
        return number;
    }


    public String numbertoString() {
        return ""+number;
    }

    public String getAccountName() {
        return name + " " + number;
    }
}
