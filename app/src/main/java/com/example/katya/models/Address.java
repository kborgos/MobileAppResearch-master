package com.example.katya.models;

import java.io.Serializable;

/**
 * Created by Katya on 11/2/2014.
 */
public class Address implements Serializable {

    private String street;
    private String streetNumber;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    public Address(String s, String stn, String ci, String c, String zip){
        this.street = s;
        this.streetNumber = stn;
        this.city = ci;
        this.country = c;
        this.zipCode = zip;
    }

    public void setStreet(String s){
        this.street = s;
    }

    public void setStreetNumber(String st) { this.streetNumber = st; }

    public void setCity(String c){
        this.city = c;
    }

    public void setState(String st){
        this.state = st;
    }

    public void setCountry(String c){
        this.country = c;
    }

    public void setZipCode(String z) { this.zipCode = z; }

    public String getStreet(){
        return this.street;
    }

    public String getStreetNumber() { return this.streetNumber; }

    public String getCity(){
        return this.city;
    }

    public String getState(){
        return this.state;
    }

    public String getCountry(){
        return this.country;
    }

    public String getZipCode() { return this.zipCode; }

    @Override
    public String toString() { return this.street + " " + this.streetNumber + " " + this.city + " " + this.country + " " + this.zipCode; }

}
