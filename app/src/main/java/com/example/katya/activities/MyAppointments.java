package com.example.katya.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.katya.adapters.CustomTodayListViewAdapter;
import com.example.katya.adapters.CustomUpcomingListViewAdapter;
import com.example.katya.models.Appointment;
import com.example.katya.models.Patient;
import com.example.katya.models.Physician;

import java.util.ArrayList;


public class MyAppointments extends Activity {

    private CustomTodayListViewAdapter todayAdapter;
    private CustomUpcomingListViewAdapter upcomingAdapter;

    private Patient patient;

    private ArrayList<Appointment> todayApps = new ArrayList<Appointment>();
    private ArrayList<Appointment> upcomingApps = new ArrayList<Appointment>();

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_appointments);

        Patient temp = (Patient) getIntent().getSerializableExtra("Patient");
        if(temp != null){
            this.patient = temp; // if the patient has been updated in some other interface, update
        }
        else { // otherwise, keep the old data
            this.initializePatient();
        }

        this.setUp();
    }

    private void initializePatient(){

        this.patient = new Patient();

        Physician ph1 = new Physician("Pedro", "Rivera", "7870000000", "pedro@algo.com", null, "Ophtalmologist");
        Physician ph2 = new Physician("Maria", "Perez", "7871111111", "maria@algo.com", null, "Pediatrician");

        Appointment app1 = new Appointment("15:00min", "03:00PM", ph1, "Dec/04/14", true, true, true);
        Appointment app2 = new Appointment("00:00min", "01:00PM", ph2, "Dec/11/14", false, true, true);

        this.patient.addAppointment(app1);
        this.patient.addAppointment(app2);

        Physician ph3 = new Physician("Ernesto", "Nieves", "7870000000", "ernesto@algo.com", null, "Dentist");
        Physician ph4 = new Physician("Karla", "Jimenez", "7871111111", "karla@algo.com", null, "Neurologist");

        Appointment app3 = new Appointment("00:00min", "12:00PM", ph3, "Nov/29/14", false, true, false);
        Appointment app4 = new Appointment("00:00min", "10:00AM", ph4, "Nov/29/14", false, false, false);

        this.patient.addAppointment(app3);
        this.patient.addAppointment(app4);
    }

    /**
     * Sets up the activity; listview adapters, listeners, etc.
     */
    private void setUp(){

        //obtain the listview that will display today's appointments
        ListView todayList = (ListView) findViewById(R.id.todayListView);
        todayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3){
                //grab the appointment that was selected and move to the next activity
                intent = new Intent(getApplicationContext(), TodayAppointment.class);
                intent.putExtra("Patient", MyAppointments.this.patient);
                intent.putExtra("Appointment", MyAppointments.this.todayApps.get(position));
                startActivity(intent);
            }
        });

        //obtain the listview that will display upcoming appointments
        ListView upcomingList = (ListView) findViewById(R.id.upcomingListView);
        upcomingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3){
                //grab the appointment that was selected and move to the next activity
                intent = new Intent(getApplicationContext(), UpcomingAppointment.class);
                intent.putExtra("Patient", MyAppointments.this.patient);
                intent.putExtra("Appointment", MyAppointments.this.upcomingApps.get(position));
                startActivity(intent);
            }
        });

        todayAdapter = new CustomTodayListViewAdapter(this, getTodayAppointments());
        todayList.setAdapter(todayAdapter);

        upcomingAdapter = new CustomUpcomingListViewAdapter(this, getUpcomingAppointments());
        upcomingList.setAdapter(upcomingAdapter);
    }

    /**
     * Adds "fake" today appointments data for testing purposes.
     * @return a String array version of the list of appointments
     */
    private ArrayList<String> getTodayAppointments(){
        ArrayList<String> temp = new ArrayList<String>();

        for(Appointment a: this.patient.getAppointments()){
            if(a.isToday()) {
                this.todayApps.add(a);
                if(!a.isCheckedIn()){
                    String tempS = "b" + a.getETS() + a.getHour() + a.getSpecialist().getSpecialty();
                    temp.add(tempS);
                }
                else {
                    String tempS = a.getETS() + a.getHour() + a.getSpecialist().getSpecialty();
                    temp.add(tempS);
                }
            }
        }
        return temp;
    }

    /**
     * Adds "fake" upcoming appointments data for testing purposes.
     * @return a String array version of the list of appointments
     */
    private ArrayList<String> getUpcomingAppointments(){
        ArrayList<String> temp = new ArrayList<String>();

        for(Appointment a: this.patient.getAppointments()){
            if(!a.isToday()) {
                this.upcomingApps.add(a);
                String tempS = a.getDate() + a.getHour() + a.getSpecialist().getSpecialty();
                temp.add(tempS);
            }
        }
        return temp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_appointments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_add_appointment) {
            Intent intent = new Intent(this, BookAppointment.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
