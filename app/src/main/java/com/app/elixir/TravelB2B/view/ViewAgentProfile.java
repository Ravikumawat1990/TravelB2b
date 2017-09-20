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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptCertificate;
import com.app.elixir.TravelB2B.adapter.adptreview;
import com.app.elixir.TravelB2B.model.Model_Profile;
import com.app.elixir.TravelB2B.model.pojoAdvert;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.certifyCatePogo;
import com.app.elixir.TravelB2B.pojos.pojoTestimonial;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAgentProfile extends AppCompatActivity {
    private static final String TAG = "ViewAgentProfile";

    //region VARIABLES
    adptreview mAdapter;
    RecyclerView recycleViewTestimonial, recyclerViewAdv;
    ArrayList<pojoTestimonial> pojoTestimonialArrayList;
    Toolbar toolbar;
    private ArrayList<pojoAdvert> pojoAdvertArrayList;
    MtplTextView userName, phoneNumber, email, discription;
    ArrayList<certifyCatePogo> certifyCatePogoArrayList;
    ArrayList<String> stringArrayList;
    adptCertificate mAdapter1;
    CircleImageView circularTextView;
    String userId = "";
    ImageView icon1, icon2, icon3, icon4;
    TextView txtCount;
    //endregion

    ProgressBar progressBar;
    CardView cardViewReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.agntProfile));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        //String rAVI kUMAWAT;

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


        pojoAdvertArrayList = new ArrayList<>();
        stringArrayList = new ArrayList<>();
        certifyCatePogoArrayList = new ArrayList<>();
        mAdapter1 = new adptCertificate(ViewAgentProfile.this, stringArrayList);
        recyclerViewAdv.setAdapter(mAdapter1);
        pojoTestimonialArrayList = new ArrayList<>();
        mAdapter = new adptreview(ViewAgentProfile.this, pojoTestimonialArrayList);
        circularTextView = (CircleImageView) findViewById(R.id.imageView);
        cardViewReview = (CardView) findViewById(R.id.cardReview);


        icon1 = (ImageView) findViewById(R.id.icon1);
        icon2 = (ImageView) findViewById(R.id.icon2);
        icon3 = (ImageView) findViewById(R.id.icon3);
        icon4 = (ImageView) findViewById(R.id.icon4);
        txtCount = (TextView) findViewById(R.id.totRating);
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);


        initView();
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        if (CM.isInternetAvailable(ViewAgentProfile.this)) {
            webTestimonial(userId);
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewAgentProfile.this);
        }


    }

    private void initView() {

        userName = (MtplTextView) findViewById(R.id.userName);
        phoneNumber = (MtplTextView) findViewById(R.id.phoneNumber);
        email = (MtplTextView) findViewById(R.id.email);
        email.setSelected(true);
        discription = (MtplTextView) findViewById(R.id.txtDiscription);
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
                    webUserProfile(userId);

                    if (jsonArray.length() == 0) {
                        cardViewReview.setVisibility(View.GONE);
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {

                        pojoTestimonial pojoTestimonial = new pojoTestimonial();
                        pojoTestimonial.setComment(jsonArray.getJSONObject(i).optString("comment"));
                        pojoTestimonial.setAuthor_id(jsonArray.getJSONObject(i).optString("author_id"));
                        pojoTestimonial.setDescription(jsonArray.getJSONObject(i).optString("description"));
                        pojoTestimonial.setName(jsonArray.getJSONObject(i).optString("name"));
                        pojoTestimonial.setProfile_pic(jsonArray.getJSONObject(i).optString("profile_pic"));
                        pojoTestimonial.setUser_id(jsonArray.getJSONObject(i).optString("user_id"));
                        pojoTestimonial.setAuthor_id(jsonArray.getJSONObject(i).optString("author_id"));
                        // JSONObject jsonObject1 = new JSONObject(jsonArray.getJSONObject(i).optString("userprofile"));


                        pojoTestimonialArrayList.add(pojoTestimonial);

                    }
                    //JSONObject jsonObject1 = new JSONObject(jsonArray.getJSONObject(0).optString("userprofile"));

                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("userprofile").toString());
                    userName.setText(jsonObject1.optString("first_name") + " " + jsonObject1.optString("last_name"));
                    phoneNumber.setText(jsonObject1.optString("mobile_number"));
                    email.setText(jsonObject1.optString("email"));
                    discription.setText(jsonObject1.optString("description"));

                    try {
                        // Log.i("TAG", "onBindViewHolder: " + "http://www.travelb2bhub.com/b2b/img/user_docs/" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonArray.getJSONObject(i).optString("profile_pic"));
                        Picasso.with(ViewAgentProfile.this)
                                .load("http://www.travelb2bhub.com/b2b/img/user_docs/" + jsonObject1.optString("id") + "/" + jsonObject1.optString("profile_pic"))  //URLS.UPLOAD_IMG_URL + "" + dataSet.get(position).getHotel_pic()
                                .placeholder(R.drawable.ic_person_black_24dp) // optional
                                .error(R.drawable.ic_person_black_24dp)         // optional
                                .into(circularTextView);

                    } catch (Exception e) {

                        Log.i("TAG", "onBindViewHolder: " + e.getMessage());
                    }


                   /* certifyCatePogo catePogo = new certifyCatePogo();
                    catePogo.setIata_pic(jsonObject1.optString("iata_pic"));
                    catePogo.setTafi_pic(jsonObject1.optString("tafi_pic"));
                    catePogo.setTaai_pic(jsonObject1.optString("taai_pic"));
                    catePogo.setIato_pic(jsonObject1.optString("iato_pic"));
                    catePogo.setAdyoi_pic(jsonObject1.optString("adyoi_pic"));
                    catePogo.setIso9001_pic(jsonObject1.optString("iso9001_pic"));
                    catePogo.setUftaa_pic(jsonObject1.optString("uftaa_pic"));
                    catePogo.setAdyoi_pic(jsonObject1.optString("adtoi_pic"));*/

                    stringArrayList.add(jsonObject1.optString("iata_pic"));
                    stringArrayList.add(jsonObject1.optString("tafi_pic"));
                    stringArrayList.add(jsonObject1.optString("taai_pic"));
                    stringArrayList.add(jsonObject1.optString("iato_pic"));
                    stringArrayList.add(jsonObject1.optString("adyoi_pic"));
                    stringArrayList.add(jsonObject1.optString("iso9001_pic"));
                    stringArrayList.add(jsonObject1.optString("uftaa_pic"));
                    stringArrayList.add(jsonObject1.optString("adtoi_pic"));
                    //certifyCatePogoArrayList.add(catePogo);


                    pojoTestimonialArrayList.size();
                    recycleViewTestimonial.setAdapter(mAdapter);
                    recyclerViewAdv.setAdapter(mAdapter1);
                    recyclerViewAdv.invalidate();
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

    public void webUserProfile(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewAgentProfile.this, false, false);
            WebService.getUserProfile(v, userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getUserProfile(response);

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

    private void getUserProfile(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewAgentProfile.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object"));
                    Model_Profile model_main = CM.JsonParse(new Model_Profile(), jsonObject.getString("response_object"));

                    int i = 0;

                    if (model_main.pancard_pic != null && !model_main.pancard_pic.equals("")) {
                        icon2.setVisibility(View.VISIBLE);
                        icon2.setImageResource(R.drawable.pancard);
                        if (!model_main.role_id.toString().equals("null") && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2")) {
                                i += 16;
                            } else if (model_main.role_id.toString().equals("3")) {
                                i += 10;
                            } else {
                                i += 5;
                            }
                        }
                    } else {
                        icon2.setVisibility(View.GONE);
                    }
                    if (model_main.company_shop_registration_pic != null && !model_main.company_shop_registration_pic.toString().equals("")) {
                        icon4.setVisibility(View.VISIBLE);
                        icon4.setImageResource(R.drawable.conreg);
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2")) {
                                i += 15;
                            } else if (model_main.role_id.toString().equals("3")) {
                                i += 10;
                            } else {
                                i += 5;
                            }
                        }
                    } else {
                        icon4.setVisibility(View.GONE);
                    }
                    if (model_main.company_img_1_pic != null && !model_main.company_img_1_pic.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 10;
                            } else {
                                i += 5;
                            }
                        }
                    }
                    if (model_main.id_card_pic != null && !model_main.id_card_pic.toString().equals("")) {
                        icon3.setVisibility(View.VISIBLE);
                        icon3.setImageResource(R.drawable.idcard);
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 10;
                            } else {
                                i += 5;
                            }
                        }
                    } else {
                        icon3.setVisibility(View.GONE);
                    }


                    if (model_main.profile_pic != null && !model_main.profile_pic.equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 10;
                            } else {
                                i += 5;
                            }
                        }
                    }
                    if (model_main.first_name != null && !model_main.first_name.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 3;
                            } else {
                                i += 2;
                            }
                        }
                    }
                    if (model_main.company_name != null && !model_main.company_name.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 3;
                            } else {
                                i += 2;
                            }
                        }
                    }
                    if (model_main.email != null && !model_main.email.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.mobile_number != null && !model_main.mobile_number.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 4;
                            } else {
                                i += 5;
                            }
                        }
                    }
                    if (model_main.p_contact != null && !model_main.p_contact.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.address != null && !model_main.address.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.locality != null && !model_main.locality.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("3")) {
                                i += 3;
                            } else {
                                i += 2;
                            }
                        }
                    }
                    if (model_main.city_id != null && !model_main.city_id.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.state_id != null && !model_main.state_id.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.country_id != null && !model_main.country_id.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.pincode != null && !model_main.pincode.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.web_url != null && !model_main.web_url.toString().equals("")) {
                        i += 3;
                        icon1.setVisibility(View.VISIBLE);
                        icon1.setImageResource(R.drawable.weburl);
                    } else {
                        icon1.setVisibility(View.GONE);
                    }
                    if (model_main.description != null && !model_main.description.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 3;
                            } else {
                                i += 2;
                            }
                        }
                    }
                    if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                        if (model_main.role_id.toString().equals("3")) {
                            if (model_main.hotel_rating != null && !model_main.hotel_rating.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.hotel_categories != null && !model_main.hotel_categories.toString().equals("")) {
                                i += 5;
                            }
                        } else {

                        }
                    }

                    if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                        if (model_main.role_id.toString().equals("1")) {

                            if (model_main.iata_pic != null && !model_main.iata_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.tafi_pic != null && !model_main.tafi_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.taai_pic != null && !model_main.taai_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.iato_pic != null && !model_main.iato_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.adyoi_pic != null && !model_main.adyoi_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.iso9001_pic != null && !model_main.iso9001_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.uftaa_pic != null && !model_main.uftaa_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.adtoi_pic != null && !model_main.adtoi_pic.toString().equals("")) {
                                i += 5;
                            }

                        } else {

                        }
                    }


                    txtCount.setText(String.valueOf(i) + " %");
                    progressBar.setProgress(i);


                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (
                Exception e)

        {
            CM.showPopupCommonValidation(ViewAgentProfile.this, e.getMessage(), false);
        }

    }
}
