package com.example.robert.ormlitetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robert.ormlitetest.data.SimpleData;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Robert on 12.05.2017.
 */

public class SimpleAdapter extends ArrayAdapter<SimpleData> {
    private final Context context;
    private List<SimpleData> items;

    public SimpleAdapter(Context context, List<SimpleData> resource) {
        super(context, -1, resource);
        this.context = context;
        this.items = resource;
    }

    @Nullable
    @Override
    public SimpleData getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.lbl_Name);
        textView.setText(items.get(position).string);

        TextView txt_ID = (TextView) rowView.findViewById(R.id.lbl_ID);
        String text = String.valueOf(items.get(position).id);
        txt_ID.setText(text);

        return rowView;
    }

}
