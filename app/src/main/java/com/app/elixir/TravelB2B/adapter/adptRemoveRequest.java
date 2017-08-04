package com.app.elixir.TravelB2B.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.OnAdapterItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoRemoveReq;
import com.app.elixir.TravelB2B.utils.CM;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptRemoveRequest extends RecyclerView.Adapter<adptRemoveRequest.MyViewHolder> {


    private ArrayList<pojoRemoveReq> dataSet;
    Context context;
    // public OnItemClickListener listener;
    public OnAdapterItemClickListener listener;

    public adptRemoveRequest(Context context, ArrayList<pojoRemoveReq> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView txtRefid, txttotBudget, txtRexType, txtMebers, reqAgentType, startDate, endDate;
        MtplButton btnDetail;
        ImageView catImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtRefid = (MtplTextView) itemView.findViewById(R.id.txtRefid1);
            txttotBudget = (MtplTextView) itemView.findViewById(R.id.txttotBudget1);
            txtRexType = (MtplTextView) itemView.findViewById(R.id.txtRexType1);
            txtMebers = (MtplTextView) itemView.findViewById(R.id.txtAdult1);
            btnDetail = (MtplButton) itemView.findViewById(R.id.btnDetail);
            catImage = (ImageView) itemView.findViewById(R.id.imageViewCat);
            reqAgentType = (MtplTextView) itemView.findViewById(R.id.reqAgentType);
            startDate = (MtplTextView) itemView.findViewById(R.id.txStartDate);
            endDate = (MtplTextView) itemView.findViewById(R.id.txEndDate);
            btnDetail.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnDetail:
                    listener.onItemClick("detail", dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getCategory_id());
                    break;
            }

        }
    }

    public void SetOnItemClickListener(OnAdapterItemClickListener mItemClickListener) {
        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptremoverequest, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MtplTextView textViewReqId = holder.txtRefid;
        MtplTextView mebers = holder.txtMebers;
        MtplTextView total = holder.txttotBudget;
        MtplTextView reqType = holder.txtRexType;
        ImageView catImg = holder.catImage;
        MtplTextView reqAgentType = holder.reqAgentType;
        TextView startDate = holder.startDate;
        TextView endDate = holder.endDate;

        textViewReqId.setText(dataSet.get(position).getReference_id());
        reqType.setText(CM.getReqType(dataSet.get(position).getCategory_id()));

        int mebersInt = 0;
        int adtInt = 0;
        try {
            adtInt = Integer.parseInt(dataSet.get(position).getAdult());
        } catch (Exception e) {
            adtInt = 0;
        }
        int childInt = 0;
        try {
            childInt = Integer.parseInt(dataSet.get(position).getChildren());
        } catch (Exception e) {
            childInt = 0;
        }


        try {
            mebersInt = adtInt + childInt;
        } catch (Exception e) {
            mebersInt = 0;

        }

        String txtStartDt = "";
        String txtEndDt = "";
        if (dataSet.get(position).getCategory_id().equals("1") || dataSet.get(position).getCategory_id().equals("3")) {

            try {
                txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy", dataSet.get(position).getCheck_in().trim().toString());
            } catch (Exception e) {
                txtStartDt = dataSet.get(position).getCheck_in();
            }

            try {
                txtEndDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy", dataSet.get(position).getCheck_out().trim().toString());
            } catch (Exception e) {
                txtEndDt = dataSet.get(position).getCheck_out();
            }
        } else {

            try {
                txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy", dataSet.get(position).getStart_date().trim().toString());
            } catch (Exception e) {
                txtStartDt = dataSet.get(position).getStart_date();
            }

            try {
                txtEndDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy", dataSet.get(position).getEnd_date().trim().toString());
            } catch (Exception e) {
                txtEndDt = dataSet.get(position).getEnd_date();
            }

        }

        startDate.setText(txtStartDt);
        endDate.setText(txtEndDt);


        mebers.setText(String.valueOf(mebersInt));


        total.setText("Budget\n" + dataSet.get(position).getTotal_budget() + "/-");
        if (dataSet.get(position).getCategory_id().toString().equals("1")) {
            catImg.setImageResource(R.drawable.pp);
            reqAgentType.setText(" ( " + "Package" + " )");

        } else if (dataSet.get(position).getCategory_id().toString().equals("2")) {
            catImg.setImageResource(R.drawable.tt);
            reqAgentType.setText(" ( " + "Transport" + " )");
        } else {
            catImg.setImageResource(R.drawable.hh);
            reqAgentType.setText(" ( " + "Hotel" + " )");
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
