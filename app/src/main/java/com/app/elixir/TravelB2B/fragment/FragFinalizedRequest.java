package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptfinalizedRequest;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListeners;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoFinalizeReq;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.utils.URLS;
import com.app.elixir.TravelB2B.view.ViewAgentProfile;
import com.app.elixir.TravelB2B.view.ViewChat;
import com.app.elixir.TravelB2B.view.ViewCommonDeatilView;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by NetSupport on 05-06-2017.
 */

public class FragFinalizedRequest extends Fragment implements View.OnTouchListener {

    private static final String TAG = "FragFinalizedRequest";
    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    private RecyclerView mRecyclerView;
    adptfinalizedRequest mAdapter;
    private PopupWindow mPopupWindow;
    CoordinatorLayout layoutrootView;
    ArrayList<pojoFinalizeReq> pojoFinalizeReqArrayList;
    FloatingActionButton myFab;
    //Filter Variable

    private int dayOfMonth1;
    private int month1;
    private int year1;

    AlertDialog alertDialog;

    CharSequence[] values = {"Total Budget (High To Low)", "Total Budget (Low To High) ", "Request Type"};
    AlertDialog levelDialog;
    Boolean wantToCloseDialog1 = false;

    Boolean wantToCloseDialog = false;

    EditText edtStartDate;
    EditText edtEndDate;
    EditText edtRefId;
    Spinner spinnerRefType, spinnerBudget;
    //new filter fields
    AutoCompleteTextView spinnerPCity, spinnerDCity, spinnerChatWid;
    RelativeLayout spinnerQuotedprice;
    EditText edtMembers;
    CheckBox checkboxFollow, checkboxShareDetail;
    private String sortItemPos = "";


    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.finalized_requests));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragfinlizerequest, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.finalized_requests));
        setHasOptionsMenu(true);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {


        layoutrootView = (CoordinatorLayout) rootView.findViewById(R.id.rootview);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));
        pojoFinalizeReqArrayList = new ArrayList<>();

        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showFilter();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    //myFab.hide();
                } else {
                    //  myFab.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });
        myFab.hide();
        mAdapter = new adptfinalizedRequest(thisActivity, pojoFinalizeReqArrayList);

        mAdapter.SetOnItemClickListener(new OnItemClickListeners() {
            @Override
            public void onItemClick(String value, String value1, String value2, String value3) {

                if (value.equals("detail")) {


                    Intent intent = new Intent(thisActivity, ViewCommonDeatilView.class);
                    intent.putExtra("refId", value1);
                    intent.putExtra("reqtype", value3);
                    intent.putExtra("title", getString(R.string.finalized_requests));
                    CM.startActivity(intent, thisActivity);

                } else if (value.equals("chat")) {

                    Intent intent = new Intent(thisActivity, ViewChat.class);
                    intent.putExtra("refId", value1);
                    intent.putExtra("chatUserId", value2);

                    CM.startActivity(intent, thisActivity);


                } else if (value.equals("userdetail")) {
                    Intent intent = new Intent(thisActivity, ViewAgentProfile.class);
                    intent.putExtra("userId", value1);
                    CM.startActivity(intent, thisActivity);
                } else {

                    showRating(CM.getSp(thisActivity, CV.PrefID, "").toString(), value2, value3);
                }


            }
        });

        if (CM.isInternetAvailable(thisActivity)) {
            webFinalizeRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), "", "", "", "", "", "", "");
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(false);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


    }

   /* @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.cartMenu);
        item.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void showPopup(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to unblock this user?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logo3).show();
    }


    public void showPopupForTestimonial() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(thisActivity);
        // dialog.setTitle(getString(R.string.app_name));
        //dialog.setIcon(R.drawable.logo3);
        dialog.setCancelable(false);
        dialog.setView(R.layout.custom_layout);
        dialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    //String userid, String reqid, String reqType, String reqId, String budget
    public void webFinalizeRequest(String userid, String reqid, String reqType, String reqId, String budget, String startDate, String endDate, String member, String sort) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getFinalizeReq(v, userid, reqid, reqType, reqId, budget, startDate, endDate, member, sort, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getFinalizeRequest(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(thisActivity)) {
                        CM.showPopupCommonValidation(thisActivity, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getFinalizeRequest(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONArray jsonArray = new JSONArray(jsonObject.optString("response_object").toString());
                    pojoFinalizeReqArrayList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        pojoFinalizeReq pojoMyResponse = new pojoFinalizeReq();
                        pojoMyResponse.setId(jsonArray.getJSONObject(i).optString("user_id"));
                        pojoMyResponse.setAdult(jsonArray.getJSONObject(i).optString("adult"));
                        pojoMyResponse.setCategory_id(jsonArray.getJSONObject(i).optString("category_id"));
                        pojoMyResponse.setChildren(jsonArray.getJSONObject(i).optString("children"));
                        pojoMyResponse.setReference_id(jsonArray.getJSONObject(i).optString("reference_id"));
                        pojoMyResponse.setTotal_budget(jsonArray.getJSONObject(i).optString("total_budget"));
                        pojoMyResponse.setRequest_id(jsonArray.getJSONObject(i).optString("id"));
                        pojoMyResponse.setStart_date(jsonArray.getJSONObject(i).get("start_date").toString());
                        pojoMyResponse.setEnd_date(jsonArray.getJSONObject(i).get("end_date").toString());
                        pojoMyResponse.setCheck_in(jsonArray.getJSONObject(i).get("check_in").toString());
                        pojoMyResponse.setCheck_out(jsonArray.getJSONObject(i).get("check_out").toString());
                        JSONArray jsonArray1 = new JSONArray(jsonArray.getJSONObject(i).optString("responses"));

                        if (jsonArray1.length() > 0) {
                            JSONObject jsonObject2 = new JSONObject(jsonArray1.getJSONObject(0).getString("user"));
                            pojoMyResponse.setUserName(jsonObject2.optString("first_name") + " " + jsonObject2.optString("last_name"));
                            pojoMyResponse.setQuotation_price(jsonArray1.getJSONObject(0).getString("quotation_price"));

                        }
                        pojoFinalizeReqArrayList.add(pojoMyResponse);

                    }
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.invalidate();
                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }


    public void showFilter() {
        LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.filter, (ViewGroup) thisActivity.findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity)
                .setView(layout);
        builder.setTitle("Filter By:");
        SearchView searchView = (SearchView) layout.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by name, email, mobile");
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) searchView.findViewById(id);
        Typeface myCustomFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
        searchText.setTypeface(myCustomFont);
        builder.setIcon(R.drawable.logo3);

        Spinner spinner = (Spinner) layout.findViewById(R.id.spinner);
        final String[] cat = getResources().getStringArray(R.array.catArray);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(thisActivity, R.layout.support_simple_spinner_dropdown_item, cat) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.WHITE);
                v.setBackgroundColor(Color.parseColor("#1295a2"));

                return v;
            }
        };
        spinner.setAdapter(langAdapter);

        Spinner spinner1 = (Spinner) layout.findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> langAdapter1 = new ArrayAdapter<CharSequence>(thisActivity, R.layout.support_simple_spinner_dropdown_item, cat) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.WHITE);
                v.setBackgroundColor(Color.parseColor("#1295a2"));

                return v;
            }
        };
        spinner1.setAdapter(langAdapter1);


        Spinner spinner2 = (Spinner) layout.findViewById(R.id.spinner3);

        ArrayAdapter<CharSequence> langAdapter2 = new ArrayAdapter<CharSequence>(thisActivity, R.layout.support_simple_spinner_dropdown_item, cat) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.WHITE);
                v.setBackgroundColor(Color.parseColor("#1295a2"));

                return v;
            }
        };
        spinner2.setAdapter(langAdapter2);


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

    public void showRating(final String userId, final String profileuID, String userNm) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(thisActivity);
        LayoutInflater inflater = thisActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rating, null);
        dialogBuilder.setView(dialogView);


        final EditText edtComment = (EditText) dialogView.findViewById(R.id.edtDis);
        final RatingBar Ratingbar = (RatingBar) dialogView.findViewById(R.id.ratingBar);
        final MtplTextView userName = (MtplTextView) dialogView.findViewById(R.id.txtUserName);

        userName.setText(userNm);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                wantToCloseDialog = true;

            }
        });
        dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Do stuff, possibly set wantToCloseDialog to true then...
                // if (wantToCloseDialog)
                //

                String Comment;
                float Rating;

                Comment = edtComment.getText().toString();
                Rating = Ratingbar.getRating();


                if (!Comment.matches("")) {
                    if (Rating > 0) {

                        alertDialog.dismiss();
                        webReview(userId, profileuID, "" + Rating, "0", Comment, "");
                    } else {
                        CM.showToast("select Rating", thisActivity);
                    }
                } else {
                    CM.showToast("Enter Comment", thisActivity);
                }

                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }

    public void webReview(String id, String profileuID, String rating, String status, String comment, String userName) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getReview(v, id, profileuID, rating, status, comment, userName, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getReview(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(thisActivity)) {
                        CM.showPopupCommonValidation(thisActivity, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getReview(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    CM.showToast((getString(R.string.review_submit)), thisActivity);

                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }

    public static void getReview(VolleyIntialization vollyInit, String userId, String prouserid, String rating, String staus, String comment, String userName, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.ADDTESTIMONIALAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userId);
        params.put(CV.PROFILEUSER_ID, prouserid);
        params.put(CV.RATING, rating);
        params.put(CV.STATUS, staus);
        params.put(CV.COMMENT, comment);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.noti).setVisible(false);
        menu.findItem(R.id.cartMenu).setVisible(false);
        menu.findItem(R.id.sort).setVisible(true);
        menu.findItem(R.id.filter).setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                showFilterPopup();
                break;
            case R.id.sort:
                showDialogSort();
                break;
        }
        return false;
    }

    public void showFilterPopup() {
        LayoutInflater inflater1 = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout1 = inflater1.inflate(R.layout.popup_filter, (ViewGroup) thisActivity.findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity).setView(layout1);
        builder.setTitle("Filter By:");

        SearchView searchView = (SearchView) layout1.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by Name");
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) searchView.findViewById(id);
        Typeface myCustomFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
        searchText.setTypeface(myCustomFont);
        builder.setIcon(R.drawable.logonewnew);

        spinnerBudget = (Spinner) layout1.findViewById(R.id.spinnerbudget);
        edtStartDate = (EditText) layout1.findViewById(R.id.edtstartdate1);
        edtStartDate.setOnTouchListener(this);
        edtEndDate = (EditText) layout1.findViewById(R.id.edtenddate1);
        edtEndDate.setOnTouchListener(this);
        edtRefId = (EditText) layout1.findViewById(R.id.edtrefid1);
        spinnerRefType = (Spinner) layout1.findViewById(R.id.spinnerreftype);

        //new

        spinnerPCity = (AutoCompleteTextView) layout1.findViewById(R.id.pcityspinner);
        spinnerDCity = (AutoCompleteTextView) layout1.findViewById(R.id.dcityspinner);
        edtMembers = (EditText) layout1.findViewById(R.id.edtmember);
        checkboxFollow = (CheckBox) layout1.findViewById(R.id.followimgcheckbox);
        checkboxShareDetail = (CheckBox) layout1.findViewById(R.id.sdetailcheckbox);
        spinnerChatWid = (AutoCompleteTextView) layout1.findViewById(R.id.chatwidspinner);
        spinnerQuotedprice = (RelativeLayout) layout1.findViewById(R.id.priceroot);

        //hide show new fields
        searchView.setVisibility(View.GONE);
        spinnerPCity.setVisibility(View.GONE);
        spinnerDCity.setVisibility(View.GONE);
        edtMembers.setVisibility(View.VISIBLE);
        spinnerChatWid.setVisibility(View.GONE);
        checkboxFollow.setVisibility(View.GONE);
        checkboxShareDetail.setVisibility(View.GONE);
        spinnerQuotedprice.setVisibility(View.GONE);


        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String reqType, reqId, budgetv, startDate, endDate;
                //new
                String member;

                reqType = spinnerRefType.getSelectedItem().toString();
                reqId = edtRefId.getText().toString();
                budgetv = spinnerBudget.getSelectedItem().toString();
                startDate = edtStartDate.getText().toString();
                endDate = edtEndDate.getText().toString();
                //new
                member = edtMembers.getText().toString();

                if (reqType.equals("Select Package")) {
                    reqType = "";
                }
                if (budgetv.equals("Select Budget")) {
                    budgetv = "";
                }
                //String userid, String reqid, String reqType, String reqId, String budget, String startDate, String endDate, String member
                webFinalizeRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), CM.getReqTypeRev(reqType), reqId, budgetv, startDate, endDate, member, "");

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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        switch (view.getId()) {
            case R.id.edtstartdate1:

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (edtStartDate.getRight() - edtStartDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(edtStartDate);
                        return true;
                    }
                }

                break;
            case R.id.edtenddate1:
                if (!edtStartDate.getText().toString().equals("")) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (motionEvent.getRawX() >= (edtEndDate.getRight() - edtEndDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            checkOut(edtEndDate);
                            return true;
                        }
                    }
                } else {
                    CM.showToast("Select start date first", thisActivity);
                }

                break;
        }
        return false;
    }

    public void checkIn(final EditText ed) {
        Calendar now = Calendar.getInstance();
        Log.i(ContentValues.TAG, "onTouch:" + ed.getId());
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(null,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        try {


            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            dpd.setOnDateSetListener(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


                    Log.i(ContentValues.TAG, "onDateSet: ");
                    dayOfMonth1 = dayOfMonth;
                    month1 = monthOfYear;
                    year1 = year;

                    int month = monthOfYear + 1;
                    if (edtStartDate != null) {


                    /*dayOfMonth1 = dayOfMonth;
                    month1 = month;
                    year1 = year;*/
                        //edtStartDate.setText(month + "/" + dayOfMonth + "/" + year);
                        edtStartDate.setText(dayOfMonth + "/" + month + "/" + year);

                        edtStartDate.setSelection(edtStartDate.getText().length());
                    }

                }
            });

        } catch (Exception e) {
            e.getMessage();

        }


    }

    public void checkOut(final EditText edt) {

        Calendar now = Calendar.getInstance();
        Log.i(ContentValues.TAG, "onTouch:" + edt.getId());
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(null,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        try {
            Calendar now1 = Calendar.getInstance();
            now1.set(year1, month1, dayOfMonth1);// you can pass your custom date
            dpd.setMinDate(now1);

            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


            LocalDate monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);


            dpd.setOnDateSetListener(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


                    Log.i(ContentValues.TAG, "onDateSet: ");

                    int month = monthOfYear + 1;
                    if (edt != null) {
                        //edt.setText(month + "/" + dayOfMonth + "/" + year);
                        edt.setText(dayOfMonth + "/" + month + "/" + year);

                    }

                }
            });

        } catch (Exception e) {
            e.getMessage();

        }
    }


    public void showDialogSort() {


        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity,
                R.style.MyDialogTheme);
        builder.setTitle("Sorting");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch (item) {
                    case 0:
                        sortItemPos = "0";
                        wantToCloseDialog1 = true;
                        break;
                    case 1:
                        sortItemPos = "1";
                        wantToCloseDialog1 = true;
                        break;
                    case 2:
                        sortItemPos = "2";
                        wantToCloseDialog1 = true;
                        break;
                    case 3:
                        sortItemPos = "3";
                        wantToCloseDialog1 = true;
                        break;
                    case 4:
                        sortItemPos = "4";
                        wantToCloseDialog1 = true;
                        break;
                    default:
                        wantToCloseDialog1 = false;
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
                if (wantToCloseDialog1)
                    webFinalizeRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), "", "", "", "", "", "", setSort(sortItemPos));

                levelDialog.dismiss();
                wantToCloseDialog1 = false;

                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });


    }
    //CharSequence[] values = {"Total Budget (High To Low)", "Total Budget (Low To High) ", "Request Type"};

    /* requesttype
            totalbudgetlh
    totalbudgethl*/
    public String setSort(String pos) {
        String sortItem = "";
        switch (pos) {
            case "0":
                sortItem = "totalbudgethl";
                break;
            case "1":
                sortItem = "totalbudgetlh";
                break;
            case "3":
                sortItem = "requesttype";
                break;
        }

        return sortItem;
    }
}
