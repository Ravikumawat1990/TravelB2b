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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptChatWith;
import com.app.elixir.TravelB2B.adapter.adptCheckResponse;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListeners;
import com.app.elixir.TravelB2B.model.pojoUserChat;
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
    String chatUserId;
    //Filter Variable
    //  RelativeLayout edtStartDate;
    // RelativeLayout edtEndDate;
    RelativeLayout priceRoot;
    // EditText edtRefId;
    EditText edtPrice;
    // RelativeLayout spinnerRefType;
    Spinner spinnerBudget, spinnerPriceQuot;
    // String ref_Id;
    boolean aBoolean = false;
    MtplTextView room1, room2, room3;
    MtplTextView childWithbed, childWithoutbed, meal;
    ArrayList<pojoUserChat> pojoUserChatList;
    //filter
    RelativeLayout edtStartDate, spinnerRefType;
    RelativeLayout edtEndDate;
    EditText edtRefId;
    //Spinner  spinnerBudget;
    //new filter fields
    Spinner spinnerQuotedprice;
    AutoCompleteTextView spinnerPCity, spinnerDCity, spinnerChatWid;
    RelativeLayout edtMembers;
    CheckBox checkboxFollow, checkboxShareDetail;
    String ref_Id;
    CharSequence[] values = {"Total Budget (High To Low)", "Total Budget (Low To High) ", "Quoted Price (High To Low)", "Quoted Price (Low To High)", "Chats (High To Low)", "Chats (Low To High)", "Agent Name (A To Z)", "Agent Name (Z To A)"};
    AlertDialog levelDialog;
    Boolean wantToCloseDialog = false;
    private String sortItemPos = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_check_response);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.checkResp));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        aBoolean = false;

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
        if (CM.isInternetAvailable(ViewCheckResponse.this)) {
            webCheckResponse(referenceId, "", "", "", "", CV.ASC, "", "", "", "", "");
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
        }


        initView();
    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ViewCheckResponse.this));
        btnDetail = (MtplButton) findViewById(R.id.btnDetail);

        resposneArrayList = new ArrayList<>();
        mAdapter = new adptCheckResponse(ViewCheckResponse.this, resposneArrayList);
        pojoUserChatList = new ArrayList<>();

        mAdapter.SetOnItemClickListener(new OnItemClickListeners() {
            @Override
            public void onItemClick(String value, String value1, String value2, String value3) {
                if (value.equals("chat")) {

                    Intent intent = new Intent(ViewCheckResponse.this, ViewChat.class);
                    intent.putExtra("chatUserId", value1);
                    intent.putExtra("refId", value2);
                    CM.startActivity(intent, ViewCheckResponse.this);

                } else if (value.equals("share")) {

                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        showPopup(ViewCheckResponse.this, "Are you sure you want to share your details,with this user?", value, value1, value2, "");
                    } else {
                        CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                    }

                } else if (value.equals("btnAccept")) {

                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        showPopup(ViewCheckResponse.this, "Are you sure you want to accept this offer?", value, value1, value2, value3);
                    } else {
                        CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                    }

                } else if (value.equals("rate")) {

                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        webFollow(CM.getSp(ViewCheckResponse.this, CV.PrefID, "").toString(), value2);
                    } else {
                        CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                    }


                } else if (value.equals("block")) {


                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        showPopup(ViewCheckResponse.this, "Are you sure you want to  block this user?", value, value1, value2, "");
                    } else {
                        CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                    }


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

                showFilterPopup();
                return true;
            case R.id.sort:
                showDialogSort();

              /*  if (aBoolean) {
                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        //webCheckResponse(referenceId, "", "", "", "", CV.ASC, "", "", "", "", "");
                        showDialogSort();
                    } else {
                        CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                    }
                    aBoolean = false;
                } else {
                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        webCheckResponse(referenceId, "", "", "", "", CV.DESC, "", "", "", "", "");
                    } else {
                        CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                    }
                    aBoolean = true;

                }*/


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   /* public void showPopup() {
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
    }*/

    /*public void ShowRatingDialog() {

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
*/

    //ViewCheckResponse.this, "Are you sure you want to accept this offer?", value, value1, value2, value3
    public void showPopup(Context context, String msg, final String typeNew, final String blockId, final String value2, final String value3) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (typeNew.equals("block")) {

                            getBlockUser(CM.getSp(ViewCheckResponse.this, CV.PrefID, "").toString(), blockId);

                        } else if (typeNew.equals("btnAccept")) {

                            if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                                //webCheckResponse(referenceId, "", "", "", "", CV.ASC, "", "", "", "");
                                //  webAcceptOffer("", "", CM.getSp(ViewCheckResponse.this, CV.PrefID, "").toString());
                                // String resid, final String reqid, String userid

                                webAcceptOffer(blockId, value2, CM.getSp(ViewCheckResponse.this, CV.PrefID, "").toString());

                            } else {
                                CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                            }


                        } else if (typeNew.equals("share")) {

                            webShareDetail(CM.getSp(ViewCheckResponse.this, CV.PrefID, "").toString(), value2);
                        }

                    }
                }).

                setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).

                setIcon(R.drawable.logo1).

                show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDetail:
                //showDetail();
                Intent intent = new Intent(ViewCheckResponse.this, ViewCommonDeatilView.class);
                intent.putExtra("refId", referenceId);
                String s = (String) CM.getSp(ViewCheckResponse.this, "deatilreqtype", "");
                CM.setSp(ViewCheckResponse.this, "deatilreqtype", null);
                intent.putExtra("reqtype", s);
                intent.putExtra("title", getString(R.string.checkResp));
                CM.startActivity(intent, ViewCheckResponse.this);
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
        //vehicle = (MtplTextView) dialogView.findViewById(R.id.txtVehicle);
        finalState = (MtplTextView) dialogView.findViewById(R.id.txtDestState);
        finalCity = (MtplTextView) dialogView.findViewById(R.id.txtDestCity);
        comment = (MtplTextView) dialogView.findViewById(R.id.txtCmt);


        room1 = (MtplTextView) dialogView.findViewById(R.id.txtSingle);
        room2 = (MtplTextView) dialogView.findViewById(R.id.txtDouble);
        room3 = (MtplTextView) dialogView.findViewById(R.id.txtTriple);


        childWithbed = (MtplTextView) dialogView.findViewById(R.id.txtChildWithbed);
        childWithoutbed = (MtplTextView) dialogView.findViewById(R.id.txtChildWithoutbed);
        meal = (MtplTextView) dialogView.findViewById(R.id.txtMeal);


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


    public void webCheckResponse(String reqid, String refid, String budget, String price, String agentName, String order, String quotPrice, String chatwith, String follow, String sharedetail, String sort) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCheckResponse.this, true, true);
            WebService.getCheckResposne(v, reqid, refid, budget, price, agentName, order, quotPrice, chatwith, follow, sharedetail, sort, new OnVolleyHandler() {
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
                    final JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object").toString());

                    refId.setText(jsonObject1.optString("id"));
                    budget.setText(getString(R.string.rsSymbol) + " " + jsonObject1.optString("total_budget"));
                    members.setText(jsonObject1.optString("adult"));
                    childres.setText(jsonObject1.optString("children"));
                    comment.setText(jsonObject1.optString("comment"));
                    // vehicle.setText("");
                    startdate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("check_in")));
                    enddate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonObject1.optString("check_out")));
                    pickupLocation.setText(jsonObject1.optString("locality"));

                    //   MtplTextView room1, room2, room3;
                    // MtplTextView childWithbed, childWithoutbed, meal;


                    room1.setText(jsonObject1.optString("room1"));
                    room2.setText(jsonObject1.optString("room2"));
                    room3.setText(jsonObject1.optString("room3"));
                    childWithbed.setText(jsonObject1.optString("child_with_bed"));
                    childWithoutbed.setText(jsonObject1.optString("child_without_bed"));
                    meal.setText(CM.getMealPlane(jsonObject1.optString("meal_plan")));


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (jsonObject1.optString("state_id").equals("") || jsonObject1.optString("state_id").equals("0") || jsonObject1.optString("state_id").toString().equals("null")) {

                            } else {

                                if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                                    webState(jsonObject1.optString("state_id"), "1");
                                } else {
                                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                                }

                            }

                        }
                    }, 100);

                    final Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (jsonObject1.optString("final_state").equals("") || jsonObject1.optString("final_state").equals("0") || jsonObject1.optString("final_state").toString().equals("null")) {

                            } else {
                                if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                                    webState(jsonObject1.optString("final_state"), "2");
                                } else {
                                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                                }


                            }


                        }
                    }, 200);


                    final Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (jsonObject1.optString("city_id").equals("") || jsonObject1.optString("city_id").equals("0") || jsonObject1.optString("city_id").toString().equals("null")) {

                            } else {
                                if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                                    webCity(jsonObject1.optString("city_id"), "1");
                                } else {
                                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                                }


                            }

                        }
                    }, 400);

                    final Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (jsonObject1.optString("final_city").equals("") || jsonObject1.optString("final_city").equals("0") || jsonObject1.optString("final_city").toString().equals("null")) {

                            } else {

                                if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                                    webCity(jsonObject1.optString("final_city"), "2");
                                } else {
                                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                                }

                            }

                        }
                    }, 500);


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


                    JSONArray jsonArray = new JSONArray(jsonObject.optString("response_object").toString());
                    resposneArrayList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        pojoCheckResposne checkResposne = new pojoCheckResposne();
                        checkResposne.setQuotation_price(jsonArray.getJSONObject(i).optString("quotation_price"));
                        checkResposne.setId(jsonArray.getJSONObject(i).optString("id"));
                        checkResposne.setUser_id(jsonArray.getJSONObject(i).optString("user_id"));
                        checkResposne.setRequest_id(jsonArray.getJSONObject(i).optString("request_id"));
                        checkResposne.setComment(jsonArray.getJSONObject(i).optString("comment"));
                        JSONObject jsonObject2 = new JSONObject(jsonArray.getJSONObject(i).optString("request").toString());
                        checkResposne.setReference_id(jsonObject2.optString("reference_id"));
                        checkResposne.setResponse_id(jsonObject2.optString("response_id"));
                        checkResposne.setTotal_budget(jsonObject2.optString("total_budget"));
                        JSONObject jsonObject3 = new JSONObject(jsonArray.getJSONObject(i).optString("user").toString());
                        checkResposne.setFirst_name(jsonObject3.optString("first_name"));
                        checkResposne.setLast_name(jsonObject3.optString("last_name"));
                        checkResposne.setFollow_id(jsonObject3.optString("id"));
                        checkResposne.setReference_id(jsonObject2.optString("reference_id"));
                        ref_Id = jsonObject2.optString("reference_id");
                        resposneArrayList.add(checkResposne);

                    }
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.invalidate();
                    JSONArray jsonChatArray1 = new JSONArray(jsonObject.optString("chat_users").toString());
                    pojoUserChatList.clear();
                    if (jsonChatArray1.length() != 0) {

                        for (int j = 0; j < jsonChatArray1.length(); j++) {
                            pojoUserChat chatPojo = new pojoUserChat();
                            chatPojo.setUserid(jsonChatArray1.getJSONObject(j).get("id").toString());
                            chatPojo.setUsername(jsonChatArray1.getJSONObject(j).get("first_name").toString() + " " + jsonChatArray1.getJSONObject(j).get("last_name").toString());
                            pojoUserChatList.add(chatPojo);
                        }
                    }

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


    public void getBlockUser(String userId, String blockUId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCheckResponse.this, true, true);
            WebService.getBlockUser(v, userId, blockUId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseBlockUser(response);

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

    private void getResponseBlockUser(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    jsonObject.optString("response_object").toString();
                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, e.getMessage(), false);
        }
    }


    public void webCity(String cityId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCheckResponse.this, true, true);
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
                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        CM.showPopupCommonValidation(ViewCheckResponse.this, error, false);
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
            CM.showPopupCommonValidation(ViewCheckResponse.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
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
                    CM.showToast(jsonObject.optString("msg"), ViewCheckResponse.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, e.getMessage(), false);
        }
    }

    public void webState(String stateId, final String type) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCheckResponse.this, true, true);
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
                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        CM.showPopupCommonValidation(ViewCheckResponse.this, error, false);
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
            CM.showPopupCommonValidation(ViewCheckResponse.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
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
                    CM.showToast(jsonObject.optString("msg"), ViewCheckResponse.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, e.getMessage(), false);
        }
    }

    public void webAcceptOffer(String resid, final String reqid, String userid) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCheckResponse.this, true, true);
            WebService.getAcceptOffer(v, resid, reqid, userid, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getAcceptOffer(response);

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

    public void webShareDetail(String userId, final String reqId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCheckResponse.this, true, true);
            WebService.getShareDetail(v, userId, reqId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    //  getState(response, type);

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

    public void showRating(final String userId, final String profileuID) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ViewCheckResponse.this);
        LayoutInflater inflater = ViewCheckResponse.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rating, null);
        dialogBuilder.setView(dialogView);


        final EditText edtComment = (EditText) dialogView.findViewById(R.id.edtDis);
        final RatingBar Ratingbar = (RatingBar) dialogView.findViewById(R.id.ratingBar);


        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String Comment;
                float Rating;

                Comment = edtComment.getText().toString();
                Rating = Ratingbar.getRating();

                if (!Comment.matches("")) {
                    if (Rating > 0) {


                        if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                            webReview(userId, profileuID, "" + Rating, "0", Comment, "");
                        } else {
                            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                        }


                    } else {
                        CM.showToast("select Rating", ViewCheckResponse.this);
                    }
                } else {
                    CM.showToast("Enter Comment", ViewCheckResponse.this);
                }

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


    public void webReview(String id, String profileuID, String rating, String status, String comment, String userName) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCheckResponse.this, true, true);
            WebService.getReview(v, id, profileuID, rating, status, comment, userName, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (ViewCheckResponse.this.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getReview(response);

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


    private void getAcceptOffer(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    CM.showToast(getString(R.string.offerACT), ViewCheckResponse.this);
                    if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                        webCheckResponse(referenceId, "", "", "", "", CV.ASC, "", "", "", "", "");
                    } else {
                        CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                    }


                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, e.getMessage(), false);
        }
    }


    private void getReview(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    CM.showToast((getString(R.string.review_submit)), ViewCheckResponse.this);

                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, e.getMessage(), false);
        }
    }

    public void webFollow(String userId, String followid) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCheckResponse.this, true, true);
            WebService.getFollow(v, userId, followid, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (ViewCheckResponse.this.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getFollowRes(response);

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

    private void getFollowRes(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (!jsonObject.optString("response_object").toString().equals("null")) {
                        CM.showToast(getString(R.string.follow_success), ViewCheckResponse.this);

                        if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                            webCheckResponse(referenceId, "", "", "", "", CV.ASC, "", "", "", "", "");
                        } else {
                            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                        }


                    }

                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCheckResponse.this, e.getMessage(), false);
        }
    }


    public void showFilterPopup() {
        LayoutInflater inflater1 = (LayoutInflater) ViewCheckResponse.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout1 = inflater1.inflate(R.layout.popup_filter, (ViewGroup) ViewCheckResponse.this.findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewCheckResponse.this).setView(layout1);
        builder.setTitle("Filter By:");

        final SearchView searchView = (SearchView) layout1.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by Name");
        searchView.setVisibility(View.VISIBLE);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final TextView searchText = (TextView) searchView.findViewById(id);
        Typeface myCustomFont = Typeface.createFromAsset(ViewCheckResponse.this.getAssets(), getString(R.string.fontface_roboto_light));
        searchText.setTypeface(myCustomFont);
        builder.setIcon(R.drawable.logonewnew);

        spinnerBudget = (Spinner) layout1.findViewById(R.id.spinnerbudget);
        edtStartDate = (RelativeLayout) layout1.findViewById(R.id.startdateroot);
        edtStartDate.setVisibility(View.GONE);
        //edtStartDate.setOnTouchListener(this);
        edtEndDate = (RelativeLayout) layout1.findViewById(R.id.enddateroot);
        edtEndDate.setVisibility(View.GONE);
        //edtEndDate.setOnTouchListener(this);
        edtRefId = (EditText) layout1.findViewById(R.id.edtrefid1);
        spinnerRefType = (RelativeLayout) layout1.findViewById(R.id.typeroot);
        spinnerRefType.setVisibility(View.GONE);

        //new
        spinnerPCity = (AutoCompleteTextView) layout1.findViewById(R.id.pcityspinner);
        spinnerDCity = (AutoCompleteTextView) layout1.findViewById(R.id.dcityspinner);
        edtMembers = (RelativeLayout) layout1.findViewById(R.id.memberroot);
        checkboxFollow = (CheckBox) layout1.findViewById(R.id.followimgcheckbox);
        checkboxShareDetail = (CheckBox) layout1.findViewById(R.id.sdetailcheckbox);
        spinnerChatWid = (AutoCompleteTextView) layout1.findViewById(R.id.chatwidspinner);
        spinnerQuotedprice = (Spinner) layout1.findViewById(R.id.spinnerPriceQuot);


        //hide show new fields
        spinnerPCity.setVisibility(View.GONE);
        spinnerDCity.setVisibility(View.GONE);
        edtMembers.setVisibility(View.GONE);
        checkboxFollow.setVisibility(View.VISIBLE);
        checkboxShareDetail.setVisibility(View.VISIBLE);

        chatUserId = "";

        adptChatWith adptchat1 = new adptChatWith(ViewCheckResponse.this, R.layout.conntylayout, R.id.textViewSpinner, pojoUserChatList);
        spinnerChatWid.setThreshold(3);
        spinnerChatWid.setAdapter(adptchat1);

        spinnerChatWid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chatUserId = pojoUserChatList.get(i).getUserid();
            }
        });


        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String quotPrice, reqId, budgetv, name, pricetv, nameAgent;
                //new
                String chatwithStr, followStr, sharesdetailStr;


                quotPrice = spinnerQuotedprice.getSelectedItem().toString();
                budgetv = spinnerBudget.getSelectedItem().toString();
                nameAgent = searchView.getQuery().toString();

                //new
                chatwithStr = spinnerChatWid.getText().toString();
                if (checkboxFollow.isChecked()) {
                    followStr = "1";
                } else {
                    followStr = "0";
                }

                if (checkboxShareDetail.isChecked()) {
                    sharesdetailStr = "1";
                } else {
                    sharesdetailStr = "0";
                }

                if (budgetv.equals("Select Budget")) {
                    budgetv = "";
                }
                if (quotPrice.equals("Select Quoted Price")) {
                    quotPrice = "";
                }

                if (CM.isInternetAvailable(ViewCheckResponse.this)) {
                    //String reqid, String refid, String budget, String price, String agentName, String order, String quotPrice,String chatwith, String follow, String sharedetail
                    webCheckResponse(referenceId, ref_Id, budgetv, "", nameAgent, CV.ASC, quotPrice, chatUserId, followStr, sharesdetailStr, "");
                } else {
                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewCheckResponse.this);
                }


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

    public void showDialogSort() {


        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewCheckResponse.this,
                R.style.MyDialogTheme);
        builder.setTitle("Sorting");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch (item) {
                    case 0:
                        sortItemPos = "0";
                        wantToCloseDialog = true;
                        break;
                    case 1:
                        sortItemPos = "1";
                        wantToCloseDialog = true;
                        break;
                    case 2:
                        sortItemPos = "2";
                        wantToCloseDialog = true;

                        break;
                    case 3:
                        sortItemPos = "3";
                        wantToCloseDialog = true;
                        break;
                    case 4:
                        sortItemPos = "4";
                        wantToCloseDialog = true;
                        break;
                    case 5:
                        sortItemPos = "5";
                        wantToCloseDialog = true;
                        break;
                    case 6:
                        sortItemPos = "6";
                        wantToCloseDialog = true;
                        break;
                    case 7:
                        sortItemPos = "7";
                        wantToCloseDialog = true;
                        break;


                }
                //levelDialog.dismiss();

            }
        });


        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        levelDialog = builder.create();
        levelDialog.show();
        levelDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Do stuff, possibly set wantToCloseDialog to true then...
                if (wantToCloseDialog)
                    levelDialog.dismiss();
                wantToCloseDialog = false;
                //webMyResponse(CM.getSp(thisActivity, CV.PrefID, "").toString(), "", "", "", "", "", "", "", "", "", "", "", "", setSort(sortItemPos));

                webCheckResponse(referenceId, "", "", "", "", CV.ASC, "", "", "", "", setSort(sortItemPos));


            }
        });


    }

    // CharSequence[] values = {"Total Budget (High To Low)", "Total Budget (Low To High) ", "Quoted Price (High To Low)", "Quoted Price (Low To High)", "Chats (High To Low)", "Chats (Low To High)", "Agent Name (A To Z)", "Agent Name (Z To A)"};

    public String setSort(String pos) {
        String sortItem = "";
        switch (pos) {
            case "0":
                sortItem = "totalbudgethl";
                break;
            case "1":
                sortItem = "totalbudgetlh";
                break;
            case "2":
                sortItem = "quotationhl";
                break;
            case "3":
                sortItem = "quotationlh";
                break;
            case "4":
                sortItem = "chatshl";
                break;
            case "5":
                sortItem = "chatslh";
                break;
            case "6":
                sortItem = "agentaz";
                break;
            case "7":
                sortItem = "agentza";
                break;

        }

        return sortItem;
    }

}
