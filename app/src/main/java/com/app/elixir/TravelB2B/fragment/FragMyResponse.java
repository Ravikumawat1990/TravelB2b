package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.AutocompleteAdapter;
import com.app.elixir.TravelB2B.adapter.adptChatWith;
import com.app.elixir.TravelB2B.adapter.adptMyResponse;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnAdapterItemClickListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.model.pojoUserChat;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoCity;
import com.app.elixir.TravelB2B.pojos.pojoMyResposne;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
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
import java.util.Date;
import java.util.Iterator;

import static android.content.ContentValues.TAG;

/**
 * Created by NetSupport on 01-06-2017.
 */

public class FragMyResponse extends Fragment implements View.OnTouchListener {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private Activity thisActivity;
    private adptMyResponse mAdapter;
    ArrayList<pojoMyResposne> pojoMyResposneArrayList;
    FloatingActionButton myFab;
    EditText edtStartDate;
    EditText edtEndDate;
    EditText edtRefId;
    Spinner spinnerRefType, spinnerBudget;
    int startDay;
    private String finaleDate;
    private int dayOfMonth1;
    private int month1;
    private int year1;
    String chatUserId;
    CharSequence[] values = {"Total Budget (High To Low)", "Total Budget (Low To High) ", "Agent Name (A To Z)", "Agent Name (Z To A)", "Request Type"};
    AlertDialog levelDialog;
    Boolean wantToCloseDialog = false;
    ArrayList<pojoUserChat> pojoUserChatList;
    AutoCompleteTextView spinnerPCity, spinnerDCity, spinnerChatWid;
    RelativeLayout spinnerQuotedprice;
    EditText edtMembers;
    TextView searchText;
    CheckBox checkboxFollow, checkboxShareDetail;
    String pickupCityId, destCityId;
    private ArrayList<pojoCity> pojoCities;

    ArrayList<String> cityStateArrayList;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLayoutManager;
    private String sortItemPos = "";

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.MyResponse));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_myresponse, container, false);
        Log.i(TAG, "onTabSelected: ");
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.MyResponse));
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(thisActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        pojoMyResposneArrayList = new ArrayList<>();
        mAdapter = new adptMyResponse(thisActivity, pojoMyResposneArrayList);
        setHasOptionsMenu(true);
        pojoCities = new ArrayList<>();
        parseJsonCity();
        pojoUserChatList = new ArrayList<>();

        mAdapter.SetOnItemClickListener(new OnAdapterItemClickListener() {
            @Override
            public void onItemClick(String key, String value1, String value2) {

                if (key.equals("detail")) {

                    Intent intent = new Intent(thisActivity, ViewCommonDeatilView.class);
                    intent.putExtra("refId", value1);
                    intent.putExtra("reqtype", value2);
                    intent.putExtra("title", getString(R.string.MyResponse));
                    CM.startActivity(intent, thisActivity);

                } else if (key.equals("userdetail")) {
                    Intent intent = new Intent(thisActivity, ViewAgentProfile.class);
                    intent.putExtra("userId", value1);
                    CM.startActivity(intent, thisActivity);
                } else if (key.equals("follow")) {

                    showPopup(thisActivity, value2);

                } else if (key.equals("chat")) {
                    Intent intent = new Intent(thisActivity, ViewChat.class);
                    intent.putExtra("refId", value1);
                    intent.putExtra("chatUserId", value2);
                    CM.startActivity(intent, thisActivity);
                }


            }
        });


        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        cityStateArrayList = new ArrayList<>();
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showPopup();
            }
        });

        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //mohit
                //change filterPopup
                //showPopup();
                showFilterPopup();
            }
        });
        myFab.setVisibility(View.GONE);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    // myFab.hide();
                } else {
                    //  myFab.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });


        if (CM.isInternetAvailable(thisActivity)) {
            webMyResponse(CM.getSp(thisActivity, CV.PrefID, "").toString(), "", "", "", "", "", "", "", "", "", "", "", "", "");
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(true);
    }


    public void showPopup() {
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
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showFilterPopup() {
        LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_filter, (ViewGroup) thisActivity.findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity)
                .setView(layout);
        builder.setTitle("Filter By:");

        final SearchView searchView = (SearchView) layout.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by Name");
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        searchText = (TextView) searchView.findViewById(id);
        Typeface myCustomFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
        searchText.setTypeface(myCustomFont);
        builder.setIcon(R.drawable.logonewnew);

        spinnerBudget = (Spinner) layout.findViewById(R.id.spinnerbudget);
        edtStartDate = (EditText) layout.findViewById(R.id.edtstartdate1);
        edtStartDate.setOnTouchListener(this);
        edtEndDate = (EditText) layout.findViewById(R.id.edtenddate1);
        edtEndDate.setOnTouchListener(this);
        edtRefId = (EditText) layout.findViewById(R.id.edtrefid1);
        spinnerRefType = (Spinner) layout.findViewById(R.id.spinnerreftype);

        //new
        spinnerPCity = (AutoCompleteTextView) layout.findViewById(R.id.pcityspinner);
        spinnerDCity = (AutoCompleteTextView) layout.findViewById(R.id.dcityspinner);
        edtMembers = (EditText) layout.findViewById(R.id.edtmember);
        checkboxFollow = (CheckBox) layout.findViewById(R.id.followimgcheckbox);
        checkboxShareDetail = (CheckBox) layout.findViewById(R.id.sdetailcheckbox);
        spinnerChatWid = (AutoCompleteTextView) layout.findViewById(R.id.chatwidspinner);
        spinnerQuotedprice = (RelativeLayout) layout.findViewById(R.id.priceroot);

        //hide show new fields
        searchView.setVisibility(View.VISIBLE);
        spinnerPCity.setVisibility(View.VISIBLE);
        spinnerDCity.setVisibility(View.VISIBLE);
        edtMembers.setVisibility(View.VISIBLE);
        spinnerChatWid.setVisibility(View.VISIBLE);
        checkboxFollow.setVisibility(View.VISIBLE);
        checkboxShareDetail.setVisibility(View.VISIBLE);
        spinnerQuotedprice.setVisibility(View.GONE);

        AutocompleteAdapter adptCountry1 = new AutocompleteAdapter(thisActivity, R.layout.conntylayout, R.id.textViewSpinner, pojoCities);
        spinnerPCity.setThreshold(3);
        spinnerPCity.setAdapter(adptCountry1);
        spinnerDCity.setThreshold(3);
        spinnerDCity.setAdapter(adptCountry1);
        pickupCityId = "";
        destCityId = "";
        chatUserId = "";
        spinnerPCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pickupCityId = pojoCities.get(i).getId();

            }
        });

        spinnerDCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                destCityId = pojoCities.get(i).getId();
            }
        });

        adptChatWith adptchat1 = new adptChatWith(thisActivity, R.layout.conntylayout, R.id.textViewSpinner, pojoUserChatList);
        spinnerChatWid.setThreshold(3);
        spinnerChatWid.setAdapter(adptchat1);

        spinnerChatWid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chatUserId = pojoUserChatList.get(i).getUserid();
            }
        });


        // String userid, String reqType, String reqId, String startDate, String endDate,
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String reqType, reqId, startDate, endDate, budgetv;
                //new
                String member, follow, shareDetail, agentName, chatWith;


                reqType = spinnerRefType.getSelectedItem().toString();
                reqId = edtRefId.getText().toString();
                startDate = edtStartDate.getText().toString();
                endDate = edtEndDate.getText().toString();
                budgetv = spinnerBudget.getSelectedItem().toString();

                //new
                member = edtMembers.getText().toString();
                agentName = searchView.getQuery().toString();
                chatWith = spinnerChatWid.getText().toString();

                if (checkboxFollow.isChecked()) {
                    follow = "1";
                } else {
                    follow = "0";
                }

                if (checkboxShareDetail.isChecked()) {
                    shareDetail = "1";
                } else {
                    shareDetail = "0";
                }

                if (reqType.equals("Select Package")) {
                    reqType = "";
                }
                if (budgetv.equals("Select Budget")) {
                    budgetv = "";
                }

                if (CM.isInternetAvailable(thisActivity)) {
                    //String userid, String reqType, String reqId, String startDate, String endDate, String budget, String agentname, String pcity, String dcity, String member, String chatwith, String follow, String sharedetail
                    webMyResponse(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getReqTypeRev(reqType), reqId, startDate, endDate, budgetv, agentName, pickupCityId, destCityId, member, chatUserId, follow, shareDetail, "");
                } else {
                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
                }


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        Log.i(TAG, "onTouch:" + ed.getId());
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(null,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        try {


            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            dpd.setOnDateSetListener(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


                    Log.i(TAG, "onDateSet: ");
                    dayOfMonth1 = dayOfMonth;
                    month1 = monthOfYear;
                    year1 = year;

                    int month = monthOfYear + 1;
                    if (edtStartDate != null) {


                    /*dayOfMonth1 = dayOfMonth;
                    month1 = month;
                    year1 = year;*/
                        edtStartDate.setText(month + "/" + dayOfMonth + "/" + year);
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
        Log.i(TAG, "onTouch:" + edt.getId());
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


                    Log.i(TAG, "onDateSet: ");

                    int month = monthOfYear + 1;
                    if (edt != null) {
                        edt.setText(month + "/" + dayOfMonth + "/" + year);
                    }

                }
            });

        } catch (Exception e) {
            e.getMessage();

        }
    }

    public static Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public void webMyResponse(String userid, String reqType, String reqId, String startDate, String endDate, String budget, String agentname, String pcity, String dcity, String member, String chatwith, String follow, String sharedetail, String sortItem) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getMyResponse(v, userid, reqType, reqId, startDate, endDate, budget, agentname, pcity, dcity, member, chatwith, follow, sharedetail, sortItem, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getMyResponse(response);

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

    private void getMyResponse(String response) {
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
                    //cityStateArrayList


                    pojoMyResposneArrayList.clear();
                    if (jsonArray.length() != 0) {

                        JSONObject jsonArray1 = new JSONObject(jsonObject.optString("citystate").toString());

                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("rating").toString());

                        JSONObject jsonObject11 = new JSONObject(jsonObject1.optString("rating").toString());
                        // JSONObject jsonObject2 = new JSONObject(jsonObject.optString("BusinessBuddies").toString());


                        ArrayList<String> buddyArray = new ArrayList<>();
                        if (jsonObject.optString("BusinessBuddies").toString().equals("[]")) {

                        } else {
                            JSONObject businessBuddiesObj = new JSONObject(jsonObject.optString("BusinessBuddies").toString());
                            Iterator<String> keys = businessBuddiesObj.keys();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                Log.i(TAG, "getMyResponse: " + key);
                                buddyArray.add(key);
                            }
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {

                            pojoMyResposne myResposne = new pojoMyResposne();
                            myResposne.setComment(jsonArray.getJSONObject(i).get("comment").toString());
                            myResposne.setRequest_id(jsonArray.getJSONObject(i).get("request_id").toString());
                            myResposne.setUserId(jsonArray.getJSONObject(i).get("user_id").toString());
                            myResposne.setChatStatus(jsonArray.getJSONObject(i).get("status").toString());
                            myResposne.setShareDetail(jsonArray.getJSONObject(i).get("is_details_shared").toString());
                            myResposne.setQuotation_price(jsonArray.getJSONObject(i).get("quotation_price").toString());
                            JSONObject jsonObjectReq = new JSONObject(jsonArray.getJSONObject(i).get("request").toString());
                            myResposne.setCategory_id(jsonObjectReq.optString("category_id").toString());
                            myResposne.setChatUserID(jsonObjectReq.optString("user_id").toString());
                            if (buddyArray.contains(jsonObjectReq.optString("user_id").toString())) {
                                Log.i("BusinessBuddies", "getMyResponse: BusinessBuddies  true");
                                myResposne.setIsBusinessBuddy("1");

                            } else {
                                Log.i("BusinessBuddies", "getMyResponse: BusinessBuddies false");
                                myResposne.setIsBusinessBuddy("0");

                            }

                            myResposne.setReference_id(jsonObjectReq.optString("reference_id").toString());
                            myResposne.setTotal_budget(jsonObjectReq.optString("total_budget").toString());
                            myResposne.setChildren(jsonObjectReq.optString("children").toString());
                            myResposne.setAdult(jsonObjectReq.optString("adult").toString());
                            myResposne.setRoom1(jsonObjectReq.optString("room1").toString());
                            myResposne.setRoom2(jsonObjectReq.optString("room2").toString());
                            myResposne.setRoom3(jsonObjectReq.optString("room3").toString());
                            myResposne.setChild_with_bed(jsonObjectReq.optString("child_with_bed").toString());
                            myResposne.setChild_without_bed(jsonObjectReq.optString("child_without_bed").toString());
                            myResposne.setHotel_rating(jsonObjectReq.optString("hotel_rating").toString());
                            myResposne.setHotel_category(jsonObjectReq.optString("hotel_category").toString());
                            myResposne.setMeal_plan(jsonObjectReq.optString("meal_plan").toString());
                            myResposne.setDestination_city(jsonArray1.optString(jsonArray.getJSONObject(i).get("id").toString()));
                            myResposne.setCheck_in(jsonObjectReq.optString("check_in").toString());
                            myResposne.setCheck_out(jsonObjectReq.optString("check_out").toString());
                            myResposne.setTransport_requirement(jsonObjectReq.optString("transport_requirement").toString());
                            myResposne.setPickup_city(jsonObjectReq.optString("pickup_city").toString());
                            myResposne.setPickup_state(jsonObjectReq.optString("pickup_state").toString());
                            myResposne.setPickup_country(jsonObjectReq.optString("pickup_country").toString());
                            myResposne.setPickup_locality(jsonObjectReq.optString("pickup_locality").toString());
                            myResposne.setCity_id(jsonObjectReq.optString("city_id").toString());
                            myResposne.setState_id(jsonObjectReq.optString("state_id").toString());
                            myResposne.setFinal_city(jsonObjectReq.optString("final_city").toString());
                            myResposne.setFinal_state(jsonObjectReq.optString("final_state").toString());
                            myResposne.setFinal_country(jsonObjectReq.optString("final_country").toString());
                            myResposne.setUserComment(jsonObjectReq.optString("comment").toString());
                            myResposne.setStart_date(jsonObjectReq.optString("start_date").toString());
                            myResposne.setEnd_date(jsonObjectReq.optString("end_date").toString());
                            JSONObject jsonObjectUser = new JSONObject(jsonObjectReq.get("user").toString());
                            myResposne.setCompany_name(jsonObjectUser.optString("company_name").toString());
                            myResposne.setFirst_name(jsonObjectUser.optString("first_name").toString());
                            myResposne.setLast_name(jsonObjectUser.optString("last_name").toString());
                            myResposne.setMobile_number(jsonObjectUser.optString("mobile_number").toString());
                            myResposne.setP_contact(jsonObjectUser.optString("p_contact").toString());
                            myResposne.setId(jsonObjectUser.optString("id").toString());
                            myResposne.setWeb_url(jsonObjectUser.optString("web_url").toString());
                            myResposne.setEmail(jsonObjectUser.optString("email").toString());


                            JSONArray jsonArray2 = new JSONArray(jsonObject11.optString(jsonArray.getJSONObject(i).get("id").toString()));
                            for (int i1 = 0; i1 < jsonArray2.length(); i1++) {
                                myResposne.setRating(jsonArray2.getJSONObject(i1).optString("average_rating"));

                            }

                            pojoMyResposneArrayList.add(myResposne);
                        }
                        pojoMyResposneArrayList.size();
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.invalidate();
                    } else {
                        pojoMyResposneArrayList.size();
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.invalidate();

                    }

                    JSONArray jsonChatArray1 = new JSONArray(jsonObject.optString("user_chats").toString());
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
                    CM.showToast(jsonObject.optString("msg"), thisActivity);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.noti).setVisible(false);
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


    public void webFollow(String userId, String followid) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getFollow(v, userId, followid, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getFollowRes(response);

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

    private void getFollowRes(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (!jsonObject.optString("response_object").toString().equals("null")) {
                        CM.showToast(getString(R.string.follow_success), thisActivity);
                        //   webCheckResponse(referenceId);
                    }

                    break;
                case "202":
                    CM.showToast(jsonObject.optString("response_object").toString(), thisActivity);
                    break;
                case "402":
                    CM.showToast(jsonObject.optString("response_object").toString(), thisActivity);
                    break;
                case "403":
                    CM.showToast(jsonObject.optString("response_object").toString(), thisActivity);
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }

    public void showPopup(Context context, final String value) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage(R.string.follow_user)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        webFollow(CM.getSp(thisActivity, CV.PrefID, "").toString(), value);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logonewnew).show();
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
                    default:

                        wantToCloseDialog = false;
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
                webMyResponse(CM.getSp(thisActivity, CV.PrefID, "").toString(), "", "", "", "", "", "", "", "", "", "", "", "", setSort(sortItemPos));


            }
        });


    }

    /*totalbudgetlh
            totalbudgethl
    agentaz
            agentza
    requesttype*/

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
                sortItem = "agentaz";
                break;
            case "3":
                sortItem = "agentza";
                break;
            case "4":
                sortItem = "requesttype";
                break;
        }

        return sortItem;
    }

    private void parseJsonCity() {
        String json = CM.getSp(thisActivity, "cities", "").toString();
        // ArrayList<pojoCity> pojoCities= CM.getSp(thisActivity, "cities", "").toString();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                pojoCity country = new pojoCity();
                country.setId(jsonArray.getJSONObject(i).getString("id"));
                country.setName(jsonArray.getJSONObject(i).getString("name"));
                country.setState_id(jsonArray.getJSONObject(i).getString("state_id"));
                pojoCities.add(country);
            }
            Log.i(TAG, "parseJsonCity: " + pojoCities.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
