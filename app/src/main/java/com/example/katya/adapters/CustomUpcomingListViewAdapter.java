package com.example.katya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.katya.activities.R;

import java.util.ArrayList;

public class CustomUpcomingListViewAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> appointments;

    public CustomUpcomingListViewAdapter(Context context, ArrayList<String> appointments) {
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

        String hour = itemValue.substring(0, 9); // format: XX:XXmin
        Log.i("Hour: ", hour);
        tv.setText(hour);

        String specialist = itemValue.substring(9, 16);
        Log.i("Specialist: ", specialist);
        tv2.setText(specialist);

        String date = itemValue.substring(16, itemValue.length());
        Log.i("Date: ", date);
        tv3.setText(date);

        return rowView;
    }
} 