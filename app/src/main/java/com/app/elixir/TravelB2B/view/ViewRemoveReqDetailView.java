package com.app.elixir.TravelB2B.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

public class ViewRemoveReqDetailView extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewRemoveReqDetailView";
    private MtplButton btnBlockUser;
    private MtplButton btnRateUser;
    Toolbar toolbar;
    String requestId = "";
    MtplTextView refId, budget, members, childres, startDate, endDate, pickupState, pickupCity, pickupLocality, finalState, finalCity, vichel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_remove_request_detail_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.removed_requests));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CM.finishActivity(ViewRemoveReqDetailView.this);
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


        refId = (MtplTextView) findViewById(R.id.txtRefId);
        budget = (MtplTextView) findViewById(R.id.txttotBudget);
        members = (MtplTextView) findViewById(R.id.txtMemebers);
        childres = (MtplTextView) findViewById(R.id.txtChildren);
        vichel = (MtplTextView) findViewById(R.id.txtVichel);
        startDate = (MtplTextView) findViewById(R.id.txtStartDate);
        endDate = (MtplTextView) findViewById(R.id.txtendDate);
        pickupState = (MtplTextView) findViewById(R.id.txtPickupState);
        pickupCity = (MtplTextView) findViewById(R.id.txtpickupCity);
        pickupLocality = (MtplTextView) findViewById(R.id.txtpickupLocality);
        finalState = (MtplTextView) findViewById(R.id.txtfinalState);
        finalCity = (MtplTextView) findViewById(R.id.txtfinalCity);


        if (CM.isInternetAvailable(ViewRemoveReqDetailView.this)) {

            webMyResponseDeatil(CM.getSp(ViewRemoveReqDetailView.this, CV.PrefID, "").toString(), requestId);

        } else {

            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewRemoveReqDetailView.this);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewRemoveReqDetailView.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CM.finishActivity(ViewRemoveReqDetailView.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBlockUser:

                break;
            case R.id.btnRateUser:

                break;

        }
    }

    public void webMyResponseDeatil(String userId, String reqId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewRemoveReqDetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewRemoveReqDetailView.this)) {
                        CM.showPopupCommonValidation(ViewRemoveReqDetailView.this, error, false);
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
            CM.showPopupCommonValidation(ViewRemoveReqDetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":


                    if (jsonObject.optString("response_object") != null) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());

                        refId.setText(jsonObject1.optString("reference_id"));
                        budget.setText(getString(R.string.rsSymbol) + " " + jsonObject1.optString("total_budget"));
                        members.setText(jsonObject1.optString("adult"));
                        childres.setText(jsonObject1.optString("children"));
                        startDate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("start_date")));
                        endDate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("end_date")));
                        //pickupState.setText(jsonObject1.optString("pickup_state"));
                        //  pickupCity.setText(jsonObject1.optString("pickup_city"));
                        pickupLocality.setText(jsonObject1.optString("locality"));
                        //     finalState.setText();
                        //   finalCity.setText();
                        //  vichel.setText(jsonObject1.optString("comment"));

                        if (jsonObject1.optString("pickup_state").equals("") || jsonObject1.optString("pickup_state").toString().equals("null") || jsonObject1.optString("pickup_state") == null) {

                        } else {


                            if (CM.isInternetAvailable(ViewRemoveReqDetailView.this)) {
                                webState(jsonObject1.optString("pickup_city"), "1");
                            } else {
                                CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewRemoveReqDetailView.this);
                            }
                        }

                        if (jsonObject1.optString("pickup_city").equals("") || jsonObject1.optString("pickup_city").toString().equals("null") || jsonObject1.optString("pickup_city") == null) {

                        } else {

                            if (CM.isInternetAvailable(ViewRemoveReqDetailView.this)) {
                                webCity(jsonObject1.optString("pickup_state"), "1");
                            } else {
                                CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewRemoveReqDetailView.this);
                            }

                        }


                        if (jsonObject1.optString("final_city").equals("") || jsonObject1.optString("final_city").toString().equals("null") || jsonObject1.optString("final_city") == null) {

                        } else {


                            if (CM.isInternetAvailable(ViewRemoveReqDetailView.this)) {
                                webCity(jsonObject1.optString("final_city"), "2");
                            } else {
                                CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewRemoveReqDetailView.this);
                            }
                        }

                        if (jsonObject1.optString("final_state").equals("") || jsonObject1.optString("final_state").toString().equals("null") || jsonObject1.optString("final_state") == null) {

                        } else {


                            if (CM.isInternetAvailable(ViewRemoveReqDetailView.this)) {
                                webState(jsonObject1.optString("final_state"), "2");
                            } else {
                                CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewRemoveReqDetailView.this);
                            }
                        }


                    }


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewRemoveReqDetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewRemoveReqDetailView.this, e.getMessage(), false);
        }
    }


    public void webCity(String cityId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewRemoveReqDetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewRemoveReqDetailView.this)) {
                        CM.showPopupCommonValidation(ViewRemoveReqDetailView.this, error, false);
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
            CM.showPopupCommonValidation(ViewRemoveReqDetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());


                    if (type.equals("1")) {
                        pickupCity.setText(jsonObject1.optString("name"));
                    } else {
                        finalCity.setText(jsonObject1.optString("name"));
                    }


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewRemoveReqDetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewRemoveReqDetailView.this, e.getMessage(), false);
        }
    }

    public void webState(String stateId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewRemoveReqDetailView.this, true, true);
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
                    if (CM.isInternetAvailable(ViewRemoveReqDetailView.this)) {
                        CM.showPopupCommonValidation(ViewRemoveReqDetailView.this, error, false);
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
            CM.showPopupCommonValidation(ViewRemoveReqDetailView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());

                    if (type.equals("1")) {
                        pickupState.setText(jsonObject1.optString("state_name"));
                    } else {
                        finalState.setText(jsonObject1.optString("state_name"));
                    }
                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewRemoveReqDetailView.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewRemoveReqDetailView.this, e.getMessage(), false);
        }
    }
}
