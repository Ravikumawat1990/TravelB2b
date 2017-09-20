package com.app.elixir.TravelB2B.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListeners;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoFinalizeReq;
import com.app.elixir.TravelB2B.utils.CM;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptfinalizedRequest extends RecyclerView.Adapter<adptfinalizedRequest.MyViewHolder> {


    private ArrayList<pojoFinalizeReq> dataSet;
    Context context;
    public OnItemClickListeners listener;

    public adptfinalizedRequest(Context context, ArrayList<pojoFinalizeReq> dataSet) {
        this.dataSet = dataSet;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView txtRefid, txttotBudget, txtRexType, txtMebers, reqAgentType, startDate, endDate, txtQuotPrice;
        MtplButton btnDetail, btnChat, btnTestimonial;
        ImageView catImage;
        MtplTextView txt_AgentName;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtRefid = (MtplTextView) itemView.findViewById(R.id.txtRefid);
            txt_AgentName = (MtplTextView) itemView.findViewById(R.id.txt_AgentName);
            txt_AgentName.setPaintFlags(txt_AgentName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txttotBudget = (MtplTextView) itemView.findViewById(R.id.txttotBudget);
            //  txtRexType = (MtplTextView) itemView.findViewById(R.id.txtRexType);
            txtQuotPrice = (MtplTextView) itemView.findViewById(R.id.txtQuotPrice);

            txtMebers = (MtplTextView) itemView.findViewById(R.id.txtMebers);
            catImage = (ImageView) itemView.findViewById(R.id.imageViewCat);
            startDate = (MtplTextView) itemView.findViewById(R.id.txStartDate);
            endDate = (MtplTextView) itemView.findViewById(R.id.txEndDate);
            btnDetail = (MtplButton) itemView.findViewById(R.id.btnDetail);
            btnChat = (MtplButton) itemView.findViewById(R.id.btnChat);
            btnTestimonial = (MtplButton) itemView.findViewById(R.id.btntestimonial);
            reqAgentType = (MtplTextView) itemView.findViewById(R.id.reqAgentType);
            btnDetail.setOnClickListener(this);
            btnChat.setOnClickListener(this);
            txt_AgentName.setOnClickListener(this);
            btnTestimonial.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnChat:
                    listener.onItemClick("chat", dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getId(), "");
                    break;
                case R.id.btnDetail:
                    listener.onItemClick("detail", dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getId(), dataSet.get(getAdapterPosition()).getCategory_id());
                    break;
                case R.id.btntestimonial:
                    listener.onItemClick("testi", dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getUserName());
                    break;
                case R.id.txt_AgentName:
                    listener.onItemClick("userdetail", dataSet.get(getAdapterPosition()).getId(), "", "");
                    break;


            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListeners mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptfinalierequest, parent, false);
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
        TextView startDate = holder.startDate;
        TextView endDate = holder.endDate;
        TextView txtQuotPrice = holder.txtQuotPrice;
        MtplTextView txt_AgentName = holder.txt_AgentName;
        textViewReqId.setText(dataSet.get(position).getReference_id());
        MtplTextView reqAgentType = holder.reqAgentType;
        int mebersInt = 0;
        try {
            mebersInt = Integer.parseInt(dataSet.get(position).getAdult()) + Integer.parseInt(dataSet.get(position).getChildren());
        } catch (Exception e) {
            mebersInt = 0;

        }

        txt_AgentName.setText(dataSet.get(position).getUserName());
        txtQuotPrice.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getQuotation_price());
        String txtStartDt = "";
        String txtEndDt = "";
        if (dataSet.get(position).getCategory_id().equals("1") || dataSet.get(position).getCategory_id().equals("3")) {

            try {
                txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy", dataSet.get(position).getCheck_in().trim().toString());
            } catch (Exception e) {
                //  txtStartDt = dataSet.get(position).getCheck_in();
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

        mebers.setText(String.valueOf(mebersInt));

        // reqType.setText(CM.getReqType(dataSet.get(position).getCategory_id()));
        startDate.setText(txtStartDt);
        endDate.setText(txtEndDt);

        total.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getTotal_budget());
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
