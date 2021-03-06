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
        public MtplTextView reqType, startDate, endDate, adult, txtComment, requestType, txtRefId, citytxtView, txtDestination;
        MtplButton btnRemoveReq, btnCheckRes, btnDetail;
        ImageView catImage;
        TextView total;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtRefId = (MtplTextView) itemView.findViewById(R.id.txtRefId);
            citytxtView = (MtplTextView) itemView.findViewById(R.id.citytxtView);
            txtComment = (MtplTextView) itemView.findViewById(R.id.txtcomment);
            txtComment.setSelected(true);
            startDate = (MtplTextView) itemView.findViewById(R.id.txStartDate);
            endDate = (MtplTextView) itemView.findViewById(R.id.txEndDate);
            total = (TextView) itemView.findViewById(R.id.txtTot);
            adult = (MtplTextView) itemView.findViewById(R.id.txtAdult);
            requestType = (MtplTextView) itemView.findViewById(R.id.txtDestination);
            txtDestination = (MtplTextView) itemView.findViewById(R.id.txtDestination1);
            catImage = (ImageView) itemView.findViewById(R.id.imageViewCat);
            btnRemoveReq = (MtplButton) itemView.findViewById(R.id.btnRemoveReq);
            btnCheckRes = (MtplButton) itemView.findViewById(R.id.btnCheckRes);
            btnDetail = (MtplButton) itemView.findViewById(R.id.btnDetail);


            btnRemoveReq.setOnClickListener(this);
            btnCheckRes.setOnClickListener(this);
            btnDetail.setOnClickListener(this);
            //   reqAgent.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnRemoveReq:
                    listener.onItemClick("remove", dataSet.get(getAdapterPosition()).getRequest_id());
                    break;
                case R.id.btnCheckRes:
                    if (dataSet.get(getAdapterPosition()).getCheckResCount().equals("0")) {
                        listener.onItemClick("check", "noresposne");
                    } else {
                        listener.onItemClick("check", dataSet.get(getAdapterPosition()).getRequest_id());
                        CM.setSp(context, "deatilreqtype", dataSet.get(getAdapterPosition()).getCategory_id());
                    }
                    break;
                case R.id.reqAgent:
                    //getUserId changed with getId
                    listener.onItemClick(dataSet.get(getAdapterPosition()).getId(), "showAgent");
                    break;
                case R.id.btnDetail:
                    //getUserId changed with getId
                    listener.onItemClick(dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getCategory_id());
                    break;


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
        //TextView reqAgent = holder.reqAgent;
        TextView txtComment = holder.txtComment;
        TextView startDate = holder.startDate;
        TextView endDate = holder.endDate;
        TextView total = holder.total;
        TextView adult = holder.adult;
        TextView txtRefId = holder.txtRefId;
        TextView citytxtView = holder.citytxtView;
        TextView txtDestination = holder.txtDestination;
        //  MtplTextView reqAgentType = holder.reqAgentType;
        MtplButton btnCheckRes = holder.btnCheckRes;

        TextView requestType = holder.requestType;
        ImageView catImg = holder.catImage;


        txtRefId.setText(dataSet.get(position).getReference_id());
        // reqAgent.setText(dataSet.get(position).getFirst_name() + " " + dataSet.get(position).getLast_name());
        txtComment.setText(dataSet.get(position).getUserComment());
        String txtStartDt = "";
        String txtEndDt = "";
        if (dataSet.get(position).getCategory_id().equals("1") || dataSet.get(position).getCategory_id().equals("3")) {


            try {
                txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy", dataSet.get(position).getCheck_in());
            } catch (Exception e) {
                txtStartDt = dataSet.get(position).getCheck_in();
            }

            try {
                txtEndDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy", dataSet.get(position).getCheck_out());
            } catch (Exception e) {
                txtEndDt = dataSet.get(position).getCheck_out();
            }

        } else {

            try {
                txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy", dataSet.get(position).getStart_date());
            } catch (Exception e) {
                txtStartDt = dataSet.get(position).getStart_date();
            }

            try {
                txtEndDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy", dataSet.get(position).getEnd_date());
            } catch (Exception e) {
                txtEndDt = dataSet.get(position).getEnd_date();
            }
        }


        startDate.setText(txtStartDt);
        endDate.setText(txtEndDt);
        total.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getTotal_budget());
       /* int totMemb = 0;
        try {
            totMemb = Integer.parseInt(dataSet.get(position).getAdult()) + Integer.parseInt(dataSet.get(position).getChildren());
        } catch (Exception e) {
            totMemb = 0;
        }*/
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
        txtDestination.setText(dataSet.get(position).getDestination_city().trim());

        if (dataSet.get(position).getCheckResCount().equals("0")) {
            btnCheckRes.setText("NO RESPONSE");
        } else {
            btnCheckRes.setText("CHECK RESPONSE" + " ( " + dataSet.get(position).getCheckResCount() + " )");

        }

        if (dataSet.get(position).getCategory_id().toString().equals("1")) {
            catImg.setImageResource(R.drawable.pp);
            requestType.setText("Package");
            citytxtView.setText(context.getString(R.string.destination_city));
        } else if (dataSet.get(position).getCategory_id().toString().equals("2")) {
            catImg.setImageResource(R.drawable.tt);
            requestType.setText("Transport");
            citytxtView.setText(context.getString(R.string.pickup_city));
        } else {
            catImg.setImageResource(R.drawable.hh);
            requestType.setText("Hotel");
            citytxtView.setText(context.getString(R.string.destination_city));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
