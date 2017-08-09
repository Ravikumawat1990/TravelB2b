package com.app.elixir.TravelB2B.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoTestimonial;
import com.squareup.picasso.Picasso;

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
        ImageButton mtplButton;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            comment = (MtplTextView) itemView.findViewById(R.id.txtViewComment);
            name = (MtplTextView) itemView.findViewById(R.id.txtName);
            description = (MtplTextView) itemView.findViewById(R.id.txtDescription);
            mtplButton = (ImageButton) itemView.findViewById(R.id.btnNext);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            // description.setMovementMethod(new ScrollingMovementMethod());

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
        ImageButton imageButton = holder.mtplButton;
        ImageView imageView = holder.imageView;

        if (dataSet.size() == 1) {
            imageButton.setVisibility(View.GONE);
        }
        if (position == dataSet.size() - 1) {
            imageButton.setVisibility(View.GONE);
        }

        comment.setText(dataSet.get(position).getComment());
        name.setText(dataSet.get(position).getName());
        description.setText(dataSet.get(position).getDescription());
        // description.setMovementMethod(new ScrollingMovementMethod());
        try {
            // Log.i("TAG", "onBindViewHolder: " + "http://www.travelb2bhub.com/b2b/img/user_docs/" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonArray.getJSONObject(i).optString("profile_pic"));
            Picasso.with(context)
                    .load("http://www.travelb2bhub.com/b2b/img/user_docs/" + dataSet.get(position).getAuthor_id() + "/" + dataSet.get(position).getProfile_pic())  //URLS.UPLOAD_IMG_URL + "" + dataSet.get(position).getHotel_pic()
                    .placeholder(R.drawable.logonewnew) // optional
                    .error(R.drawable.logonewnew)         // optional
                    .into(imageView);

        } catch (Exception e) {

            Log.i("TAG", "onBindViewHolder: " + e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
