package com.app.elixir.TravelB2B.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
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
        public MtplTextView reqType, reqAgent, startDate, endDate, adult, txtComment, destination, reqAgentType, citytxtView;
        MtplButton btnDetail, btnShowInterest;
        TextView total;
        ImageView catImage;
        RatingBar ratingBar;
        ImageView imageFollow;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            reqAgent = (MtplTextView) itemView.findViewById(R.id.reqAgent);
            reqAgentType = (MtplTextView) itemView.findViewById(R.id.reqAgentType);
            reqAgent.setPaintFlags(reqAgent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txtComment = (MtplTextView) itemView.findViewById(R.id.txtcomment);
            citytxtView = (MtplTextView) itemView.findViewById(R.id.citytxtView);
            imageFollow = (ImageView) itemView.findViewById(R.id.img_follow);
            txtComment.setSelected(true);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
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
                    listener.onItemClick(dataSet.get(getAdapterPosition()).getUserId(), "showInt", "");
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
        MtplTextView reqAgentType = holder.reqAgentType;
        RatingBar ratingBar = holder.ratingBar;
        MtplTextView citytxtView = holder.citytxtView;
        ImageView img_Follow = holder.imageFollow;
        txtComment.setText(dataSet.get(position).getUserComment());
        reqAgent.setText(dataSet.get(position).getFirst_name() + " " + dataSet.get(position).getLast_name());

        if (dataSet.get(position).getIsBusinessBuddy().equals("1")) {
            //btnFollow.setVisibility(View.GONE);
            // img_Follow.setVisibility(View.VISIBLE);
        } else {
            // btnFollow.setVisibility(View.VISIBLE);
            //img_Follow.setVisibility(View.GONE);
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
        total.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getTotal_budget());
        if (!dataSet.get(position).getRating().equals("") && !dataSet.get(position).getRating().equals("null")) {
            int ratte = (int) Math.round(Double.parseDouble(dataSet.get(position).getRating()));
            ratingBar.setProgress(ratte);
        }

        int totAdult = 0;
        int totChild = 0;
        int totMemb = 0;

        try {
            totAdult = Integer.parseInt(dataSet.get(position).getAdult());
        } catch (Exception e) {
            totAdult = 0;
        }
        try {
            totChild = Integer.parseInt(dataSet.get(position).getChildren());
        } catch (Exception e) {
            totChild = 0;
        }
        totMemb = totAdult + totChild;

        adult.setText(String.valueOf(totMemb));
        destination.setText(dataSet.get(position).getDestination_city().trim()); //+ " " + dataSet.get(position).getState_id()


        if (dataSet.get(position).getCategory_id().toString().equals("1")) {
            catImg.setImageResource(R.drawable.pp);
            reqAgentType.setText(" ( " + "Package" + " )");
            citytxtView.setText(context.getString(R.string.destination_city));

        } else if (dataSet.get(position).getCategory_id().toString().equals("2")) {

            citytxtView.setText(context.getString(R.string.pickup_city));
            catImg.setImageResource(R.drawable.tt);
            reqAgentType.setText(" ( " + "Transport" + " )");


        } else {
            catImg.setImageResource(R.drawable.hh);
            citytxtView.setText(context.getString(R.string.destination_city));
            reqAgentType.setText(" ( " + "Hotel" + " )");

        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
