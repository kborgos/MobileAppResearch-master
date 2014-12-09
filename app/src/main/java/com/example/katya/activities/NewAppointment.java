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
import com.example.katya.models.Physician;

public class NewAppointment extends Activity {

    private Physician physician = new Physician();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        Intent intent = getIntent();
        physician = (Physician) intent.getSerializableExtra("Physician");

        this.setUp();
    }

    /**
     * Sets up the information in the interface according to the appointment that was selected in the previous interface (My Appointments).
     */
    private void setUp() {

        //sets the actionbar title according the the specialist to visit
        setTitle("New Appointment: " + physician.getSpecialty());

        //sets the appointment time
        TextView name = (TextView) findViewById(R.id.name);
        name.setText("Dr. " + physician.toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_appointment, menu);
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
        return super.onOptionsItemSelected(item);
    }
}
