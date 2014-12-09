package com.example.katya.models;

import java.io.Serializable;

/**
 * Created by Katya on 11/2/2014.
 */
public class Appointment implements Serializable {

    private String ETS;
    private String hour;
    private String date;

    private Physician specialist;

    private boolean checkedIn;
    private boolean confirmed;
    private boolean today;

    public Appointment(){
    }

    public Appointment(String e, String h, Physician s, String d, boolean c, boolean co, boolean to){
        this.ETS = e;
        this.hour = h;
        this.specialist = s;
        this.date = d;
        this.checkedIn = c;
        this.confirmed = co;
        this.today = to;
    }

    public void setETS(String e){
        this.ETS = e;
    }

    public void setHour(String h){
        this.hour = h;
    }

    public void setSpecialist(Physician s){
        this.specialist = s;
    }

    public void setDate(String d){
        this.date = d;
    }

    public void setCheckedIn(boolean c) { this.checkedIn = c; }

    public void setConfirmed(boolean co) { this.confirmed = co; }

    public void setToday(boolean to) { this.today = to; }

    public String getETS(){
        return this.ETS;
    }

    public String getHour(){
        return this.hour;
    }

    public Physician getSpecialist(){
        return this.specialist;
    }

    public String getDate(){
        return this.date;
    }

    public boolean isCheckedIn() { return this.checkedIn; }

    public boolean isConfirmed() { return this.confirmed; }

    public boolean isToday() { return this.today; }

}
