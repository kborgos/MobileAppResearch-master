package com.example.katya.models;

import java.io.Serializable;

/**
 * Created by Katya on 11/2/2014.
 */
public class Physician implements Serializable {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Address address;
    private String specialty;

    public Physician(){

    }

    public Physician(String fN, String lN, String pN, String e, Address a, String sp){
        this.firstName = fN;
        this.lastName = lN;
        this.phoneNumber = pN;
        this.email = e;
        this.address = a;
        this.specialty = sp;
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

    public void setAddress(Address a){
        this.address = a;
    }

    public void setSpecialty(String sp) { this.specialty = sp; }

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

    public Address getAddress() {
        return this.address;
    }

    public String getSpecialty() { return this.specialty; }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }

}
