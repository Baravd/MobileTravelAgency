package com.bvd.android.agentie.client;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bvd.android.agentie.R;
import com.bvd.android.agentie.model.Item;

import java.util.List;

/**
 * Created by bara on 2/2/2018.
 */

public class CustomerAllItemsAdapter extends ArrayAdapter<Item> {
    private Context context;

    public CustomerAllItemsAdapter(@NonNull Context context, int resource,  @NonNull List<Item> objects) {
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

        Item item = getItem(position);

        textView.setText(item.getName());
        textView2.setText(item.toString2());


        return rowView;
    }
}
