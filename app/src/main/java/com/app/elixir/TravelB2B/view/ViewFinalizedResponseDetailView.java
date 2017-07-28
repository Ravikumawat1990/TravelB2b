package com.app.elixir.TravelB2B.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptAddAnotherDes;
import com.app.elixir.TravelB2B.model.pojoAddAnother;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
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

public class ViewFinalizedResponseDetailView extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewFinalizedResponse";
    private MtplButton btnBlockUser;
    private MtplButton btnRateUser;
    Toolbar toolbar;
    String requestId = "";
    MtplTextView refId, budget, members, childres, singlePer, doublePer, triplePer, child_with_bed, child_without_bed, checkIn, checkout, destiState,
            destiCity, locality, hotelCat, meal, comment;
    ArrayList<pojoAddAnother> pojoAddAnotherArrayList;
    adptAddAnotherDes mAdapter;
    RecyclerView mRecyclerView;
    MtplTextView trasnStrtDate, trasnEndDate, transPickupState, transPickupCity, transPickupLocat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_finalized_response_detail_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.finalized_response));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewFinalizedResponseDetailView.this);

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
        Intent intent = getIntent();
        requestId = intent.getStringExtra("refId");

        initView();

    }

    private void initView() {

        btnBlockUser = (MtplButton) findViewById(R.id.btnBlockUser);
        btnRateUser = (MtplButton) findViewById(R.id.btnRateUser);
        btnBlockUser.setOnClickListener(this);
        btnRateUser.setOnClickListener(this);

        refId = (MtplTextView) findViewById(R.id.txtRefId);
        budget = (MtplTextView) findViewById(R.id.txttotBudget);
        members = (MtplTextView) findViewById(R.id.txtMemebers);
        childres = (MtplTextView) findViewById(R.id.txtChildren);
        singlePer = (MtplTextView) findViewById(R.id.txtSingle);
        doublePer = (MtplTextView) findViewById(R.id.txtDouble);
        triplePer = (MtplTextView) findViewById(R.id.txtTriple);
        child_with_bed = (MtplTextView) findViewById(R.id.txtChildWithbed);
        child_without_bed = (MtplTextView) findViewById(R.id.txtChildWithoutbed);
        checkIn = (MtplTextView) findViewById(R.id.txtCheckIn);
        checkout = (MtplTextView) findViewById(R.id.txtCheckout);
        destiState = (MtplTextView) findViewById(R.id.txtDestState);
        destiCity = (MtplTextView) findViewById(R.id.txtDestCity);
        locality = (MtplTextView) findViewById(R.id.txtLocality);
        hotelCat = (MtplTextView) findViewById(R.id.txtHotelCat);
        meal = (MtplTextView) findViewById(R.id.txtMeal);
        meal.setSelected(true);
        comment = (MtplTextView) findViewById(R.id.txtCmt);


        trasnStrtDate = (MtplTextView) findViewById(R.id.transStatrDate);
        trasnEndDate = (MtplTextView) findViewById(R.id.transEndDate);
        transPickupState = (MtplTextView) findViewById(R.id.pickupState);
        transPickupCity = (MtplTextView) findViewById(R.id.pickupCity);
        transPickupLocat = (MtplTextView) findViewById(R.id.trapickupLocat);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ViewFinalizedResponseDetailView.this));
        pojoAddAnotherArrayList = new ArrayList<>();

        mAdapter = new adptAddAnotherDes(ViewFinalizedResponseDetailView.this, pojoAddAnotherArrayList);

        if (CM.isInternetAvailable(ViewFinalizedResponseDetailView.this)) {

            webFinalizeResponseDeatil(CM.getSp(ViewFinalizedResponseDetailView.this, CV.PrefID, "").toString(), requestId);

        } else {

            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewFinalizedResponseDetailView.this);
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewFinalizedResponseDetailView.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CM.finishActivity(ViewFinalizedResponseDetailView.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ShowRatingDialog() {

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final RatingBar rating = new RatingBar(this);
        rating.setMax(6);
        popDialog.setIcon(R.drawable.logo3);
        popDialog.setTitle("Rate This!! ");
        popDialog.setView(rating);
        popDialog.setPositiveButton(android.R.string.ok,

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        // txtView.setText(String.valueOf(rating.getProgress()));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel",

                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }

                        });

        popDialog.create();
        popDialog.show();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBlockUser:
                showPopup(ViewFinalizedResponseDetailView.this);
                break;
            case R.id.btnRateUser:
                showDetail();
                break;

        }
    }

    public void showDetail() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rating, null);
        dialogBuilder.setView(dialogView);
        //dialogBuilder.setTitle("Rate This!");
        //dialogBuilder.setIcon(R.drawable.logo3);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void showPopup(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to block this user?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logo3).show();
    }


    public void webFinalizeResponseDeatil(String userId, String reqId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewFinalizedResponseDetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewFinalizedResponseDetailView.this)) {
                        CM.showPopupCommonValidation(ViewFinalizedResponseDetailView.this, error, false);
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
            CM.showPopupCommonValidation(ViewFinalizedResponseDetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());
                    refId.setText(jsonObject1.optString("reference_id"));
                    budget.setText(jsonObject1.optString("total_budget"));
                    members.setText(jsonObject1.optString("adult"));
                    childres.setText(jsonObject1.optString("children"));
                    singlePer.setText(jsonObject1.optString("room1"));
                    doublePer.setText(jsonObject1.optString("room2"));
                    triplePer.setText(jsonObject1.optString("room3"));
                    child_with_bed.setText(jsonObject1.optString("child_with_bed"));
                    child_without_bed.setText(jsonObject1.optString("child_without_bed"));


                    checkIn.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("check_in")));
                    checkout.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("check_out")));
                    //  destiState.setText(jsonObject1.optString("pickup_state"));
                    //  destiCity.setText(jsonObject1.optString("destination_city"));


                    if (!jsonObject1.optString("pickup_state").equals("")) {
                        webState(jsonObject1.optString("pickup_state"), "1");
                    } else {

                    }


                    if (!jsonObject1.optString("destination_city").equals("") || !jsonObject1.optString("destination_city").equals("0")) {
                        webCity(jsonObject1.optString("destination_city"), "1");
                    } else {

                    }


                    locality.setText(jsonObject1.optString("locality"));
                    //hotelCat.setText(CM.setHotelCat(jsonObject1.optString("hotel_category")));

                    String hotel_categorys = "";
                    String[] items = jsonObject1.optString("hotel_category").split(",");
                    for (String item : items) {
                        if (hotel_categorys.equals("")) {
                            hotel_categorys = CM.setHotelCat(item);
                        } else {
                            hotel_categorys = hotel_categorys + "," + CM.setHotelCat(item);
                        }
                        // CM.showToast(item,ViewRespondToRequestDetailView.this);
                    }

                    hotelCat.setText(hotel_categorys);
                    hotelCat.setSelected(true);
                    meal.setText(CM.getMealPlane(jsonObject1.optString("meal_plan")));
                    comment.setText(jsonObject1.optString("comment"));


                    trasnStrtDate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("start_date")));
                    trasnEndDate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("end_date")));
                    //transPickupState.setText(jsonObject1.optString("pickup_state"));
                    //transPickupCity.setText(jsonObject1.optString("pickup_city"));
                    transPickupLocat.setText(jsonObject1.optString("pickup_locality"));


                    if (!jsonObject1.optString("pickup_state").equals("")) {
                        webState(jsonObject1.optString("pickup_state"), "2");
                    } else {

                    }
                    if (!jsonObject1.optString("pickup_city").equals("") || !jsonObject1.optString("pickup_city").equals("0")) {
                        webCity(jsonObject1.optString("pickup_city"), "2");
                    } else {

                    }


                    JSONArray jsonArray = new JSONArray(jsonObject1.optString("hotels").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        pojoAddAnother addAnother = new pojoAddAnother();
                        addAnother.setSingleRoom(jsonArray.getJSONObject(i).optString("room1"));
                        addAnother.setDoubleRoom(jsonArray.getJSONObject(i).optString("room2"));
                        addAnother.setTripleRomm(jsonArray.getJSONObject(i).optString("room3"));
                        addAnother.setChild_with_bed(jsonArray.getJSONObject(i).optString("child_with_bed"));
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
                                hotel_categorys11 = hotel_categorys + "," + CM.setHotelCat(items3);
                            }
                            // CM.showToast(item,ViewRespondToRequestDetailView.this);
                        }

                        addAnother.setHotel_category(hotel_categorys11);
                        addAnother.setMeal_plan(jsonArray.getJSONObject(i).optString("meal_plan"));
                        pojoAddAnotherArrayList.add(addAnother);
                    }

                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.invalidate();
                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewFinalizedResponseDetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewFinalizedResponseDetailView.this, e.getMessage(), false);
        }
    }


    public void webCity(String cityId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewFinalizedResponseDetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewFinalizedResponseDetailView.this)) {
                        CM.showPopupCommonValidation(ViewFinalizedResponseDetailView.this, error, false);
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
            CM.showPopupCommonValidation(ViewFinalizedResponseDetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (jsonObject.get("response_object").toString() == null) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());
                        if (type.equals("1")) {
                            destiCity.setText(jsonObject1.optString("name"));
                        } else {
                            transPickupCity.setText(jsonObject1.optString("name"));
                        }
                    }

                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewFinalizedResponseDetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewFinalizedResponseDetailView.this, e.getMessage(), false);
        }
    }

    public void webState(String stateId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewFinalizedResponseDetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewFinalizedResponseDetailView.this)) {
                        CM.showPopupCommonValidation(ViewFinalizedResponseDetailView.this, error, false);
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
            CM.showPopupCommonValidation(ViewFinalizedResponseDetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":

                    if (jsonObject.optString("response_object") != null) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());
                        if (type.equals("1")) {

                            destiState.setText(jsonObject1.optString("state_name"));

                        } else {
                            transPickupState.setText(jsonObject1.optString("state_name"));

                        }
                    }


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewFinalizedResponseDetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewFinalizedResponseDetailView.this, e.getMessage(), false);
        }
    }


}
