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
public class adptCheckResponse extends RecyclerView.Adapter<adptCheckResponse.MyViewHolder> {


    private ArrayList<PojoMyResponse> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptCheckResponse(Context context, ArrayList<PojoMyResponse> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView reqType, refType, startDate, endDate, total, adult;
        MtplButton btnShare, btnChat, btnAccept, btnBlock, btnRate;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            reqType = (MtplTextView) itemView.findViewById(R.id.txtReqType);
            reqType = (MtplTextView) itemView.findViewById(R.id.txtRefId);
            startDate = (MtplTextView) itemView.findViewById(R.id.txStartDate);
            endDate = (MtplTextView) itemView.findViewById(R.id.txEndDate);
            total = (MtplTextView) itemView.findViewById(R.id.txtTot);
            adult = (MtplTextView) itemView.findViewById(R.id.txtAdult);

            btnShare = (MtplButton) itemView.findViewById(R.id.btnShare);
            btnChat = (MtplButton) itemView.findViewById(R.id.btnChat);
            btnAccept = (MtplButton) itemView.findViewById(R.id.btnAccept);
            btnBlock = (MtplButton) itemView.findViewById(R.id.btnBlock);
            btnRate = (MtplButton) itemView.findViewById(R.id.btnRate);

            btnShare.setOnClickListener(this);
            btnChat.setOnClickListener(this);
            btnAccept.setOnClickListener(this);
            btnBlock.setOnClickListener(this);
            btnRate.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnChat:
                    listener.onItemClick("chat");
                    break;
                case R.id.btnShare:
                    listener.onItemClick("share");
                    break;
                case R.id.btnAccept:
                    listener.onItemClick("btnAccept");
                    break;
                case R.id.btnRate:
                    listener.onItemClick("rate");
                    break;
                case R.id.btnBlock:
                    listener.onItemClick("block");
                    break;
            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptcehckresponse, parent, false);
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
