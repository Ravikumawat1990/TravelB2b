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
import com.app.elixir.TravelB2B.model.pojoAddAnother;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
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
public class adptAddAnotherDes extends RecyclerView.Adapter<adptAddAnotherDes.MyViewHolder> {


    private ArrayList<pojoAddAnother> dataSet;
    Context context;
    public OnItemClickListener listener;

    public adptAddAnotherDes(Context context, ArrayList<pojoAddAnother> data) {
        this.dataSet = data;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView rootView;
        public MtplTextView txtsingle, txtDouble, txtCheckIn, txtCheckOut, destState, destCity, locality, txtHotelCat, txtMeal, txtTriple, txtChildWithbed;
        MtplButton btnUnBlockUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootView = (CardView) itemView.findViewById(R.id.rootView);
            txtsingle = (MtplTextView) itemView.findViewById(R.id.txtsingle);
            txtDouble = (MtplTextView) itemView.findViewById(R.id.txtDouble);
            txtTriple = (MtplTextView) itemView.findViewById(R.id.txtTriple);
            txtChildWithbed = (MtplTextView) itemView.findViewById(R.id.txtChildWithbed);
            txtCheckIn = (MtplTextView) itemView.findViewById(R.id.txtCheckIn);
            txtCheckOut = (MtplTextView) itemView.findViewById(R.id.txtCheckOut);
            destState = (MtplTextView) itemView.findViewById(R.id.destState);
            destCity = (MtplTextView) itemView.findViewById(R.id.destCity);
            locality = (MtplTextView) itemView.findViewById(R.id.locality);
            txtHotelCat = (MtplTextView) itemView.findViewById(R.id.txtHotelCat);
            txtHotelCat.setSelected(true);
            txtMeal = (MtplTextView) itemView.findViewById(R.id.txtMeal);

            txtMeal.setSelected(true);
            //  btnUnBlockUser.setOnClickListener(this);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addanotherdest, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView txtsingle = holder.txtsingle;
        TextView txtDouble = holder.txtDouble;
        TextView txtTriple = holder.txtTriple;
        TextView txtChildWithbed = holder.txtChildWithbed;
        TextView txtCheckIn = holder.txtCheckIn;
        TextView txtCheckOut = holder.txtCheckOut;
        TextView destState = holder.destState;
        TextView destCity = holder.destCity;
        TextView locality = holder.locality;
        TextView txtHotelCat = holder.txtHotelCat;
        TextView txtMeal = holder.txtMeal;

        txtsingle.setText(dataSet.get(position).getSingleRoom());
        txtDouble.setText(dataSet.get(position).getDoubleRoom());
        txtTriple.setText(dataSet.get(position).getTripleRomm());
        txtChildWithbed.setText(dataSet.get(position).getChild_with_bed());


        String txtStartDt = "";
        try {
            txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", dataSet.get(position).getCheckIn());
        } catch (Exception e) {
            txtStartDt = dataSet.get(position).getCheckIn();
        }
        String txtEndDt = "";
        try {
            txtEndDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", dataSet.get(position).getCheckOut());
        } catch (Exception e) {
            txtEndDt = dataSet.get(position).getCheckOut();
        }


        txtCheckIn.setText(txtStartDt);
        txtCheckOut.setText(txtEndDt);
        locality.setText(dataSet.get(position).getLocality());
        txtHotelCat.setText(dataSet.get(position).getHotel_category());
        txtMeal.setText(CM.getMealPlane(dataSet.get(position).getMeal_plan()));

        if (CM.isInternetAvailable(context)) {
            webCity(dataSet.get(position).getCity_id(), destCity);
        }
        if (CM.isInternetAvailable(context)) {
            webState(dataSet.get(position).getState_id(), destState);
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
