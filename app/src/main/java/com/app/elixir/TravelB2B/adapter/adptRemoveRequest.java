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
import com.app.elixir.TravelB2B.pojos.pojoRemoveReq;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptRemoveRequest extends RecyclerView.Adapter<adptRemoveRequest.MyViewHolder> {


    private ArrayList<pojoRemoveReq> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptRemoveRequest(Context context, ArrayList<pojoRemoveReq> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView txtRefid, txttotBudget, txtRexType, txtMebers;
        MtplButton btnDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtRefid = (MtplTextView) itemView.findViewById(R.id.txtRefid);
            txttotBudget = (MtplTextView) itemView.findViewById(R.id.txttotBudget);
            txtRexType = (MtplTextView) itemView.findViewById(R.id.txtRexType);
            txtMebers = (MtplTextView) itemView.findViewById(R.id.txtMebers);
            btnDetail = (MtplButton) itemView.findViewById(R.id.btnDetail);
            btnDetail.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnDetail:
                    listener.onItemClick("detail", dataSet.get(getAdapterPosition()).getRequest_id());
                    break;
            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
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
        TextView textViewReqId = holder.txtRefid;
        TextView mebers = holder.txtMebers;
        TextView total = holder.txttotBudget;


        textViewReqId.setText(dataSet.get(position).getReference_id());

        int mebersInt = 0;
        try {
            mebersInt = Integer.parseInt(dataSet.get(position).getAdult()) + Integer.parseInt(dataSet.get(position).getChildren());
        } catch (Exception e) {
            mebersInt = 0;

        }

        mebers.setText(String.valueOf(mebersInt));


        total.setText(context.getString(R.string.rsSymbol) + " " + dataSet.get(position).getTotal_budget());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
