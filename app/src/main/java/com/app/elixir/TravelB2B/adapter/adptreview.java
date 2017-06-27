package com.app.elixir.TravelB2B.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoTestimonial;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptreview extends RecyclerView.Adapter<adptreview.MyViewHolder> {


    private ArrayList<pojoTestimonial> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptreview(Context context, ArrayList<pojoTestimonial> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        MtplTextView comment, name, description;

        public MyViewHolder(View itemView) {
            super(itemView);
            comment = (MtplTextView) itemView.findViewById(R.id.txtViewComment);
            name = (MtplTextView) itemView.findViewById(R.id.txtName);
            description = (MtplTextView) itemView.findViewById(R.id.txtDescription);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptreview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MtplTextView comment = holder.comment;
        MtplTextView name = holder.name;
        MtplTextView description = holder.description;

        comment.setText(dataSet.get(position).getComment());
        name.setText(dataSet.get(position).getName());
        description.setText(dataSet.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
