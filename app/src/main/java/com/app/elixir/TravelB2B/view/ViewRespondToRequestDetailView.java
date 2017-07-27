package com.app.elixir.TravelB2B.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class ViewRespondToRequestDetailView extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewRespondToRequest";
    private MtplButton btnBlockUser;
    private MtplButton btnRateUser;
    Toolbar toolbar;

    MtplTextView refId, budget, members, childres, singlePer, doublePer, triplePer, child_with_bed, child_without_bed, checkIn, checkout, destiState,
            destiCity, locality, hotelCat, meal, comment, vehicle, startdate, enddate, pickupCity, pickupLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_response_detail_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.restoReqDetail));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewRespondToRequestDetailView.this);

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

        initView();

    }

    private void initView() {

        btnBlockUser = (MtplButton) findViewById(R.id.btnBlockUser);
        btnRateUser = (MtplButton) findViewById(R.id.btnRateUser);
        btnBlockUser.setOnClickListener(this);
        btnRateUser.setOnClickListener(this);

        refId = (MtplTextView) findViewById(R.id.txtReqId);
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
        comment = (MtplTextView) findViewById(R.id.txtCmt);
        vehicle = (MtplTextView) findViewById(R.id.txtVehicle);
        startdate = (MtplTextView) findViewById(R.id.txtStartDate);
        enddate = (MtplTextView) findViewById(R.id.txtendDate);
        pickupCity = (MtplTextView) findViewById(R.id.txtpickupCity);
        pickupLocation = (MtplTextView) findViewById(R.id.txtpickupLocation);


        btnBlockUser = (MtplButton) findViewById(R.id.btnBlockUser);
        btnRateUser = (MtplButton) findViewById(R.id.btnRateUser);
        btnBlockUser.setOnClickListener(this);
        btnRateUser.setOnClickListener(this);

        Intent intent = getIntent();
        String referenceId = intent.getStringExtra("refId");


        if (CM.isInternetAvailable(ViewRespondToRequestDetailView.this)) {
            webMyResponseDeatil(CM.getSp(ViewRespondToRequestDetailView.this, CV.PrefID, "").toString(), referenceId);
        } else {

            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewRespondToRequestDetailView.this);
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewRespondToRequestDetailView.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CM.finishActivity(ViewRespondToRequestDetailView.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ShowRatingDialog() {

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final RatingBar rating = new RatingBar(this);
        rating.setMax(5);
        rating.setRating(5);
        rating.setNumStars(5);
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
                showPopup(ViewRespondToRequestDetailView.this);
                break;
            case R.id.btnRateUser:
                // ShowRatingDialog();
                showDetail();
                break;

        }
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
        }).setIcon(R.drawable.logo1).show();
    }


    public void showDetail() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rating, null);
        dialogBuilder.setView(dialogView);
        //  dialogBuilder.setTitle("Rate This!");
        // dialogBuilder.setIcon(R.drawable.logo3);
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


    public void webMyResponseDeatil(String userId, String reqId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewRespondToRequestDetailView.this, true, true);
            WebService.getDetail(v, userId, reqId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getMyResponseDeatil(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewRespondToRequestDetailView.this)) {
                        CM.showPopupCommonValidation(ViewRespondToRequestDetailView.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getMyResponseDeatil(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewRespondToRequestDetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    final JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());


                    refId.setText(jsonObject1.optString("reference_id"));
                    budget.setText(getString(R.string.rsSymbol) + " " + jsonObject1.optString("total_budget"));
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
                    // destiCity.setText(jsonObject1.optString("destination_city"));
                    locality.setText(jsonObject1.optString("locality"));
                    //  hotelCat.setText(CM.setHotelCat(jsonObject1.optString("hotel_category")));


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
                    vehicle.setText("");
                    startdate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("start_date")));
                    enddate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("end_date")));
                    //  pickupCity.setText(jsonObject1.optString("pickup_city"));
                    pickupLocation.setText(jsonObject1.optString("pickup_locality"));
                    // jsonObject1.optString("pickup_country")


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (jsonObject1.optString("city_id").equals("") || jsonObject1.optString("city_id").toString().equals("0")) {

                            } else {
                                webCity(jsonObject1.optString("city_id"), "1");
                            }
                        }
                    }, 100);


                    final Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (jsonObject1.optString("pickup_city").equals("") || jsonObject1.optString("pickup_city").toString().equals("0") || jsonObject1.optString("pickup_city").equals("null")) {

                            } else {
                                webCity(jsonObject1.optString("pickup_city"), "2");
                            }
                        }

                    }, 200);

                    final Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (jsonObject1.optString("pickup_state").equals("") || jsonObject1.optString("pickup_state").equals("null")) {

                            } else {
                                webState(jsonObject1.optString("pickup_state"), "1");
                            }
                        }

                    }, 300);


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewRespondToRequestDetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewRespondToRequestDetailView.this, e.getMessage(), false);
        }
    }


    public void webCity(String cityId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewRespondToRequestDetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewRespondToRequestDetailView.this)) {
                        CM.showPopupCommonValidation(ViewRespondToRequestDetailView.this, error, false);
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
            CM.showPopupCommonValidation(ViewRespondToRequestDetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());


                    if (type.equals("1")) {
                        destiCity.setText(jsonObject1.optString("name"));
                    } else {
                        pickupCity.setText(jsonObject1.optString("name"));
                    }


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewRespondToRequestDetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewRespondToRequestDetailView.this, e.getMessage(), false);
        }
    }

    public void webState(String stateId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewRespondToRequestDetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewRespondToRequestDetailView.this)) {
                        CM.showPopupCommonValidation(ViewRespondToRequestDetailView.this, error, false);
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
            CM.showPopupCommonValidation(ViewRespondToRequestDetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());

                    if (type.equals("1")) {
                        destiState.setText(jsonObject1.optString("state_name"));
                    } else {
                        //    finalState.setText(jsonObject1.optString("state_name"));
                    }
                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewRespondToRequestDetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewRespondToRequestDetailView.this, e.getMessage(), false);
        }
    }

}
