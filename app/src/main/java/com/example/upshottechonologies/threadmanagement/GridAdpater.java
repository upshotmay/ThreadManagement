package com.example.upshottechonologies.threadmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdpater extends BaseAdapter {
    int[] img;// = new int[20];
    String[] strings;
    Context context;
    LayoutInflater inflater;

    public GridAdpater(int[] img, String[] strings, Context context) {
        this.img = img;
        this.strings = strings;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        return strings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.grid_item, null);
        ImageView iv = view.findViewById(R.id.iv);
        TextView tv = view.findViewById(R.id.textGrid);
        tv.setText(strings[position]);
        iv.setImageResource(img[position]);
        return view;
    }
}
