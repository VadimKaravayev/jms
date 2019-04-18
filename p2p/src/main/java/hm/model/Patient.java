package hm.model;

import java.io.Serializable;

public class Patient implements Serializable {
    private int id;
    private String name;
    private String insuranceProvider;
    private double copay;
    private double amountToBePaid;

    public Patient(int id, String name, String insuranceProvider, double copay, double amountToBePaid) {
        this.id = id;
        this.name = name;
        this.insuranceProvider = insuranceProvider;
        this.copay = copay;
        this.amountToBePaid = amountToBePaid;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public double getCopay() {
        return copay;
    }

    public double getAmountToBePaid() {
        return amountToBePaid;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", insuranceProvider='" + insuranceProvider + '\'' +
                ", copay=" + copay +
                ", amountToBePaid=" + amountToBePaid +
                '}';
    }
}
