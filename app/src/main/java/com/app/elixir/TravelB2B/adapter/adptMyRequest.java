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
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoMyRequest;
import com.app.elixir.TravelB2B.utils.CM;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptMyRequest extends RecyclerView.Adapter<adptMyRequest.MyViewHolder> {


    private ArrayList<pojoMyRequest> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptMyRequest(Context context, ArrayList<pojoMyRequest> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView reqType, reqAgent, startDate, endDate, adult, txtComment, destination, txtRefId;
        MtplButton btnRemoveReq, btnCheckRes;
        ImageView catImage;
        TextView total;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            reqAgent = (MtplTextView) itemView.findViewById(R.id.reqAgent);
            txtRefId = (MtplTextView) itemView.findViewById(R.id.txtRefId);


            txtComment = (MtplTextView) itemView.findViewById(R.id.txtcomment);
            startDate = (MtplTextView) itemView.findViewById(R.id.txStartDate);
            endDate = (MtplTextView) itemView.findViewById(R.id.txEndDate);
            total = (TextView) itemView.findViewById(R.id.txtTot);
            adult = (MtplTextView) itemView.findViewById(R.id.txtAdult);
            destination = (MtplTextView) itemView.findViewById(R.id.txtDestination);
            catImage = (ImageView) itemView.findViewById(R.id.imageViewCat);
            btnRemoveReq = (MtplButton) itemView.findViewById(R.id.btnRemoveReq);
            btnCheckRes = (MtplButton) itemView.findViewById(R.id.btnCheckRes);

            btnRemoveReq.setOnClickListener(this);
            btnCheckRes.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnRemoveReq:
                    listener.onItemClick("remove", dataSet.get(getAdapterPosition()).getRequest_id());
                    break;
                case R.id.btnCheckRes:
                    listener.onItemClick("check", dataSet.get(getAdapterPosition()).getRequest_id());
            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptmyrequest, parent, false);
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
        TextView txtRefId = holder.txtRefId;


        TextView destination = holder.destination;
        ImageView catImg = holder.catImage;


        txtRefId.setText(dataSet.get(position).getReference_id());
        reqAgent.setText(dataSet.get(position).getFirst_name() + " " + dataSet.get(position).getLast_name());
        txtComment.setText(dataSet.get(position).getComment());

        String txtStartDt = "";
        try {
            txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", dataSet.get(position).getCheck_in());
        } catch (Exception e) {
            txtStartDt = dataSet.get(position).getStart_date();
        }
        String txtEndDt = "";
        try {
            txtEndDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", dataSet.get(position).getCheck_out());
        } catch (Exception e) {
            txtEndDt = dataSet.get(position).getEnd_date();
        }


        startDate.setText(txtStartDt);
        endDate.setText(txtEndDt);
        total.setText("Budget\n" + context.getString(R.string.rsSymbol) + "" + dataSet.get(position).getTotal_budget());
        int totMemb = 0;
        try {
            totMemb = Integer.parseInt(dataSet.get(position).getAdult()) + Integer.parseInt(dataSet.get(position).getChildren());
        } catch (Exception e) {
            totMemb = 0;
        }
        adult.setText(String.valueOf(totMemb));
        destination.setText(dataSet.get(position).getDestination_city());


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
