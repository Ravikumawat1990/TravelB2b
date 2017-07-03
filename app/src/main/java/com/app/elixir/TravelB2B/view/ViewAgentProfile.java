package com.app.elixir.TravelB2B.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptAdvt;
import com.app.elixir.TravelB2B.adapter.adptreview;
import com.app.elixir.TravelB2B.model.PojoMyResponse;
import com.app.elixir.TravelB2B.model.pojoAdvert;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoTestimonial;
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

public class ViewAgentProfile extends AppCompatActivity {
    private static final String TAG = "ViewAgentProfile";
    adptreview mAdapter;
    ArrayList<PojoMyResponse> dataSet;
    RecyclerView recycleViewTestimonial, recyclerViewAdv;
    ArrayList<pojoTestimonial> pojoTestimonialArrayList;
    Toolbar toolbar;
    private ArrayList<pojoAdvert> pojoAdvertArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.edtProfile));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewAgentProfile.this);

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 600);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewAdv = (RecyclerView) findViewById(R.id.recycleView);
        recycleViewTestimonial = (RecyclerView) findViewById(R.id.recycleView1);
        recyclerViewAdv.setLayoutManager(layoutManager);
        recycleViewTestimonial.setLayoutManager(layoutManager1);

        dataSet = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            PojoMyResponse pojoMyResponse = new PojoMyResponse();
            pojoMyResponse.setRequestType("1");
            dataSet.add(pojoMyResponse);

        }
        pojoAdvertArrayList = new ArrayList<>();
        adptAdvt mAdapter1 = new adptAdvt(ViewAgentProfile.this, pojoAdvertArrayList);
        recyclerViewAdv.setAdapter(mAdapter1);
        pojoTestimonialArrayList = new ArrayList<>();
        mAdapter = new adptreview(ViewAgentProfile.this, pojoTestimonialArrayList);

        webTestimonial(CM.getSp(ViewAgentProfile.this, CV.PrefID, "").toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CM.finishActivity(ViewAgentProfile.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewAgentProfile.this);
    }

    public void webTestimonial(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewAgentProfile.this, true, true);
            WebService.getTestimonial(v, userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForTestimonial(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewAgentProfile.this)) {
                        CM.showPopupCommonValidation(ViewAgentProfile.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForTestimonial(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewAgentProfile.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    // CM.showToast(jsonObject.optString("response_object"), ViewAgentProfile.this);
                    JSONArray jsonArray = new JSONArray(jsonObject.optString("response_object").toString());


                    for (int i = 0; i < jsonArray.length(); i++) {

                        pojoTestimonial pojoTestimonial = new pojoTestimonial();
                        pojoTestimonial.setComment(jsonArray.getJSONObject(i).optString("comment"));
                        pojoTestimonial.setAuthor_id(jsonArray.getJSONObject(i).optString("author_id"));
                        pojoTestimonial.setDescription(jsonArray.getJSONObject(i).optString("description"));
                        pojoTestimonial.setName(jsonArray.getJSONObject(i).optString("name"));
                        pojoTestimonial.setProfile_pic(jsonArray.getJSONObject(i).optString("profile_pic"));
                        pojoTestimonial.setUser_id(jsonArray.getJSONObject(i).optString("user_id"));
                        pojoTestimonial.setAuthor_id(jsonArray.getJSONObject(i).optString("author_id"));
                        pojoTestimonialArrayList.add(pojoTestimonial);

                    }
                    pojoTestimonialArrayList.size();
                    recycleViewTestimonial.setAdapter(mAdapter);
                    recycleViewTestimonial.invalidate();

                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewAgentProfile.this);

                    finish();
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewAgentProfile.this, e.getMessage(), false);
        }
    }
}
