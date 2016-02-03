package com.droidpoker.jay.droidpoker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

public class NamesItemAdapter extends BaseAdapter {
    Context c;
    String[] data;
    private static LayoutInflater inflator = null;


    public NamesItemAdapter(Context c, String[] data){
        this.c = c;
        this.data = data;
        inflator = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return data.length;
    }

    @Override
    public Object getItem(int pos){
        return data[pos];
    }

    @Override
    public long getItemId(int pos){
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        /**
         * returns row view item associated with name in names[pos]
         */
        View v = convertView;

        if(v == null)
            v = inflator.inflate(R.layout.player_name_item, null);

        EditText txt = (EditText)v.findViewById(R.id.player_name_item_et);
        txt.setText(data[pos]);
        return v;
    }
}
