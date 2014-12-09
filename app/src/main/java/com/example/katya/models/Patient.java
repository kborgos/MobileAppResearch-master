package com.example.katya.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Katya on 11/2/2014.
 */
public class Patient implements Serializable {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Address address;
    private ArrayList<Appointment> appointments;

    public Patient(){
        this.appointments = new ArrayList<Appointment>();
    }

    public Patient(String fN, String lN, String pN, String e){
        this.firstName = fN;
        this.lastName = lN;
        this.phoneNumber = pN;
        this.email = e;
        this.appointments = new ArrayList<Appointment>();
    }

    public void setFirstName(String fN){
        this.firstName = fN;
    }

    public void setLastName(String lN){
        this.lastName = lN;
    }

    public void setPhoneNumber(String pN){
        this.phoneNumber = pN;
    }

    public void setEmail(String e){
        this.email = e;
    }

    public void addAppointment(Appointment a) { this.appointments.add(a); }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public String getEmail(){
        return this.email;
    }

    public ArrayList<Appointment> getAppointments() { return this.appointments; }

}
