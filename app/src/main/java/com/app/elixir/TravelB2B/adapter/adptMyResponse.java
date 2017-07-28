package com.app.elixir.TravelB2B.adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.OnAdapterItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoMyResposne;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptMyResponse extends RecyclerView.Adapter<adptMyResponse.MyViewHolder> {


    private ArrayList<pojoMyResposne> dataSet;
    Context context;
    public OnAdapterItemClickListener listener;

    public adptMyResponse(Context context, ArrayList<pojoMyResposne> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView reqType, reqAgent, startDate, endDate, adult, txtComment, destination;
        MtplButton btnDetail;
        ImageView catImage;
        ImageView imageView;
        TextView total;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            reqAgent = (MtplTextView) itemView.findViewById(R.id.reqAgent);
            reqAgent.setPaintFlags(reqAgent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txtComment = (MtplTextView) itemView.findViewById(R.id.txtcomment);
            startDate = (MtplTextView) itemView.findViewById(R.id.txStartDate);
            endDate = (MtplTextView) itemView.findViewById(R.id.txEndDate);
            total = (TextView) itemView.findViewById(R.id.txtTot);
            adult = (MtplTextView) itemView.findViewById(R.id.txtAdult);
            destination = (MtplTextView) itemView.findViewById(R.id.txtDestination);
            catImage = (ImageView) itemView.findViewById(R.id.imageViewCat);
            btnDetail = (MtplButton) itemView.findViewById(R.id.btnDetail);
            //  imageView = (ImageView) itemView.findViewById(R.id.imageView);
            reqAgent.setOnClickListener(this);
            btnDetail.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnDetail:
                    listener.onItemClick("detail", dataSet.get(getAdapterPosition()).getRequest_id(), dataSet.get(getAdapterPosition()).getCategory_id());
                    break;
                case R.id.reqAgent:
                    listener.onItemClick("userdetail", dataSet.get(getAdapterPosition()).getId(), "");
                    break;
            }

        }
    }

    public void SetOnItemClickListener(OnAdapterItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adptmyresponse, parent, false);
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
        TextView destination = holder.destination;
        ImageView catImg = holder.catImage;
        //ImageView imageView = holder.imageView;

        reqAgent.setText(dataSet.get(position).getFirst_name() + " " + dataSet.get(position).getLast_name());
        txtComment.setText(dataSet.get(position).getComment());

        String txtStartDt = "";
        try {
            txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", dataSet.get(position).getCheck_in());
        } catch (Exception e) {
            txtStartDt = dataSet.get(position).getCheck_in();
        }
        String txtEndDt = "";
        try {
            txtEndDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", dataSet.get(position).getCheck_out());
        } catch (Exception e) {
            txtEndDt = dataSet.get(position).getEnd_date();
        }


        startDate.setText(txtStartDt);
        endDate.setText(txtEndDt);
        //  total.setSingleLine(false);
        total.setText("Budget\n" + dataSet.get(position).getTotal_budget() + "/-");
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

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .useFont(Typeface.DEFAULT)
                .fontSize(30) /* size in px */
                .toUpperCase()
                .endConfig()
                .buildRect(context.getString(R.string.tot), Color.RED);

//        imageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public void webCity(String cityId, final TextView destState) {
        try {
            VolleyIntialization v = new VolleyIntialization((Activity) context, false, false);
            WebService.getCityApi(v, cityId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    /*if ((Activity) context.isFinishing()) {
                        return;
                    }*/
                    MtplLog.i("WebCalls", response);
                    Log.e("TAG", response);
                    getCity(response, destState);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable((Activity) context)) {
                        CM.showPopupCommonValidation((Activity) context, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getCity(String response, TextView destState) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation((Activity) context, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (jsonObject.get("response_object").toString() == null) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());

                        destState.setText(jsonObject1.optString("name"));

                    }

                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), (Activity) context);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation((Activity) context, e.getMessage(), false);
        }
    }

    public void webState(String stateId, final TextView destCity) {
        try {
            VolleyIntialization v = new VolleyIntialization((Activity) context, false, false);
            WebService.getStateApi(v, stateId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    /*if ((Activity) context.isFinishing()) {
                        return;
                    }*/
                    MtplLog.i("WebCalls", response);
                    Log.e("TAG", response);
                    getState(response, destCity);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable((Activity) context)) {
                        CM.showPopupCommonValidation((Activity) context, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getState(String response, TextView destCity) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation((Activity) context, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":

                    if (jsonObject.optString("response_object") != null) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());


                        destCity.setText(jsonObject1.optString("state_name"));


                    }


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), (Activity) context);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation((Activity) context, e.getMessage(), false);
        }
    }

}
