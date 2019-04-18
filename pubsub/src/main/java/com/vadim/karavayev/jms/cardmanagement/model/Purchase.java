package com.vadim.karavayev.jms.cardmanagement.model;

import java.io.Serializable;

public class Purchase implements Serializable {
    private int id;
    private String vendor;
    private double amount;

    public Purchase(int id, String vendor, double amount) {
        this.id = id;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';
    }
}
