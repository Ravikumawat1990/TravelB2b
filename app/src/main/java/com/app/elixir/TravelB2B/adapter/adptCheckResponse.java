package com.app.elixir.TravelB2B.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListeners;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoCheckResposne;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptCheckResponse extends RecyclerView.Adapter<adptCheckResponse.MyViewHolder> {


    private ArrayList<pojoCheckResposne> dataSet;
    Context context;
    public OnItemClickListeners listener;

    public adptCheckResponse(Context context, ArrayList<pojoCheckResposne> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView reqfID, txtUserName, startDate, txtTotalBudget, txtComment, txtQuotPrice;
        MtplButton btnShare, btnChat, btnAccept, btnBlock, btnRate;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtUserName = (MtplTextView) itemView.findViewById(R.id.txtUserName);
            reqfID = (MtplTextView) itemView.findViewById(R.id.txtRefId);

            txtTotalBudget = (MtplTextView) itemView.findViewById(R.id.txtTotalBudget);
            txtComment = (MtplTextView) itemView.findViewById(R.id.txtComment);
            txtQuotPrice = (MtplTextView) itemView.findViewById(R.id.txtQuotPrice);

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
                    listener.onItemClick("chat", dataSet.get(getAdapterPosition()).getUser_id(), "", "");
                    break;
                case R.id.btnShare:
                    listener.onItemClick("share", dataSet.get(getAdapterPosition()).getUser_id(), dataSet.get(getAdapterPosition()).getId(), "");
                    break;
                case R.id.btnAccept:
                    listener.onItemClick("btnAccept", dataSet.get(getAdapterPosition()).getId(), dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getUser_id());
                    break;
                case R.id.btnRate:
                    listener.onItemClick("rate", dataSet.get(getAdapterPosition()).getUser_id(), dataSet.get(getAdapterPosition()).getId(), "");
                    break;
                case R.id.btnBlock:
                    listener.onItemClick("block", dataSet.get(getAdapterPosition()).getUser_id(), "", "");
                    break;
            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListeners mItemClickListener) {

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
        TextView refId = holder.reqfID;
        TextView userName = holder.txtUserName;
        TextView totalBudget = holder.txtTotalBudget;
        TextView comment = holder.txtComment;
        TextView quotPrice = holder.txtQuotPrice;

        refId.setText(dataSet.get(position).getReference_id());
        userName.setText(dataSet.get(position).getFirst_name() + " " + dataSet.get(position).getLast_name());

        totalBudget.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getTotal_budget());
        comment.setText(dataSet.get(position).getComment());
        quotPrice.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getQuotation_price());


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
