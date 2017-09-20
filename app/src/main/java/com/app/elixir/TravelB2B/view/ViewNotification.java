package com.app.elixir.TravelB2B.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptAdvt;
import com.app.elixir.TravelB2B.adapter.adptnoti;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoNoti;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewNotification extends AppCompatActivity {

    private static final String TAG = "ViewNotification";
    Toolbar toolbar;
    ArrayList<pojoNoti> pojoNotis;
    adptnoti mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_noti_fication);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Notification");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CM.finishActivity(ViewNotification.this);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        initView();
        if (CM.isInternetAvailable(this)) {
            getNoti(CM.getSp(ViewNotification.this, CV.PrefID, "").toString());
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewNotification.this);
        }
    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        pojoNotis = new ArrayList<>();
        mAdapter = new adptnoti(ViewNotification.this, pojoNotis);

    }


    public void getClearNoti(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewNotification.this, true, true);
            WebService.getClearNoti(v, userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getNotiClearResponse(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewNotification.this)) {
                        CM.showPopupCommonValidation(ViewNotification.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getNoti(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewNotification.this, true, true);
            WebService.getNoti(v, userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getNotiResponse(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewNotification.this)) {
                        CM.showPopupCommonValidation(ViewNotification.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getNotiClearResponse(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewNotification.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":


                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewNotification.this, e.getMessage(), false);
        }
    }


    private void getNotiResponse(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewNotification.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":

                    //  CM.showToast(jsonObject.optString("response_object").toString(), ViewNotification.this);
                    JSONArray jsonArray = new JSONArray(jsonObject.optString("response_object").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        pojoNoti noti = new pojoNoti();
                        noti.setId(jsonArray.getJSONObject(i).optString("id"));
                        noti.setIs_read(jsonArray.getJSONObject(i).optString("is_read"));
                        noti.setNotification(jsonArray.getJSONObject(i).optString("notification"));
                        noti.setRead_date_time(jsonArray.getJSONObject(i).optString("read_date_time"));
                        noti.setRequest_id(jsonArray.getJSONObject(i).optString("request_id"));
                        noti.setSend_to_user_id(jsonArray.getJSONObject(i).optString("send_to_user_id"));
                        noti.setSender_name(jsonArray.getJSONObject(i).optString("sender_name"));
                        noti.setUser_id(jsonArray.getJSONObject(i).optString("user_id"));
                        noti.setMessage(jsonArray.getJSONObject(i).optString("message"));
                        noti.setCreated(jsonArray.getJSONObject(i).optString("created"));
                        pojoNotis.add(noti);
                    }
                    pojoNotis.size();
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.invalidate();
                    getClearNoti(CM.getSp(ViewNotification.this, CV.PrefID, "").toString());


                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewNotification.this, e.getMessage(), false);
        }
    }


    @Override
    public void onBackPressed() {
        CM.finishActivity(this);
        super.onBackPressed();
    }
}
