package com.app.elixir.TravelB2B.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptCertificate extends RecyclerView.Adapter<adptCertificate.MyViewHolder> {


    private ArrayList<String> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptCertificate(Context context, ArrayList<String> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgCertificate;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgCertificate = (ImageView) itemView.findViewById(R.id.imgCerTifiacate);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptcerti, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ImageView imageView = holder.imgCertificate;

        // imageView.setImageDrawable();

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
