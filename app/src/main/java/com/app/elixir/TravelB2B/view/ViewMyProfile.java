package com.app.elixir.TravelB2B.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptAdvtMyProfile;
import com.app.elixir.TravelB2B.adapter.adptCertificate;
import com.app.elixir.TravelB2B.adapter.adptreview;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.model.PojoMyResponse;
import com.app.elixir.TravelB2B.model.pojoAdvert;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoTestimonial;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CONSTANT;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.utils.URLS;
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

public class ViewMyProfile extends AppCompatActivity {

    private static final String TAG = "ViewMyProfile";
    adptreview mAdapter;
    ArrayList<PojoMyResponse> dataSet;
    RecyclerView recycleViewTestimonial, recyclerViewAdv;
    ArrayList<pojoTestimonial> pojoTestimonialArrayList;
    ArrayList<pojoAdvert> pojoAdvertArrayList;

    CircleImageView circleImageViewProfile;
    Toolbar toolbar;
    MtplTextView userEmail, userName, userMob;
    MtplTextView txtDis;
    ArrayList<String> stringArrayList;
    adptCertificate mAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.myProfile));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewMyProfile.this);

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewAdv = (RecyclerView) findViewById(R.id.recycleView);
        recycleViewTestimonial = (RecyclerView) findViewById(R.id.recycleView1);
        //  recyclerViewAdv.setHasFixedSize(true);
        // recycleViewTestimonial.setHasFixedSize(true);
        recyclerViewAdv.setLayoutManager(layoutManager);
        recycleViewTestimonial.setLayoutManager(layoutManager1);

        pojoTestimonialArrayList = new ArrayList<>();
        //  pojoAdvertArrayList = new ArrayList<>();
        stringArrayList = new ArrayList<>();

        //mAdapter1 = new adptAdvtMyProfile(ViewMyProfile.this, pojoAdvertArrayList);
        mAdapter = new adptreview(ViewMyProfile.this, pojoTestimonialArrayList);
        mAdapter1 = new adptCertificate(ViewMyProfile.this, stringArrayList);
        mAdapter1.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String item, String item1) {
                try {
                    if (!item1.startsWith("http://") && !item1.startsWith("https://"))
                        item1 = "http://" + item1;

                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item1));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(ViewMyProfile.this, "No application can handle this request."
                            + "Please install a webbrowser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });


        userEmail = (MtplTextView) findViewById(R.id.txtUserEmail);
        userName = (MtplTextView) findViewById(R.id.txtUserName);
        userMob = (MtplTextView) findViewById(R.id.userPNo);
        txtDis = (MtplTextView) findViewById(R.id.txtViewDis);

        //     userEmail.setText(CM.getSp(ViewMyProfile.this, CV.PrefEmail, "").toString());
        //   userName.setText(CM.getSp(ViewMyProfile.this, CV.Preffirst_name, "").toString() + " " + CM.getSp(ViewMyProfile.this, CV.Preflast_name, "").toString());
        //    userMob.setText(CM.getSp(ViewMyProfile.this, CV.PrefMobile_number, "").toString());
        circleImageViewProfile = (CircleImageView) findViewById(R.id.imageViewProfilePic);

        try {
            // Log.i("TAG", "onBindViewHolder: " + "http://www.travelb2bhub.com/b2b/img/user_docs/" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonArray.getJSONObject(i).optString("profile_pic"));
            Picasso.with(ViewMyProfile.this)
                    .load("http://www.travelb2bhub.com/b2b/img/user_docs/" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + CM.getSp(ViewMyProfile.this, CV.PROFILE_PIC, "").toString())  //URLS.UPLOAD_IMG_URL + "" + dataSet.get(position).getHotel_pic()
                    .placeholder(R.drawable.ic_person_black_24dp) // optional
                    .error(R.drawable.ic_person_black_24dp)         // optional
                    .into(circleImageViewProfile);

        } catch (Exception e) {

            Log.i("TAG", "onBindViewHolder: " + e.getMessage());
        }

        CardView cardViewAdv = (CardView) findViewById(R.id.cardViewAdv);

        if (CM.getSp(ViewMyProfile.this, CV.PrefRole_Id, "").equals("2")) {
            cardViewAdv.setVisibility(View.GONE);
        } else {
            cardViewAdv.setVisibility(View.VISIBLE);
        }

        if (CM.isInternetAvailable(ViewMyProfile.this)) {
            webTestimonial(CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString());


        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewMyProfile.this);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CM.finishActivity(ViewMyProfile.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewMyProfile.this);
    }

    public void webTestimonial(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewMyProfile.this, true, true);
            WebService.getMyProfile(v, userId, new OnVolleyHandler() {
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
                    if (CM.isInternetAvailable(ViewMyProfile.this)) {
                        CM.showPopupCommonValidation(ViewMyProfile.this, error, false);
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
            CM.showPopupCommonValidation(ViewMyProfile.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());
                    JSONArray jsonArray = new JSONArray(jsonObject1.optString("testimonial").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        pojoTestimonial pojoTestimonial = new pojoTestimonial();
                        pojoTestimonial.setComment(jsonArray.getJSONObject(i).optString("comment"));
                        pojoTestimonial.setAuthor_id(jsonArray.getJSONObject(i).optString("author_id"));
                        pojoTestimonial.setDescription(jsonArray.getJSONObject(i).optString("description"));
                        pojoTestimonial.setName(jsonArray.getJSONObject(i).optString("name"));
                        pojoTestimonial.setProfile_pic(jsonArray.getJSONObject(i).optString("profile_pic"));
                        pojoTestimonial.setUser_id(jsonArray.getJSONObject(i).optString("user_id"));
                        pojoTestimonial.setAuthor_id(jsonArray.getJSONObject(i).optString("author_id"));
                        pojoTestimonial.setRating(jsonArray.getJSONObject(i).optString("rating1"));
                        pojoTestimonialArrayList.add(pojoTestimonial);


                    }
                    JSONObject jsonObject2 = new JSONObject(jsonObject1.optString("users").toString());


                    userEmail.setText(jsonObject2.optString("email"));
                    userName.setText(jsonObject2.optString("first_name") + " " + jsonObject2.optString("last_name"));
                    userMob.setText(jsonObject2.optString("mobile_number"));

                    Picasso.with(ViewMyProfile.this)
                            .load(URLS.UPLOAD_IMG_URL1 + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonObject2.optString("profile_pic"))  //URLS.UPLOAD_IMG_URL + "" + dataSet.get(position).getHotel_pic()
                            .placeholder(R.drawable.ic_person_black_24dp) // optional
                            .error(R.drawable.ic_person_black_24dp)         // optional
                            .into(circleImageViewProfile);


                    stringArrayList.add(URLS.UPLOAD_IMG_URL2 + "" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonObject2.optString("iata_pic"));
                    stringArrayList.add(URLS.UPLOAD_IMG_URL2 + "" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonObject2.optString("tafi_pic"));
                    stringArrayList.add(URLS.UPLOAD_IMG_URL2 + "" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonObject2.optString("taai_pic"));
                    stringArrayList.add(URLS.UPLOAD_IMG_URL2 + "" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonObject2.optString("iato_pic"));
                    stringArrayList.add(URLS.UPLOAD_IMG_URL2 + "" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonObject2.optString("adyoi_pic"));
                    stringArrayList.add(URLS.UPLOAD_IMG_URL2 + "" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonObject2.optString("iso9001_pic"));
                    stringArrayList.add(URLS.UPLOAD_IMG_URL2 + "" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonObject2.optString("uftaa_pic"));
                    stringArrayList.add(URLS.UPLOAD_IMG_URL2 + "" + CM.getSp(ViewMyProfile.this, CV.PrefID, "").toString() + "/" + jsonObject2.optString("adtoi_pic"));


                    stringArrayList.size();
                    //  pojoTestimonialArrayList.size();
                    recycleViewTestimonial.setAdapter(mAdapter);
                    recyclerViewAdv.setAdapter(mAdapter1);
                    recyclerViewAdv.invalidate();
                    recycleViewTestimonial.invalidate();

                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewMyProfile.this);

                    finish();
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewMyProfile.this, e.getMessage(), false);
        }
    }


}
