package com.example.katya.models;

/**
 * Created by Katya on 11/2/2014.
 */
public class Address {

    private String street;
    private String city;
    private String state;
    private String country;

    public Address(String s, String ci, String st, String c){
        this.street = s;
        this.city = ci;
        this.street = st;
        this.country = c;
    }

    public void setStreet(String s){
        this.street = s;
    }

    public void setCity(String c){
        this.city = c;
    }

    public void setState(String st){
        this.state = st;
    }

    public void setCountry(String c){
        this.country = c;
    }

    public String getStreet(){
        return this.street;
    }

    public String getCity(){
        return this.city;
    }

    public String getState(){
        return this.state;
    }

    public String getCountry(){
        return this.country;
    }

}
