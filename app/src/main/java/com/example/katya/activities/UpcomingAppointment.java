package com.example.katya.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.katya.models.Appointment;
import com.example.katya.models.Patient;

import java.util.ArrayList;

public class UpcomingAppointment extends Activity {

    private Appointment appointment;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appointment);

        Intent intent = getIntent();
        appointment = (Appointment) intent.getSerializableExtra("Appointment");
        patient = (Patient) intent.getSerializableExtra("Patient");

        this.setUp();
    }

    /**
     * Sets up the information in the interface according to the appointment that was selected in the previous interface (My Appointments).
     */
    private void setUp() {

        //sets the actionbar title according the the specialist to visit
        setTitle("Appointment: " + appointment.getSpecialist().getSpecialty());

        //sets the appointment time
        TextView time = (TextView) findViewById(R.id.dateTime);
        time.setText(appointment.getDate() + " " + appointment.getHour());

        //if the appointment has been confirmed, state so in status text
        if(appointment.isConfirmed()){
            TextView status = (TextView) findViewById(R.id.confirmationStatus);
            status.setText("Confirmed");
        }

        //sets physician's name
        TextView physician = (TextView) findViewById(R.id.physicianName);
        physician.setText("Dr. " + appointment.getSpecialist().toString());

        //sets physician's email
        TextView email = (TextView) findViewById(R.id.physicianEmail);
        email.setText(appointment.getSpecialist().getEmail());

        //sets physician's phone
        TextView phone = (TextView) findViewById(R.id.physicianPhone);
        phone.setText(appointment.getSpecialist().getPhoneNumber());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.upcoming_appointment, menu);
        return true;
    }

    /**
     * Creates a dialog asking patient if they would like to cancel an appointment.
     * @return the dialog that will appear
     */
    private AlertDialog.Builder getDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cancel appointment?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ArrayList<Appointment> temp = new ArrayList<Appointment>();

                        for(Appointment a: UpcomingAppointment.this.patient.getAppointments()){
                            if(!a.getSpecialist().toString().equals(UpcomingAppointment.this.appointment.getSpecialist().toString())
                                    & !a.getSpecialist().getSpecialty().toString().equals(UpcomingAppointment.this.appointment.getSpecialist().getSpecialty().toString())){
                                    temp.add(a); // adding all prescriptions that are NOT the one I want to delete
                            }
                        }

                        UpcomingAppointment.this.patient.getAppointments().clear();

                        //re-adding
                        for(Appointment a: temp){
                            UpcomingAppointment.this.patient.addAppointment(a);
                        }

                        Intent intent = new Intent(getApplicationContext(), MyAppointments.class);
                        intent.putExtra("Patient", UpcomingAppointment.this.patient);
                        startActivity(intent);
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

        if (id == R.id.action_cancel_appointment) {
            this.getDialog().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
