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
import com.app.elixir.TravelB2B.model.pojoAdvert;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.utils.CM;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptAdvt extends RecyclerView.Adapter<adptAdvt.MyViewHolder> {


    private ArrayList<pojoAdvert> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptAdvt(Context context, ArrayList<pojoAdvert> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView reqType, refType, startDate, endDate, total, adult;
        MtplButton btnUnBlockUser;
        MtplTextView webSite, hotel_Name, charges, hotelType;

        public MyViewHolder(View itemView) {
            super(itemView);
            webSite = (MtplTextView) itemView.findViewById(R.id.txtWebsite);
            hotel_Name = (MtplTextView) itemView.findViewById(R.id.txthotel_name);
            charges = (MtplTextView) itemView.findViewById(R.id.txtCharges);
            hotelType = (MtplTextView) itemView.findViewById(R.id.txthotel_type);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptadvt, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView webSite = holder.webSite;
        TextView hotel_Name = holder.hotel_Name;
        TextView charges = holder.charges;
        TextView hotelType = holder.hotelType;
        webSite.setText(dataSet.get(position).getWebsite());
        hotel_Name.setText(dataSet.get(position).getHotel_name());
        charges.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getCheap_tariff() + " - " + dataSet.get(position).getExpensive_tariff());
        hotelType.setText(CM.setHotelCat(dataSet.get(position).getHotel_type()));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
