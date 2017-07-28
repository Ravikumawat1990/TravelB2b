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
import com.app.elixir.TravelB2B.interfaceimpl.OnAdapterItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoMyResposne;
import com.app.elixir.TravelB2B.utils.CM;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptRespondToRequest extends RecyclerView.Adapter<adptRespondToRequest.MyViewHolder> {


    private ArrayList<pojoMyResposne> dataSet;
    Context context;
    OnAdapterItemClickListener listener;

    public adptRespondToRequest(Context context, ArrayList<pojoMyResposne> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private CardView rootView;
        public MtplTextView reqType, reqAgent, startDate, endDate, adult, txtComment, destination;
        MtplButton btnDetail, btnShowInterest;
        TextView total;
        ImageView catImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            reqAgent = (MtplTextView) itemView.findViewById(R.id.reqAgent);
            reqAgent.setPaintFlags(reqAgent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txtComment = (MtplTextView) itemView.findViewById(R.id.txtcomment);
            startDate = (MtplTextView) itemView.findViewById(R.id.txStartDate);
            endDate = (MtplTextView) itemView.findViewById(R.id.txEndDate);
            total = (TextView) itemView.findViewById(R.id.txtTot);
            adult = (MtplTextView) itemView.findViewById(R.id.txtAdult);
            destination = (MtplTextView) itemView.findViewById(R.id.txtDestination);
            catImage = (ImageView) itemView.findViewById(R.id.imageViewCat);
            btnDetail = (MtplButton) itemView.findViewById(R.id.btnDetail);
            btnShowInterest = (MtplButton) itemView.findViewById(R.id.btnShowInterest);
            btnShowInterest.setOnClickListener(this);
            btnDetail.setOnClickListener(this);
            reqAgent.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnDetail:
                    listener.onItemClick(dataSet.get(getAdapterPosition()).getUserId(), "detail", dataSet.get(getAdapterPosition()).getCategory_id());
                    break;
                case R.id.btnShowInterest:
                    //getUserId changed with getId
                    listener.onItemClick(dataSet.get(getAdapterPosition()).getId(), "showInt", "");
                    break;
                case R.id.reqAgent:
                    //getUserId changed with getId
                    listener.onItemClick(dataSet.get(getAdapterPosition()).getId(), "showAgent", "");
                    break;


            }

        }
    }

    public void SetOnItemClickListener(OnAdapterItemClickListener mItemClickListener) {

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
        TextView reqAgent = holder.reqAgent;
        TextView txtComment = holder.txtComment;
        TextView startDate = holder.startDate;
        TextView endDate = holder.endDate;
        TextView total = holder.total;
        TextView adult = holder.adult;
        TextView destination = holder.destination;
        ImageView catImg = holder.catImage;

        reqAgent.setText(dataSet.get(position).getFirst_name() + " " + dataSet.get(position).getLast_name());
        txtComment.setText(dataSet.get(position).getUserComment());

        String txtStartDt = "";
        try {
            txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", dataSet.get(position).getCheck_in().trim().toString());
        } catch (Exception e) {
            txtStartDt = dataSet.get(position).getCheck_in();
        }
        String txtEndDt = "";
        try {
            txtEndDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", dataSet.get(position).getCheck_out().trim().toString());
        } catch (Exception e) {
            txtEndDt = dataSet.get(position).getCheck_out();
        }


        startDate.setText(txtStartDt);
        endDate.setText(txtEndDt);
        total.setText("Budget\n" + dataSet.get(position).getTotal_budget() + "/-");
        int totMemb = 0;
        try {
            totMemb = Integer.parseInt(dataSet.get(position).getAdult()) + Integer.parseInt(dataSet.get(position).getChildren());
        } catch (Exception e) {
            totMemb = 0;
        }
        adult.setText(String.valueOf(totMemb));
        destination.setText(dataSet.get(position).getDestination_city()); //+ " " + dataSet.get(position).getState_id()


        if (dataSet.get(position).getCategory_id().toString().equals("1")) {
            catImg.setImageResource(R.drawable.pp);

        } else if (dataSet.get(position).getCategory_id().toString().equals("2")) {
            catImg.setImageResource(R.drawable.tt);
        } else {
            catImg.setImageResource(R.drawable.hh);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
