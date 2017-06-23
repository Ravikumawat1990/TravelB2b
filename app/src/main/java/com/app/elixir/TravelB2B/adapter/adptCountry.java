package com.app.elixir.TravelB2B.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.pojos.pojoCountry;

import java.util.ArrayList;


public class adptCountry extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private ArrayList<pojoCountry> asr;

    public adptCountry(Context context, ArrayList<pojoCountry> asr) {
        this.asr = asr;
        activity = context;
    }


    public int getCount() {
        return asr.size();
    }

    public Object getItem(int i) {
        return asr.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Typeface face = Typeface.createFromAsset(activity.getAssets(), "Roboto-Regular_0.ttf");
        TextView txt = new TextView(activity);
        txt.setPadding(32, 16, 32, 16);

        txt.setTextSize(18);
        txt.setTypeface(face);
        txt.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        if (asr.get(position) != null) {
            txt.setText(asr.get(position).getCountry_name());
        } else {
            txt.setText("");
        }


        return txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        View view1 = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.conntylayout, viewgroup, false);
        Typeface face = Typeface.createFromAsset(activity.getAssets(), "Roboto-Regular_0.ttf");
        TextView txt = new TextView(activity);
        txt.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        txt.setTypeface(face);
        txt.setPadding(32, 16, 32, 16);

        txt.setTextSize(15);
        if (asr.get(i) != null) {
            txt.setText(asr.get(i).getCountry_name());
        } else {
            txt.setText("");
        }

        return txt;
    }


}