package com.example.katya.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.katya.adapters.CustomMyDoctorsListViewAdapter;
import com.example.katya.models.Physician;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class BookAppointment extends Activity {

    private CustomMyDoctorsListViewAdapter physiciansAdapter;
    private ArrayList<Physician> myPhysicians  = new ArrayList<Physician>();

    private static final String DEBUG_TAG = "MyPhysicians";
    private EditText filterText = null;

    private String url = "http://192.168.1.111:3000/doctor/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        // send the request to get all contacts
        this.connect();
    }

    /**
     * Sets up the activity; listview adapters, listeners, etc.
     */
    private void setUp() {

        //obtain the listview that will display the patient's physicians
        ListView upcomingList = (ListView) findViewById(R.id.myPhysiciansListView);

        physiciansAdapter = new CustomMyDoctorsListViewAdapter(this, convertToString(this.myPhysicians));
        upcomingList.setAdapter(physiciansAdapter);
        upcomingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3){
                //grab the appointment that was selected and move to the next activity
                Intent intent = new Intent(getApplicationContext(), NewAppointment.class);
                intent.putExtra("Physician", BookAppointment.this.myPhysicians.get(position));
                startActivity(intent);
            }
        });
    }

    /**
     * Adds "fake" physicians data for testing purposes.
     * @return a String array version of the list of physicians
     */
    private ArrayList<String> getPhysicians(){
        ArrayList<String> temp = new ArrayList<String>();

        Physician ph1 = new Physician("Ana C.", "Hernández", "7870000000", "ana@algo.com", null, "Dermatologist");
        Physician ph2 = new Physician("José", "Morales", "7871111111", "jose@algo.com", null, "Pediatrician");

        this.myPhysicians.add(ph1);
        this.myPhysicians.add(ph2);

        for(Physician p: this.myPhysicians){
            String tempS = p.getFirstName() + " " + p.getLastName() + p.getSpecialty();
            temp.add(tempS);
        }
        return temp;    }

    /**
     * Converts myPhysicians items into ArrayList<String> items to be able to use the adapter.
     * @param physicians the list to convert
     * @return the converted list
     */
    private ArrayList<String> convertToString(ArrayList<Physician> physicians){
        ArrayList<String> temp = new ArrayList<String>();
        for(int i=0; i < physicians.size(); i++){
            temp.add(physicians.get(i).toString() + "?" + physicians.get(i).getSpecialty());
        }
        return temp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book_appointment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_find_physician) {
            Intent intent = new Intent(this, FindPhysician.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Establishes the connection with the server.
     * When user clicks button, calls AsyncTask.
     * Before attempting to fetch the URL, makes sure that there is a network connection.
     */
    public void connect() {
        String stringUrl = this.url;

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            Toast.makeText(BookAppointment.this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    // Uses AsyncTask to create a task away from the main UI thread. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {

                JSONObject reader = new JSONObject(result);
                // obtains the list of physicians as JSON
                JSONArray physicians = reader.getJSONArray("doctors");

                // for every physician in the json array...
                for(int i=0; i < physicians.length(); i++){
                    Physician p = new Physician(); // create a new contact obj
                    JSONObject contact = physicians.getJSONObject(i); // extract first json in array

                    //begin setting obt the contact obj according to the jsonobject specifications
                    p.setFirstName(contact.getString("doctor_name"));

                    p.setLastName(contact.getString("doctor_last"));

                    p.setPhoneNumber(contact.getString("doctor_phone"));

                    p.setEmail(contact.getString("doctor_email"));

                    p.setSpecialty(contact.getString("doctor_especialidad"));

                    BookAppointment.this.myPhysicians.add(p);
                }

                BookAppointment.this.setUp();

//                cAdapter = new CustomContactListViewAdapter(MyContactsActivity.this, convertToString(myContacts));
//                MyContactsActivity.this.setListAdapter(cAdapter);
                Toast.makeText(BookAppointment.this, "Success.", Toast.LENGTH_SHORT).show();

            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 250000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    //Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
