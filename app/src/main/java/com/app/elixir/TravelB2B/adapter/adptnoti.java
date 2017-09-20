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
import com.app.elixir.TravelB2B.pojos.pojoBlockUser;
import com.app.elixir.TravelB2B.pojos.pojoNoti;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptnoti extends RecyclerView.Adapter<adptnoti.MyViewHolder> {


    private ArrayList<pojoNoti> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptnoti(Context context, ArrayList<pojoNoti> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView txtUserName, txtMessage, txtReqId;
        MtplButton btnUnBlockUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtUserName = (MtplTextView) itemView.findViewById(R.id.txt_senderName);
            txtMessage = (MtplTextView) itemView.findViewById(R.id.txt_message);
            txtReqId = (MtplTextView) itemView.findViewById(R.id.txt_questid);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnUnBlockUser:
                    listener.onItemClick(dataSet.get(getAdapterPosition()).getSend_to_user_id(), "block");
                    break;
            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptnoti, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView userName = holder.txtUserName;
        TextView reqId = holder.txtReqId;
        TextView message = holder.txtMessage;
        userName.setText(dataSet.get(position).getSender_name());
        reqId.setText(dataSet.get(position).getRequest_id());
        message.setText(dataSet.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
