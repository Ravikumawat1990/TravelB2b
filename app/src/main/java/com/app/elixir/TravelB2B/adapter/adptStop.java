package com.app.elixir.TravelB2B.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoStops;
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
public class adptStop extends RecyclerView.Adapter<adptStop.MyViewHolder> {


    private ArrayList<pojoStops> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptStop(Context context, ArrayList<pojoStops> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       // private CardView rootView;
        public MtplTextView txtstopCity, txtstopState, txtstopLocality,stopTitle;


        public MyViewHolder(View itemView) {
            super(itemView);
          //  rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtstopCity = (MtplTextView) itemView.findViewById(R.id.txtscity);
            txtstopState = (MtplTextView) itemView.findViewById(R.id.txtsstate);
            txtstopLocality = (MtplTextView) itemView.findViewById(R.id.txtslocality);
            stopTitle = (MtplTextView) itemView.findViewById(R.id.Stop_txt);
            //txtCmt = (MtplTextView) itemView.findViewById(R.id.txtCmt);

        }

        @Override
        public void onClick(View view) {

        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addstop_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView txtstopCity = holder.txtstopCity;
        TextView txtstopState = holder.txtstopState;
        TextView txtstopLocality = holder.txtstopLocality;
        TextView txtStopTitle = holder.stopTitle;
        //TextView txtCmt = holder.txtCmt;

        int i =position+1;
        txtStopTitle.setText("Stop "+i);

        if (dataSet.get(position).getLocality()==null || dataSet.get(position).getLocality().equals("") || dataSet.get(position).getLocality().equals("")) {
            txtstopLocality.setText("-- --");
        }
        else
        {
            txtstopLocality.setText(dataSet.get(position).getLocality());
        }

        if (CM.isInternetAvailable(context)) {
            if (dataSet.get(position).getCity_id()==null || dataSet.get(position).getCity_id().equals("") || dataSet.get(position).getCity_id().equals("")) {
               txtstopCity.setText("-- --");
            }
            else
            {
                webCity(dataSet.get(position).getCity_id(), txtstopCity);
            }

        }
        if (CM.isInternetAvailable(context)) {
            if (dataSet.get(position).getState_id()==null || dataSet.get(position).getState_id().equals("") || dataSet.get(position).getState_id().equals("")) {
                txtstopState.setText("-- --");
            }
            else
            {
                webState(dataSet.get(position).getState_id(), txtstopState);
            }
        }
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
                    if (jsonObject.get("response_object").toString() != null) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());

                        try {
                            destState.setText(jsonObject1.optString("name"));

                        } catch (Exception e) {
                            //destState.setText("");
                        }

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


                        try {
                            destCity.setText(jsonObject1.optString("state_name"));

                        } catch (Exception e) {

                        }


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
