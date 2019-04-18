package com.vadim.karavayev.jms.model;

import java.io.Serializable;

public class Employee implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String designation;
    private String email;
    private String phone;

    public Employee(int id, String firstName, String lastName, String designation, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", designation='" + designation + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
