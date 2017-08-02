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
import android.support.v7.widget.CardView;
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

public class ViewMyResdetailView extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewMyResdetailView";
    private MtplButton btnBlockUser;
    private MtplButton btnRateUser;
    Toolbar toolbar;
    String referenceId = "";
    MtplTextView refId, budget, members, childres, singlePer, doublePer, triplePer, child_with_bed, child_without_bed, checkIn, checkout, destiState,
            destiCity, locality, hotelCat, meal, comment, vehicle, startdate, enddate, pickupCity, pickupLocation;
    // MtplTextView DetailTxt;
    //  String reqId = "", reqType = "";


    //cards
    CardView TransCard1, TransCard2, HotelCard;
    //New Transprot Variables
    MtplTextView transLable, TransRefId, TranBudget, TransMember, TransChildren, TransVehicle, TransStartdate, TransEnddate, TransPickState, TransPickCity, TransPickLocality, TransFinalState, TransFinalCity;
    MtplTextView DetailTxt;
    String reqId = "", reqType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_response_detail_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.myRespons));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CM.finishActivity(ViewMyResdetailView.this);

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
        referenceId = intent.getStringExtra("refId");
        reqId = intent.getStringExtra("reqtype");
        reqType = CM.getReqType(reqId);
        initView();

    }

    private void initView() {

        btnBlockUser = (MtplButton) findViewById(R.id.btnBlockUser);
        btnRateUser = (MtplButton) findViewById(R.id.btnRateUser);
        btnBlockUser.setOnClickListener(this);
        btnRateUser.setOnClickListener(this);
        DetailTxt = (MtplTextView) findViewById(R.id.detalidtxt);
        DetailTxt.setText(reqType + " Details");
        CardView cardView = (CardView) findViewById(R.id.cardViewReq);
        //MtplTextView mtplTextView = (MtplTextView) findViewById(R.id.transportTile);

        CardView cardPacakge = (CardView) findViewById(R.id.rootView);
        ///  LinearLayout packageTile = (LinearLayout) findViewById(R.id.packageTile);


       /* if (reqId.equals("2")) {
            cardView.setVisibility(View.VISIBLE);
            mtplTextView.setVisibility(View.VISIBLE);
            cardPacakge.setVisibility(View.GONE);
            packageTile.setVisibility(View.GONE);

        } else {
            cardPacakge.setVisibility(View.VISIBLE);
            packageTile.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
            mtplTextView.setVisibility(View.GONE);


        }*/
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
        hotelCat.setSelected(true);
        meal = (MtplTextView) findViewById(R.id.txtMeal);
        comment = (MtplTextView) findViewById(R.id.txtCmt);
        vehicle = (MtplTextView) findViewById(R.id.txtVehicle);
        startdate = (MtplTextView) findViewById(R.id.txtStartDate);
        enddate = (MtplTextView) findViewById(R.id.txtendDate);
        pickupCity = (MtplTextView) findViewById(R.id.txtpickupCity);
        pickupLocation = (MtplTextView) findViewById(R.id.txtpickupLocation);


        //Hotel Card
        HotelCard = (CardView) findViewById(R.id.rootView);
//Transport Cards
        TransCard1 = (CardView) findViewById(R.id.transportcart);
        TransCard2 = (CardView) findViewById(R.id.transprotcart2);

//New Transprot Variables
        TransRefId = (MtplTextView) findViewById(R.id.tranRefid);
        TranBudget = (MtplTextView) findViewById(R.id.tranT_budget);
        TransMember = (MtplTextView) findViewById(R.id.tran_member);
        TransChildren = (MtplTextView) findViewById(R.id.tran_child);
        TransVehicle = (MtplTextView) findViewById(R.id.tranVehicle);
        TransStartdate = (MtplTextView) findViewById(R.id.tranSdate);
        TransEnddate = (MtplTextView) findViewById(R.id.tranEdate);
        TransPickState = (MtplTextView) findViewById(R.id.tran_Pstate);
        TransPickCity = (MtplTextView) findViewById(R.id.tran_Pcity);
        TransPickLocality = (MtplTextView) findViewById(R.id.tran_Plocation);
        TransFinalState = (MtplTextView) findViewById(R.id.tran_Fcity);
        TransFinalCity = (MtplTextView) findViewById(R.id.tran_Fstate);
        transLable = (MtplTextView) findViewById(R.id.trans_lable);

        //Check ResponseType
        if (reqId.equals("1")) {
            TransCard2.setVisibility(View.GONE);
            TransCard1.setVisibility(View.VISIBLE);
            HotelCard.setVisibility(View.VISIBLE);
            transLable.setVisibility(View.VISIBLE);

        } else if (reqId.equals("2")) {
            TransCard1.setVisibility(View.GONE);
            TransCard2.setVisibility(View.VISIBLE);
            HotelCard.setVisibility(View.GONE);
            transLable.setVisibility(View.GONE);
        } else {
            TransCard1.setVisibility(View.GONE);
            TransCard2.setVisibility(View.GONE);
            HotelCard.setVisibility(View.VISIBLE);
            transLable.setVisibility(View.GONE);
        }


        if (CM.isInternetAvailable(ViewMyResdetailView.this)) {
            webMyResponseDeatil(CM.getSp(ViewMyResdetailView.this, CV.PrefID, "").toString(), referenceId);
        } else {

            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewMyResdetailView.this);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewMyResdetailView.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CM.finishActivity(ViewMyResdetailView.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ShowRatingDialog() {

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final RatingBar rating = new RatingBar(this);
        rating.setNumStars(5);
        popDialog.setIcon(R.drawable.logo3);
        popDialog.setTitle("Rate This!! ");
        popDialog.setView(rating);
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }

                });

        popDialog.create();
        popDialog.show();


    }

    public void showDetail() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rating, null);
        dialogBuilder.setView(dialogView);
        //   dialogBuilder.setTitle("Rate This!");
        //  dialogBuilder.setIcon(R.drawable.logo3);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBlockUser:
                showPopup(ViewMyResdetailView.this);
                break;
            case R.id.btnRateUser:
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


    private void actionBarIdForAll() {
        int titleId = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        } else {
            titleId = R.id.action_bar_title;
        }

        if (titleId > 0) {
            try {
                Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_DroidSerif_Bold));
                TextView titleView = (TextView) findViewById(titleId);
                titleView.setText("gfdg");
                titleView.setTypeface(font);

            } catch (Exception e) {

                e.getMessage();

            }
        }
    }


    public void webMyResponseDeatil(String userId, String reqId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewMyResdetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewMyResdetailView.this)) {
                        CM.showPopupCommonValidation(ViewMyResdetailView.this, error, false);
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
            CM.showPopupCommonValidation(ViewMyResdetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());


                    refId.setText(jsonObject1.optString("reference_id"));
                    budget.setText(getString(R.string.rsSymbol) + " " + jsonObject1.optString("total_budget"));
                    members.setText(jsonObject1.optString("adult"));
                    childres.setText(jsonObject1.optString("children"));
                    singlePer.setText(jsonObject1.optString("room1"));
                    doublePer.setText(jsonObject1.optString("room2"));
                    triplePer.setText(jsonObject1.optString("room3"));
                    child_with_bed.setText(jsonObject1.optString("child_with_bed"));
                    child_without_bed.setText(jsonObject1.optString("child_without_bed"));
                    checkIn.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("check_in").trim().toString()));
                    checkout.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("check_out").trim().toString()));
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
                    if (jsonObject1.optString("transport_requirement") != null) {
                        vehicle.setText(CM.setVichel(jsonObject1.optString("transport_requirement")));

                    }
                    startdate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("start_date")));
                    enddate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("end_date")));

                    pickupLocation.setText(jsonObject1.optString("pickup_locality"));


                    if (!jsonObject1.optString("city_id").equals("") || !jsonObject1.optString("city_id").toString().equals("0")) {
                        webCity(jsonObject1.optString("city_id"), "1");
                    } else {

                    }

                    if (!jsonObject1.optString("pickup_city").equals("") && !jsonObject1.optString("pickup_city").toString().equals("0") && !jsonObject1.optString("pickup_city").toString().equals("null")) {
                        webCity(jsonObject1.optString("pickup_city"), "2");
                    } else {

                    }

                    if (!jsonObject1.optString("state_id").toString().equals("") && !jsonObject1.optString("state_id").toString().equals("null") && !jsonObject1.optString("state_id").toString().equals("0")) {
                        webState(jsonObject1.optString("state_id"), "1");
                    } else {

                    }


                    //transport
                    TransRefId.setText(jsonObject1.optString("reference_id"));
                    TranBudget.setText(getString(R.string.rsSymbol) + " " + jsonObject1.optString("total_budget"));
                    TransMember.setText(jsonObject1.optString("adult"));
                    TransChildren.setText(jsonObject1.optString("children"));


//transport
                    TransVehicle.setText("");
                    TransStartdate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("start_date")));
                    TransEnddate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("end_date")));
                    TransPickLocality.setText(jsonObject1.optString("pickup_locality"));


                    break;
                case "202":
                    break;
                case "501":
                    //  CM.showToast(jsonObject.optString("msg"), ViewMyResdetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewMyResdetailView.this, e.getMessage(), false);
        }
    }


    public void webCity(String cityId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewMyResdetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewMyResdetailView.this)) {
                        CM.showPopupCommonValidation(ViewMyResdetailView.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // {"response_code":200,"response_object":null}
    private void getCity(String response, String type) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewMyResdetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":

                    if (!jsonObject.optString("response_object").toString().equals("null")) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());
                        if (type.equals("1")) {
                            destiCity.setText(jsonObject1.optString("name"));
                            TransFinalCity.setText(jsonObject1.optString("name"));
                        } else {
                            pickupCity.setText(jsonObject1.optString("name"));
                            TransPickCity.setText(jsonObject1.optString("name"));
                        }

                    }


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewMyResdetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewMyResdetailView.this, e.getMessage(), false);
        }
    }

    public void webState(String stateId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewMyResdetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewMyResdetailView.this)) {
                        CM.showPopupCommonValidation(ViewMyResdetailView.this, error, false);
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
            CM.showPopupCommonValidation(ViewMyResdetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());

                    if (type.equals("1")) {
                        destiState.setText(jsonObject1.optString("state_name"));
                        TransFinalState.setText(jsonObject1.optString("state_name"));
                    } else {
                        //    finalState.setText(jsonObject1.optString("state_name"));
                    }
                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewMyResdetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewMyResdetailView.this, e.getMessage(), false);
        }
    }
}
