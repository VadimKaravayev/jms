package model;

import java.io.Serializable;

public class Passenger implements Serializable {

    private static int idCounter = 1;

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Passenger(String firstName, String lastName, String email, String phone) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }
}
