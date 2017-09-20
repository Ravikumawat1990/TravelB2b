package com.app.elixir.TravelB2B.adapter;


import android.app.Activity;
import android.content.Context;
import android.media.Rating;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
        public MtplTextView titleTxt,txtsingle, txtDouble, txtCheckIn, txtCheckOut, destState, destCity, locality, txtHotelCat, txtMeal, txtTriple, txtChildWithbed,txtChildWithoutbed;
        RatingBar txtRating;


        public MyViewHolder(View itemView) {
            super(itemView);
           // rootView = (CardView) itemView.findViewById(R.id.rootView);
            titleTxt =  (MtplTextView) itemView.findViewById(R.id.dest_txt);
            txtsingle = (MtplTextView) itemView.findViewById(R.id.txtsingle);
            txtDouble = (MtplTextView) itemView.findViewById(R.id.txtDouble);
            txtTriple = (MtplTextView) itemView.findViewById(R.id.txtTriple);
            txtChildWithbed = (MtplTextView) itemView.findViewById(R.id.txtChildWithbed);
            txtCheckIn = (MtplTextView) itemView.findViewById(R.id.txtCheckIn);
            txtCheckOut = (MtplTextView) itemView.findViewById(R.id.txtCheckout);
            destState = (MtplTextView) itemView.findViewById(R.id.txtHotelstate);
            destCity = (MtplTextView) itemView.findViewById(R.id.txtDestCity);
            locality = (MtplTextView) itemView.findViewById(R.id.txtLocality);
            txtHotelCat = (MtplTextView) itemView.findViewById(R.id.txtHotelCate);
            txtHotelCat.setSelected(true);
            txtMeal = (MtplTextView) itemView.findViewById(R.id.meal);
            txtChildWithoutbed = (MtplTextView) itemView.findViewById(R.id.txtChildWithoutbed);
            txtRating = (RatingBar) itemView.findViewById(R.id.txtHoterate);


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addanotherdes_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView txtsingle = holder.txtsingle;
        TextView txtDouble = holder.txtDouble;
        TextView txtTriple = holder.txtTriple;
        TextView txtChildWithbed = holder.txtChildWithbed;
        TextView txtChildWithoutbed = holder.txtChildWithoutbed;
        TextView txtCheckIn = holder.txtCheckIn;
        TextView txtCheckOut = holder.txtCheckOut;
        TextView destState = holder.destState;
        TextView destCity = holder.destCity;
        TextView locality = holder.locality;
        TextView txtHotelCat = holder.txtHotelCat;
        TextView txtMeal = holder.txtMeal;
        RatingBar txtRating = holder.txtRating;
        TextView txtTitle = holder.titleTxt;



        int i = position + 1;
        txtTitle.setText("Destination " + i);

        if( dataSet.get(position).getSingleRoom()==null ||dataSet.get(position).getSingleRoom().equalsIgnoreCase("")  )
        {
            txtsingle.setText("-- --");
        }else {
            txtsingle.setText(dataSet.get(position).getSingleRoom());
        }
        if(dataSet.get(position).getDoubleRoom()==null  ||dataSet.get(position).getDoubleRoom().equalsIgnoreCase("") )
        {
            txtDouble.setText("-- --");
        }else {
            txtDouble.setText(dataSet.get(position).getDoubleRoom());
        }
        if( dataSet.get(position).getTripleRomm()==null  || dataSet.get(position).getTripleRomm().equalsIgnoreCase("") )
        {
            txtTriple.setText("-- --");
        }else {
            txtTriple.setText(dataSet.get(position).getTripleRomm());
        }
        if(dataSet.get(position).getChild_with_bed()==null || dataSet.get(position).getChild_with_bed().equalsIgnoreCase(""))
        {
            txtChildWithbed.setText("-- --");
        }else {
            txtChildWithbed.setText(dataSet.get(position).getChild_with_bed());
        }
        if( dataSet.get(position).getChild_without_bed()==null  || dataSet.get(position).getChild_without_bed().equalsIgnoreCase("") )
        {
            txtChildWithoutbed.setText("-- --");
        }else {
            txtChildWithoutbed.setText(dataSet.get(position).getChild_without_bed());
        }

        if(dataSet.get(position).getRating()==null || dataSet.get(position).getRating().equalsIgnoreCase("") || dataSet.get(position).getRating().toString().equalsIgnoreCase("null") )
        {
            //txtRating.setText("-- --");
        }else {

            if(dataSet.get(position).getRating().equals("null"))
            {

            }else {
                float f =Float.parseFloat(dataSet.get(position).getRating());
                txtRating.setRating(f);
            }

        }

        if(dataSet.get(position).getCheckIn()==null  || dataSet.get(position).getCheckIn().equalsIgnoreCase(""))
        {
            txtCheckIn.setText("-- --");
        }else {
            String txtStartDt = "";
            try {
                txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", dataSet.get(position).getCheckIn());
            } catch (Exception e) {
                txtStartDt = dataSet.get(position).getCheckIn();
            }
            txtCheckIn.setText(txtStartDt);
        }

        if(dataSet.get(position).getCheckOut()==null || dataSet.get(position).getCheckOut().equalsIgnoreCase("")  )
        {
            txtCheckOut.setText("-- --");
        }else {
            String txtEndDt = "";
            try {
                txtEndDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", dataSet.get(position).getCheckOut());
            } catch (Exception e) {
                txtEndDt = dataSet.get(position).getCheckOut();
            }
            txtCheckOut.setText(txtEndDt);
        }

        if(dataSet.get(position).getLocality()==null || dataSet.get(position).getLocality().equalsIgnoreCase("")  )
        {
            locality.setText("-- --");
        }else {
            locality.setText(dataSet.get(position).getLocality());
        }

        if(dataSet.get(position).getHotel_category()==null || dataSet.get(position).getHotel_category().equalsIgnoreCase("") )
        {
            txtHotelCat.setText("-- --");
        }else {
            txtHotelCat.setText(dataSet.get(position).getHotel_category());
        }
        if(dataSet.get(position).getMeal_plan()==null  || dataSet.get(position).getMeal_plan().equalsIgnoreCase("") )
        {
            txtMeal.setText("-- --");
        }else {
            txtMeal.setText(dataSet.get(position).getMeal_plan());
        }

        if (CM.isInternetAvailable(context)) {
            if(dataSet.get(position).getCity_id()==null ||  dataSet.get(position).getCity_id().equalsIgnoreCase("") || dataSet.get(position).getCity_id().equalsIgnoreCase("0"))
            {
                destCity.setText("-- --");
            }
            else {
                webCity(dataSet.get(position).getCity_id(), destCity);
            }
        }
        if (CM.isInternetAvailable(context)) {
            if(dataSet.get(position).getCity_id()==null || dataSet.get(position).getCity_id().equalsIgnoreCase("") || dataSet.get(position).getCity_id().equalsIgnoreCase("0"))
            {
                destState.setText("-- --");
            }
            else {
                webState(dataSet.get(position).getState_id(), destState);
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
