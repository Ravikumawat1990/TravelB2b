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

import java.util.ArrayList;

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
        public MtplTextView txtHotelName, txtViewCount;
        MtplButton btnUnBlockUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtHotelName = (MtplTextView) itemView.findViewById(R.id.txthName);
            txtViewCount = (MtplTextView) itemView.findViewById(R.id.txtvcount);

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
        TextView name = holder.txtHotelName;
        TextView count = holder.txtViewCount;

        name.setText(dataSet.get(position).getHotelname());
        count.setText(dataSet.get(position).getViewer_count());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}

