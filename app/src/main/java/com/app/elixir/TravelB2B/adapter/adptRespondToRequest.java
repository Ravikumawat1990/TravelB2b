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
import com.app.elixir.TravelB2B.model.PojoMyResponse;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptRespondToRequest extends RecyclerView.Adapter<adptRespondToRequest.MyViewHolder> {


    private ArrayList<PojoMyResponse> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptRespondToRequest(Context context, ArrayList<PojoMyResponse> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView reqType, refType, startDate, endDate, total, adult;
        MtplButton btnDetail, btnShowInterest;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            reqType = (MtplTextView) itemView.findViewById(R.id.txtReqType);
            reqType = (MtplTextView) itemView.findViewById(R.id.txtRefId);
            startDate = (MtplTextView) itemView.findViewById(R.id.txStartDate);
            endDate = (MtplTextView) itemView.findViewById(R.id.txEndDate);
            total = (MtplTextView) itemView.findViewById(R.id.txtTot);
            adult = (MtplTextView) itemView.findViewById(R.id.txtAdult);
            btnDetail = (MtplButton) itemView.findViewById(R.id.btnDetail);
            btnShowInterest = (MtplButton) itemView.findViewById(R.id.btnShowInterest);

            btnDetail.setOnClickListener(this);
            btnShowInterest.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnDetail:
                    listener.onItemClick("detail");
                    break;
                case R.id.btnShowInterest:
                    listener.onItemClick("showInte");


            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptrespontorequest, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView textViewReqType = holder.reqType;
        textViewReqType.setText(dataSet.get(position).getAdult());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
