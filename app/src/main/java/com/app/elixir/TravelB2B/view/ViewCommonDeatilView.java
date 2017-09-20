package com.app.elixir.TravelB2B.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptAddAnotherDes;
import com.app.elixir.TravelB2B.adapter.adptStop;
import com.app.elixir.TravelB2B.model.pojoAddAnother;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoStops;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by User on 12-Sep-2017.
 */

public class ViewCommonDeatilView extends AppCompatActivity {
    private static final String TAG = "Detailcreen";

    Toolbar toolbar;
    String requestId = "";
    String reqId = "";
    String reqType = "";

    MtplTextView reqTyprTxt, refIdTxt, totalBudgetTxt, adultTxt, childrenTxt, commentTxt, tranportTxt, startDateTxt, endDateTxt, pickupLocTxt, pickupCityTxt, pickupStateTxt, finalLocTxt, finalCityTxt, finalStateTxt;
    LinearLayout stayReqTitleTxt, tranReqTilteTxt, commenttilteTxt;

    CardView transCardView, commentCard;
    LinearLayout destiantionCardView, commentView;

    RecyclerView destRecycleView, transRecycleView;

    ArrayList<pojoAddAnother> pojoAddAnotherArrayList;
    ArrayList<pojoStops> pojoStopses;

    adptAddAnotherDes mAdapter;
    adptStop adptStops;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        requestId = intent.getStringExtra("refId");
        String title = intent.getStringExtra("title");
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CM.finishActivity(ViewCommonDeatilView.this);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        TextView titleTextView = null;
        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_roboto_black));
            titleTextView.setTypeface(font);
            titleTextView.setTextSize(18);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        reqId = intent.getStringExtra("reqtype");
        reqType = CM.getReqType(reqId);

        initView();

    }

    private void initView() {

        reqTyprTxt = (MtplTextView) findViewById(R.id.reqtypetxt);
        reqTyprTxt.setText(reqType + " Details");

        refIdTxt = (MtplTextView) findViewById(R.id.txtRefId);
        totalBudgetTxt = (MtplTextView) findViewById(R.id.txttotBudget);
        adultTxt = (MtplTextView) findViewById(R.id.txtadult);
        childrenTxt = (MtplTextView) findViewById(R.id.txtchilds);
        commentTxt = (MtplTextView) findViewById(R.id.comment_txt);
        tranportTxt = (MtplTextView) findViewById(R.id.txttransprot);
        startDateTxt = (MtplTextView) findViewById(R.id.txtsdate);
        endDateTxt = (MtplTextView) findViewById(R.id.txtedate);
        pickupLocTxt = (MtplTextView) findViewById(R.id.txtplocalicy);
        pickupCityTxt = (MtplTextView) findViewById(R.id.txtpcity);
        pickupStateTxt = (MtplTextView) findViewById(R.id.txtpstate);
        finalLocTxt = (MtplTextView) findViewById(R.id.txtdLocality);
        finalCityTxt = (MtplTextView) findViewById(R.id.txtDestCity);
        finalStateTxt = (MtplTextView) findViewById(R.id.textdstate);

        stayReqTitleTxt = (LinearLayout) findViewById(R.id.stay_req_root);
        tranReqTilteTxt = (LinearLayout) findViewById(R.id.trans_req_root);
        commenttilteTxt = (LinearLayout) findViewById(R.id.comment_root);

        destiantionCardView = (LinearLayout) findViewById(R.id.cardstayreq);
        commentView = (LinearLayout) findViewById(R.id.comment_root);
        transCardView = (CardView) findViewById(R.id.cardtarnsreq);
        commentCard = (CardView) findViewById(R.id.cardcomment);


        destRecycleView = (RecyclerView) findViewById(R.id.stay_recycleView);
        transRecycleView = (RecyclerView) findViewById(R.id.trans_recycleView);
        destRecycleView.setLayoutManager(new LinearLayoutManager(ViewCommonDeatilView.this));
        transRecycleView.setLayoutManager(new LinearLayoutManager(ViewCommonDeatilView.this));

        pojoAddAnotherArrayList = new ArrayList<>();
        pojoStopses = new ArrayList<>();

        mAdapter = new adptAddAnotherDes(ViewCommonDeatilView.this, pojoAddAnotherArrayList);
        adptStops = new adptStop(ViewCommonDeatilView.this, pojoStopses);

        if (reqId.equals("2")) {
            destiantionCardView.setVisibility(View.GONE);
            transCardView.setVisibility(View.VISIBLE);
            stayReqTitleTxt.setVisibility(View.GONE);
            tranReqTilteTxt.setVisibility(View.VISIBLE);
        } else if (reqId.equals("3")) {
            destiantionCardView.setVisibility(View.VISIBLE);
            transCardView.setVisibility(View.GONE);
            stayReqTitleTxt.setVisibility(View.VISIBLE);
            tranReqTilteTxt.setVisibility(View.GONE);
        } else {
            destiantionCardView.setVisibility(View.VISIBLE);
            transCardView.setVisibility(View.VISIBLE);
            stayReqTitleTxt.setVisibility(View.VISIBLE);
            tranReqTilteTxt.setVisibility(View.VISIBLE);
        }


        if (CM.isInternetAvailable(ViewCommonDeatilView.this)) {

            webFinalizeResponseDeatil(CM.getSp(ViewCommonDeatilView.this, CV.PrefID, "").toString(), requestId);

        } else {

            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCommonDeatilView.this);
        }

    }

    public void webFinalizeResponseDeatil(String userId, String reqId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCommonDeatilView.this, true, true);
            WebService.getDetail(v, userId, reqId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getFinalizeResponse(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCommonDeatilView.this)) {
                        CM.showPopupCommonValidation(ViewCommonDeatilView.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getFinalizeResponse(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCommonDeatilView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());

                    //Gen Req
                    refIdTxt.setText(jsonObject1.optString("reference_id"));
                    totalBudgetTxt.setText(getString(R.string.rsSymbol) + " " + jsonObject1.optString("total_budget"));
                    adultTxt.setText(jsonObject1.optString("adult"));
                    childrenTxt.setText(jsonObject1.optString("children"));

                    //Hotel
                    JSONArray jsonArray = new JSONArray(jsonObject1.optString("hotels").toString());
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            pojoAddAnother addAnother = new pojoAddAnother();
                            addAnother.setSingleRoom(jsonArray.getJSONObject(i).optString("room1"));
                            addAnother.setDoubleRoom(jsonArray.getJSONObject(i).optString("room2"));
                            addAnother.setTripleRomm(jsonArray.getJSONObject(i).optString("room3"));
                            addAnother.setChild_with_bed(jsonArray.getJSONObject(i).optString("child_with_bed"));
                            addAnother.setChild_without_bed(jsonArray.getJSONObject(i).optString("child_without_bed"));
                            addAnother.setCheckIn(jsonArray.getJSONObject(i).optString("check_in"));
                            addAnother.setCheckOut(jsonArray.getJSONObject(i).optString("check_out"));
                            addAnother.setCity_id(jsonArray.getJSONObject(i).optString("city_id"));
                            addAnother.setState_id(jsonArray.getJSONObject(i).optString("state_id"));
                            addAnother.setLocality(jsonArray.getJSONObject(i).optString("locality"));
                            String hotel_categorys11 = "";
                            String[] items2 = jsonArray.getJSONObject(i).optString("hotel_category").split(",");
                            for (String items3 : items2) {
                                if (hotel_categorys11.equals("")) {
                                    hotel_categorys11 = CM.setHotelCat(items3);
                                } else {
                                    hotel_categorys11 = hotel_categorys11 + "," + CM.setHotelCat(items3);
                                }
                            }

                            addAnother.setHotel_category(hotel_categorys11);
                            addAnother.setMeal_plan(CM.getMealPlane(jsonArray.getJSONObject(i).optString("meal_plan")));
                            addAnother.setRating(jsonArray.getJSONObject(i).optString("hotel_rating"));
                            pojoAddAnotherArrayList.add(addAnother);
                        }
                    } else {
                        pojoAddAnother addAnother = new pojoAddAnother();
                        addAnother.setSingleRoom(jsonObject1.optString("room1"));
                        addAnother.setDoubleRoom(jsonObject1.optString("room2"));
                        addAnother.setTripleRomm(jsonObject1.optString("room3"));
                        addAnother.setChild_with_bed(jsonObject1.optString("child_with_bed"));
                        addAnother.setChild_without_bed(jsonObject1.optString("child_without_bed"));
                        addAnother.setCheckIn(jsonObject1.optString("check_in"));
                        addAnother.setCheckOut(jsonObject1.optString("check_out"));
                        addAnother.setCity_id(jsonObject1.optString("city_id"));
                        addAnother.setState_id(jsonObject1.optString("state_id"));
                        addAnother.setLocality(jsonObject1.optString("locality"));
                        String hotel_categorys1 = "";
                        String[] items1 = jsonObject1.optString("hotel_category").split(",");
                        for (String item : items1) {
                            if (hotel_categorys1.equals("")) {
                                hotel_categorys1 = CM.setHotelCat(item);
                            } else {
                                hotel_categorys1 = hotel_categorys1 + "," + CM.setHotelCat(item);
                            }
                        }

                        addAnother.setHotel_category(hotel_categorys1);
                        addAnother.setMeal_plan(CM.getMealPlane(jsonObject1.optString("meal_plan")));
                        addAnother.setRating(jsonObject1.optString("hotel_rating"));
                        pojoAddAnotherArrayList.add(addAnother);
                    }

                    //Tarnsport
                    if (jsonObject1.optString("transport_requirement") == null || jsonObject1.optString("transport_requirement").equalsIgnoreCase("")) {
                        tranportTxt.setText("-- --");
                    } else {
                        tranportTxt.setText(CM.setVichel(jsonObject1.optString("transport_requirement")));
                    }
                    if (jsonObject1.optString("start_date") == null || jsonObject1.optString("start_date").equalsIgnoreCase("")) {
                        startDateTxt.setText("-- --");
                    } else {
                        startDateTxt.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("start_date")));
                    }
                    if (jsonObject1.optString("end_date") == null || jsonObject1.optString("end_date").equalsIgnoreCase("")) {
                        endDateTxt.setText("-- --");
                    } else {
                        endDateTxt.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("end_date")));
                    }

                    if (jsonObject1.optString("pickup_locality") == null || jsonObject1.optString("pickup_locality").equalsIgnoreCase("")) {
                        pickupLocTxt.setText("-- --");
                    } else {
                        pickupLocTxt.setText(jsonObject1.optString("pickup_locality"));
                    }

                    if (!jsonObject1.optString("pickup_state").equals("") && !jsonObject1.optString("pickup_state").equals("0") && !jsonObject1.optString("pickup_state").equals("null")) {

                        if (CM.isInternetAvailable(ViewCommonDeatilView.this)) {
                            webState(jsonObject1.optString("pickup_state"), "2");
                        } else {
                            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCommonDeatilView.this);
                        }

                    } else {
                        pickupStateTxt.setText("-- --");
                    }
                    if (!jsonObject1.optString("pickup_city").equals("") && !jsonObject1.optString("pickup_city").equals("0") && !jsonObject1.optString("pickup_city").equals("null")) {
                        if (CM.isInternetAvailable(ViewCommonDeatilView.this)) {
                            webCity(jsonObject1.optString("pickup_city"), "2");
                        } else {
                            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCommonDeatilView.this);
                        }
                    } else {
                        pickupCityTxt.setText("-- --");
                    }

                    if (jsonObject1.optString("final_locality") == null || jsonObject1.optString("final_locality").equalsIgnoreCase("")) {
                        finalLocTxt.setText("-- --");
                    } else {
                        finalLocTxt.setText(jsonObject1.optString("final_locality"));
                    }

                    if (!jsonObject1.optString("final_state").equals("") && !jsonObject1.optString("final_state").equals("0") && !jsonObject1.optString("final_state").equals("null")) {

                        if (CM.isInternetAvailable(ViewCommonDeatilView.this)) {
                            webState(jsonObject1.optString("final_state"), "3");
                        } else {
                            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCommonDeatilView.this);
                        }

                    } else {
                        finalStateTxt.setText("-- --");
                    }
                    if (!jsonObject1.optString("final_city").equals("") && !jsonObject1.optString("final_city").equals("0") && !jsonObject1.optString("final_city").equals("null")) {
                        if (CM.isInternetAvailable(ViewCommonDeatilView.this)) {
                            webCity(jsonObject1.optString("final_city"), "3");
                        } else {
                            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCommonDeatilView.this);
                        }
                    } else {
                        finalCityTxt.setText("-- --");
                    }

                    JSONArray stops = new JSONArray(jsonObject1.optString("request_stops").toString());
                    if (stops.length() > 0) {
                        transRecycleView.setVisibility(View.VISIBLE);
                        for (int i = 0; i < stops.length(); i++) {
                            pojoStops pojo = new pojoStops();
                            if (stops.getJSONObject(i).optString("city_id") != null && !stops.getJSONObject(i).optString("city_id").toString().equals("null") && !stops.getJSONObject(i).optString("city_id").toString().equals("")) {
                                pojo.setCity_id(stops.getJSONObject(i).optString("city_id"));
                            } else {
                                pojo.setCity_id("");
                            }

                            pojo.setId(stops.getJSONObject(i).optString("id"));
                            pojo.setLocality(stops.getJSONObject(i).optString("locality"));

                            if (stops.getJSONObject(i).optString("state_id") != null && !stops.getJSONObject(i).optString("state_id").toString().equals("null") && !stops.getJSONObject(i).optString("state_id").toString().equals("")) {
                                pojo.setState_id(stops.getJSONObject(i).optString("state_id"));
                            } else {
                                pojo.setState_id("");
                            }

                            pojo.setRequest_id(stops.getJSONObject(i).optString("request_id"));
                            pojoStopses.add(pojo);

                        }
                    } else {
                        transRecycleView.setVisibility(View.GONE);
                    }

                    //Comment
                    if (jsonObject1.optString("comment") == null || jsonObject1.optString("comment").equalsIgnoreCase("")) {
                        commentView.setVisibility(View.GONE);
                        commentCard.setVisibility(View.GONE);
                    } else {
                        commentTxt.setText(jsonObject1.optString("comment"));
                        commentView.setVisibility(View.VISIBLE);
                        commentCard.setVisibility(View.VISIBLE);
                    }


                    //transport Adapter
                    transRecycleView.setAdapter(adptStops);
                    transRecycleView.invalidate();
                    //hotel Adapter
                    destRecycleView.setAdapter(mAdapter);
                    destRecycleView.invalidate();

                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewCommonDeatilView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCommonDeatilView.this, e.getMessage(), false);
        }
    }

    public void webCity(String cityId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCommonDeatilView.this, true, true);
            WebService.getCityApi(v, cityId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getCity(response, type);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCommonDeatilView.this)) {
                        CM.showPopupCommonValidation(ViewCommonDeatilView.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getCity(String response, String type) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCommonDeatilView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (jsonObject.get("response_object").toString() != null) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());
                        if (type.equals("2")) {
                            pickupCityTxt.setText(jsonObject1.optString("name"));
                        } else {
                            finalCityTxt.setText(jsonObject1.optString("name"));
                        }
                    }

                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewCommonDeatilView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCommonDeatilView.this, e.getMessage(), false);
        }
    }

    public void webState(String stateId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCommonDeatilView.this, true, true);
            WebService.getStateApi(v, stateId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getState(response, type);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCommonDeatilView.this)) {
                        CM.showPopupCommonValidation(ViewCommonDeatilView.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getState(String response, String type) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCommonDeatilView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":

                    if (jsonObject.optString("response_object") != null) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());
                        if (type.equals("2")) {
                            pickupStateTxt.setText(jsonObject1.optString("state_name"));
                        } else {
                            finalStateTxt.setText(jsonObject1.optString("state_name"));
                        }
                    }


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewCommonDeatilView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCommonDeatilView.this, e.getMessage(), false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(this);
    }
}
