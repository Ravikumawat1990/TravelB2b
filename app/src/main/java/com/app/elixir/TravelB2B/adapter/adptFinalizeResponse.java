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
import com.app.elixir.TravelB2B.pojos.pojoFinalizeResposne;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptFinalizeResponse extends RecyclerView.Adapter<adptFinalizeResponse.MyViewHolder> {


    private ArrayList<pojoFinalizeResposne> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptFinalizeResponse(Context context, ArrayList<pojoFinalizeResposne> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView txtRefId, txtTot, txtQuotPrice, txtCmt, txtAgentName;
        MtplButton btnDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtAgentName = (MtplTextView) itemView.findViewById(R.id.txtAgentName);
            txtRefId = (MtplTextView) itemView.findViewById(R.id.txtRefId);
            txtTot = (MtplTextView) itemView.findViewById(R.id.txtTot);
            txtQuotPrice = (MtplTextView) itemView.findViewById(R.id.txtQuotPrice);
            txtCmt = (MtplTextView) itemView.findViewById(R.id.txtCmt);
            btnDetail = (MtplButton) itemView.findViewById(R.id.btnDetail);
            btnDetail.setOnClickListener(this);
            txtAgentName.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnDetail:
                    listener.onItemClick("detail", dataSet.get(getAdapterPosition()).getRequest_id());
                    break;
                case R.id.txtAgentName:
                    listener.onItemClick("agent", dataSet.get(getAdapterPosition()).getId());
                    break;

            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptfinalizeresponse, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        TextView agentName = holder.txtAgentName;

        TextView comment = holder.txtCmt;
        TextView quotPrice = holder.txtQuotPrice;
        TextView total = holder.txtTot;
        TextView refId = holder.txtRefId;


        agentName.setText(dataSet.get(position).getFirst_name() + " " + dataSet.get(position).getLast_name());
        comment.setText(dataSet.get(position).getComment());
        quotPrice.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getQuotation_price());
        total.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getTotal_budget());
        refId.setText(dataSet.get(position).getReference_id());


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
