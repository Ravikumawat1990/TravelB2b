package com.app.elixir.TravelB2B.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoPromoReport;
import com.app.elixir.TravelB2B.utils.CM;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 26-Jul-2017.
 */

public class adptPromoReports extends RecyclerView.Adapter<adptPromoReports.MyViewHolder> {


    private ArrayList<pojoPromoReport> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptPromoReports(Context context, ArrayList<pojoPromoReport> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView txtHotelName, txtSrNo, txtDate, txtDuration, txtCities, txtView;
        MtplButton btnUnBlockUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            // txtHotelName = (MtplTextView) itemView.findViewById(R.id.txthName);
            txtSrNo = (MtplTextView) itemView.findViewById(R.id.txtSrNo);
            txtDate = (MtplTextView) itemView.findViewById(R.id.txtpDate);
            txtDuration = (MtplTextView) itemView.findViewById(R.id.txtduration);
            txtView = (MtplTextView) itemView.findViewById(R.id.txtvcount);
            txtCities = (MtplTextView) itemView.findViewById(R.id.txtCities);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptpromoreport, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // TextView name = holder.txtHotelName;
        TextView srNo = holder.txtSrNo;
        TextView date = holder.txtDate;
        TextView duration = holder.txtDuration;
        TextView view = holder.txtView;
        TextView cities = holder.txtCities;

        //name.setText(dataSet.get(position).getHotelname());
        int i = position + 1;
        srNo.setText("" + i);
        try {
            date.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", dataSet.get(position).getDateOfPromo()));
        } catch (Exception e) {
            date.setText(dataSet.get(position).getDateOfPromo());
        }
        duration.setText(dataSet.get(position).getDuration());
        view.setText(dataSet.get(position).getViewer_count());
        if (dataSet.get(position).getCities() != null) {
            if (!dataSet.get(position).getCities().equalsIgnoreCase("")) {
                cities.setText(dataSet.get(position).getCities());
            } else {
                cities.setText("-- --");
            }
        } else {
            cities.setText("-- --");
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}

