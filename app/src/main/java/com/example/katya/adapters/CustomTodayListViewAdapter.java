package com.example.katya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.katya.activities.R;

import java.util.ArrayList;

public class CustomTodayListViewAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> appointments;

    public CustomTodayListViewAdapter(Context context, ArrayList<String> appointments) {
        super(context, R.layout.custom_list_view_adapter, appointments);
        this.context = context;
        this.appointments = appointments;
    }

    @Override
    public View getView(int id, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.custom_list_view_adapter, parent, false);
        rowView.setBackgroundColor(id % 2 == 0 ? Color.rgb(219, 219, 219) : Color.WHITE);

        TextView tv = (TextView) rowView.findViewById(R.id.ets);
        TextView tv2 = (TextView) rowView.findViewById(R.id.hourlabel);
        TextView tv3 = (TextView) rowView.findViewById(R.id.specialistlabel);

        String itemValue = appointments.get(id);

        if(itemValue.charAt(0) == 'b') {
            String ETS = itemValue.substring(1, 9); // format: XX:XXmin
            Log.i("ETS: ", ETS);
            tv.setText(ETS);
            tv.setTypeface(null, Typeface.BOLD);

            String hour = itemValue.substring(9, 16); // format: XX:XXXM
            Log.i("Hour: ", hour);
            tv2.setText(hour);
            tv2.setTypeface(null, Typeface.BOLD);

            String specialist = itemValue.substring(16, itemValue.length());
            Log.i("Specialist: ", specialist);
            tv3.setText(specialist);
            tv3.setTypeface(null, Typeface.BOLD);
        }

        else {
            String ETS = itemValue.substring(0, 8); // format: XX:XXmin
            Log.i("ETS: ", ETS);
            tv.setText(ETS);

            String hour = itemValue.substring(8, 15); // format: XX:XXXM
            Log.i("Hour: ", hour);
            tv2.setText(hour);

            String specialist = itemValue.substring(15, itemValue.length());
            Log.i("Specialist: ", specialist);
            tv3.setText(specialist);
        }

        return rowView;
    }
} 