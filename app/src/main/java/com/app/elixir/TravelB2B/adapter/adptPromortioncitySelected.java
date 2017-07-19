package com.app.elixir.TravelB2B.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.pojos.pojoPromotionCitys;

import java.util.ArrayList;

/**
 * Created by User on 18-Jul-2017.
 */

public class adptPromortioncitySelected extends RecyclerView.Adapter<adptPromortioncitySelected.MyViewHolder> {

    private ArrayList<pojoPromotionCitys> selectedcity;
    public OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txtCity);
            layout = (LinearLayout) view.findViewById(R.id.root1);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

           /* switch (view.getId()) {
                case R.id.root1:
                    listener.onItemClick("", "" + selectedcity.get(getAdapterPosition()));
                    break;
            }*/
            if (view.getId() == R.id.root1) {
                listener.onItemClick("",""+getAdapterPosition());
            }

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }

    public adptPromortioncitySelected(ArrayList<pojoPromotionCitys> countries) {
        this.selectedcity = countries;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promcitylistitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        pojoPromotionCitys pojo = selectedcity.get(position);
        holder.title.setText(pojo.getLabel() + " (" + pojo.getUsercount() + ")");
    }

    @Override
    public int getItemCount() {
        return selectedcity.size();
    }


}

