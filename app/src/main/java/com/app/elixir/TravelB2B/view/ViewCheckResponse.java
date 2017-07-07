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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptCheckResponse;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoCheckResposne;
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

public class ViewCheckResponse extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewCheckResponse";
    private RecyclerView mRecyclerView;
    adptCheckResponse mAdapter;
    MtplButton btnDetail;
    Toolbar toolbar;
    String referenceId = "";
    MtplTextView refId, budget, members, childres, startdate, enddate, pickupCity, pickupState, pickupLocation, vehicle, finalState, finalCity, comment;
    ArrayList<pojoCheckResposne> resposneArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_check_response);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.checkResp));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewCheckResponse.this);

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
        webCheckResponse(referenceId);

        initView();
    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ViewCheckResponse.this));
        btnDetail = (MtplButton) findViewById(R.id.btnDetail);

        resposneArrayList = new ArrayList<>();
        mAdapter = new adptCheckResponse(ViewCheckResponse.this, resposneArrayList);


        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value, String value1) {
                if (value.equals("chat")) {
                    CM.startActivity(ViewCheckResponse.this, ViewChat.class);
                } else if (value.equals("share")) {
                    showPopup(ViewCheckResponse.this, "Are you sure you want to share your details,with this user?");
                } else if (value.equals("btnAccept")) {
                    showPopup(ViewCheckResponse.this, "Are you sure you want to accept this offer?");
                } else if (value.equals("rate")) {
                    showRating();
                } else if (value.equals("block")) {
                    showPopup(ViewCheckResponse.this, "Are you sure you want to  block this user?");
                }
            }
        });
        btnDetail.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewCheckResponse.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.myrequest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CM.finishActivity(ViewCheckResponse.this);
                return true;
            case R.id.filter:
                showPopup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.filter, (ViewGroup) findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewCheckResponse.this)
                .setView(layout);
        builder.setTitle("Filter By:");
        SearchView searchView = (SearchView) layout.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by name, email, mobile");
        builder.setIcon(R.drawable.logo3);


        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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


    public void showPopup(Context context, String msg) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logo1).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDetail:
                showDetail();
                break;
        }
    }

    public void showDetail() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.detailview, null);
        dialogBuilder.setView(dialogView);

        refId = (MtplTextView) dialogView.findViewById(R.id.txtReqId);
        budget = (MtplTextView) dialogView.findViewById(R.id.txttotBudget);
        members = (MtplTextView) dialogView.findViewById(R.id.txtMemebers);
        childres = (MtplTextView) dialogView.findViewById(R.id.txtChildren);
        startdate = (MtplTextView) dialogView.findViewById(R.id.txtStartDate);
        enddate = (MtplTextView) dialogView.findViewById(R.id.txtendDate);
        pickupCity = (MtplTextView) dialogView.findViewById(R.id.txtpickupCity);
        pickupState = (MtplTextView) dialogView.findViewById(R.id.pickupState);
        pickupLocation = (MtplTextView) dialogView.findViewById(R.id.txtpickupLocation);
        vehicle = (MtplTextView) dialogView.findViewById(R.id.txtVehicle);
        finalState = (MtplTextView) dialogView.findViewById(R.id.txtDestState);
        finalCity = (MtplTextView) dialogView.findViewById(R.id.txtDestCity);
        comment = (MtplTextView) dialogView.findViewById(R.id.txtCmt);


        if (CM.isInternetAvailable(ViewCheckResponse.this)) {

            webMyResponseDetail(CM.getSp(ViewCheckResponse.this, CV.PrefID, "").toString(), referenceId);

        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), this);
        }

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

    public void showRating() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rating, null);
        dialogBuilder.setView(dialogView);
        // dialogBuilder.setTitle("Rate This!");
        //   dialogBuilder.setIcon(R.drawable.logo1);


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


    public void webCheckResponse(String reqId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCheckResponse.this, true, true);
            WebService.getCheckResposne(v, reqId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getCheckResponse(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        CM.showPopupCommonValidation(ViewCheckResponse.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void webMyResponseDetail(String userId, String reqId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCheckResponse.this, true, true);
            WebService.getDetail(v, userId, reqId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getMyResponseDetail(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        CM.showPopupCommonValidation(ViewCheckResponse.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getMyResponseDetail(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());

                    refId.setText(jsonObject1.optString("id"));
                    budget.setText(getString(R.string.rsSymbol) + " " + jsonObject1.optString("total_budget"));
                    members.setText(jsonObject1.optString("adult"));
                    childres.setText(jsonObject1.optString("children"));
                    comment.setText(jsonObject1.optString("comment"));
                    vehicle.setText("");
                    startdate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("start_date")));
                    enddate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("end_date")));
                    pickupCity.setText(jsonObject1.optString("pickup_city"));
                    pickupLocation.setText(jsonObject1.optString("pickup_locality"));


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewCheckResponse.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, e.getMessage(), false);
        }
    }

    private void getCheckResponse(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);

            switch (jsonObject.optString("response_code")) {
                case "200":


                    pojoCheckResposne checkResposne = new pojoCheckResposne();
                    JSONArray jsonArray = new JSONArray(jsonObject.optString("response_object").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        checkResposne.setQuotation_price(jsonArray.getJSONObject(i).optString("quotation_price"));
                        jsonArray.getJSONObject(i).optString("request_id");
                        checkResposne.setComment(jsonArray.getJSONObject(i).optString("comment"));
                        JSONObject jsonObject2 = new JSONObject(jsonArray.getJSONObject(i).optString("request").toString());
                        checkResposne.setReference_id(jsonObject2.optString("reference_id"));
                        checkResposne.setTotal_budget(jsonObject2.optString("total_budget"));
                        JSONObject jsonObject3 = new JSONObject(jsonArray.getJSONObject(i).optString("user").toString());
                        checkResposne.setFirst_name(jsonObject3.optString("first_name"));
                        checkResposne.setLast_name(jsonObject3.optString("last_name"));
                        resposneArrayList.add(checkResposne);

                    }
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.invalidate();


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewCheckResponse.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, e.getMessage(), false);
        }
    }

}
