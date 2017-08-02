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
import com.app.elixir.TravelB2B.pojos.pojoFollowers;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptfollowers extends RecyclerView.Adapter<adptfollowers.MyViewHolder> {


    private ArrayList<pojoFollowers> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptfollowers(Context context, ArrayList<pojoFollowers> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView txtName, txtEmail, txtMobile, txtCompName;
        MtplButton btnUnBlockUser;
        MtplButton btnUnFollow;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtName = (MtplTextView) itemView.findViewById(R.id.txtName);
            txtEmail = (MtplTextView) itemView.findViewById(R.id.txtEmail);
            txtMobile = (MtplTextView) itemView.findViewById(R.id.txtMobile);
            txtMobile.setSelected(true);
            txtCompName = (MtplTextView) itemView.findViewById(R.id.txtCompName);
            btnUnFollow = (MtplButton) itemView.findViewById(R.id.btnunfollow);
            btnUnFollow.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnunfollow:
                    listener.onItemClick("follow", dataSet.get(getAdapterPosition()).getBb_userid());
                    break;
            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptfollower, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView fname = holder.txtName;
        TextView email = holder.txtEmail;
        TextView mobileNo = holder.txtMobile;
        TextView compName = holder.txtCompName;

        compName.setText(dataSet.get(position).getCompany_name());
        fname.setText(dataSet.get(position).getFirst_name() + " " + dataSet.get(position).getLast_name());
        email.setText(dataSet.get(position).getEmail());
        mobileNo.setText(dataSet.get(position).getWeb_url());


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
