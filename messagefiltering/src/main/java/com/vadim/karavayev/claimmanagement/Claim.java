package com.vadim.karavayev.claimmanagement;

import java.io.Serializable;

public class Claim implements Serializable {
    private int hospitalId;
    private String doctorName;
    private String doctorType;
    private String insuranceProvider;
    private double claimAmount;

    public Claim(int hospitalId, String doctorName, String doctorType, String insuranceProvider, double claimAmount) {
        this.hospitalId = hospitalId;
        this.doctorName = doctorName;
        this.doctorType = doctorType;
        this.insuranceProvider = insuranceProvider;
        this.claimAmount = claimAmount;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }


}
