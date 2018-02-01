package com.bvd.android.agentie.employee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bvd.android.agentie.R;
import com.bvd.android.agentie.model.Trip;

import java.util.List;

/**
 * Created by bara on 2/1/2018.
 */

public class TripAdapter extends ArrayAdapter<Trip> {
    private Context context;

    public TripAdapter(@NonNull Context context, int resource, @NonNull List<Trip> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item_layout_1, parent, false);
        TextView textView = rowView.findViewById(R.id.firstLine1);
        TextView textView2 = rowView.findViewById(R.id.secondLine1);

        Trip trip = getItem(position);

        textView.setText(trip.getName());
        textView2.setText(trip.toString1());


        return rowView;
    }
}
