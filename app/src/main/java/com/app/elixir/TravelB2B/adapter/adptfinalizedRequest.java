package com.app.elixir.TravelB2B.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        public MtplTextView txtRefid, txttotBudget, txtRexType, txtMebers;
        MtplButton btnDetail, btnChat, btnTestimonial;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtRefid = (MtplTextView) itemView.findViewById(R.id.txtRefid);
            txttotBudget = (MtplTextView) itemView.findViewById(R.id.txttotBudget);
            txtRexType = (MtplTextView) itemView.findViewById(R.id.txtRexType);
            txtMebers = (MtplTextView) itemView.findViewById(R.id.txtMebers);

            btnDetail = (MtplButton) itemView.findViewById(R.id.btnDetail);
            btnChat = (MtplButton) itemView.findViewById(R.id.btnChat);
            btnTestimonial = (MtplButton) itemView.findViewById(R.id.btntestimonial);
            btnDetail.setOnClickListener(this);
            btnChat.setOnClickListener(this);
            btnTestimonial.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnChat:
                    listener.onItemClick("chat", dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getId(), "");
                    break;
                case R.id.btnDetail:
                    listener.onItemClick("detail", dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getId(), "");
                    break;
                case R.id.btntestimonial:
                    listener.onItemClick("testi", dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getUserName());
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


        textViewReqId.setText(dataSet.get(position).getReference_id());

        int mebersInt = 0;
        try {
            mebersInt = Integer.parseInt(dataSet.get(position).getAdult()) + Integer.parseInt(dataSet.get(position).getChildren());
        } catch (Exception e) {
            mebersInt = 0;

        }

        mebers.setText(String.valueOf(mebersInt));

        reqType.setText(CM.getReqType(dataSet.get(position).getCategory_id()));


        total.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getTotal_budget());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
