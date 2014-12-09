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

public class CustomMyDoctorsListViewAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> appointments;

    public CustomMyDoctorsListViewAdapter(Context context, ArrayList<String> appointments) {
        super(context, R.layout.custom_list_view_mydoctors_adapter, appointments);
        this.context = context;
        this.appointments = appointments;
    }

    @Override
    public View getView(int id, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.custom_list_view_mydoctors_adapter, parent, false);
        rowView.setBackgroundColor(id % 2 == 0 ? Color.rgb(219, 219, 219) : Color.WHITE);

        TextView tv = (TextView) rowView.findViewById(R.id.name);
        TextView tv2 = (TextView) rowView.findViewById(R.id.specialistlabel);

        String itemValue = appointments.get(id);

        String delims = "[?]";
        String[] tokens = itemValue.split(delims);

        tv.setText(tokens[0]);
        tv2.setText(tokens[1]);
        return rowView;
    }
} 