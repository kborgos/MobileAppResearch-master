package com.example.katya.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.katya.adapters.CustomTodayListViewAdapter;
import com.example.katya.adapters.CustomUpcomingListViewAdapter;
import com.example.katya.models.Appointment;
import com.example.katya.models.Patient;

import java.util.ArrayList;


public class TodayAppointment extends Activity {

    private Menu menu;
    private TextView status;

    private Appointment appointment;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_appointment);

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("Patient");
        appointment = (Appointment) intent.getSerializableExtra("Appointment");

        this.setUp();
    }

    /**
     * Sets up the information in the interface according to the appointment that was selected in the previous interface (My Appointments).
     */
    private void setUp() {

        //sets the actionbar title according the the specialist to visit
        setTitle("Appointment: " + appointment.getSpecialist().getSpecialty());

        //sets the appointment time
        TextView time = (TextView) findViewById(R.id.todayTime);
        time.setText("Today " + appointment.getHour());

        this.status = (TextView) findViewById(R.id.checkInStatus);

        //if the patient has checked in, state so in status text
        if(appointment.isCheckedIn()){
            this.status.setText("Checked In");
        }

        //sets physician's name
        TextView physician = (TextView) findViewById(R.id.physicianName);
        physician.setText("Dr. " + appointment.getSpecialist().toString());

        //sets physician's email
        TextView email = (TextView) findViewById(R.id.physicianEmail);
        email.setText(appointment.getSpecialist().getEmail());

        //sets physician's physical address
        TextView address = (TextView) findViewById(R.id.physicianAddress);
        address.setText(appointment.getSpecialist().getAddress().toString());

        //sets physician's phone
        TextView phone = (TextView) findViewById(R.id.physicianPhone);
        phone.setText(appointment.getSpecialist().getPhoneNumber());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.today_appointment, menu);
        if(appointment.isCheckedIn()){
            menu.findItem(R.id.action_check_in).setVisible(false);
        }
        return true;
    }

    /**
     * Creates a dialog asking patient if they would like to check in to an appointment.
     * @return the dialog that will appear
     */
    private AlertDialog.Builder getDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to check in?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for(Appointment a: TodayAppointment.this.patient.getAppointments()){
                            if(a.getSpecialist().toString().equals(TodayAppointment.this.appointment.getSpecialist().toString())
                                    & a.getSpecialist().getSpecialty().toString().equals(TodayAppointment.this.appointment.getSpecialist().getSpecialty().toString()))
                                a.setCheckedIn(true);

                            //update UI
                            TodayAppointment.this.menu.findItem(R.id.action_check_in).setVisible(false);
                            TodayAppointment.this.status.setText("Checked In");
                            dialog.cancel();
                        }

                        // alert the patient that they have checked in successfully
                        AlertDialog alertDialog = new AlertDialog.Builder(TodayAppointment.this).create();
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("You are now checked in.");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //go back and update patient
                                Intent intent = new Intent(TodayAppointment.this, MyAppointments.class);
                                intent.putExtra("Patient", TodayAppointment.this.patient);
                                startActivity(intent);
                            }
                        });

                        alertDialog.show();
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder;
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

        if (id == R.id.action_check_in) {
            this.getDialog().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
