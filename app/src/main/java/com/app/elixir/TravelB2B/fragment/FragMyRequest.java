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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.AutocompleteAdapter;
import com.app.elixir.TravelB2B.adapter.adptMyRequest;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnApiDataChange;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoCity;
import com.app.elixir.TravelB2B.pojos.pojoMyRequest;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.view.ViewAgentProfile;
import com.app.elixir.TravelB2B.view.ViewCheckResponse;
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
import java.util.List;

/**
 * Created by NetSupport on 01-06-2017.
 */

public class FragMyRequest extends Fragment implements View.OnTouchListener {

    private static final String TAG = "FragMyRequest";
    private OnFragmentInteractionListener mListener;
    Activity thisActivity;

    adptMyRequest mAdapter;
    private RecyclerView mRecyclerView;
    Boolean wantToCloseDialog = false;
    ArrayList<pojoMyRequest> pojoMyResposneArrayList;
    FloatingActionButton myFab;
    EditText edtStartDate;
    EditText edtEndDate;
    EditText edtRefId;
    Spinner spinnerRefType, spinnerBudget;

    AlertDialog alertDialog1;
    CharSequence[] values = {"Total Budget (High To Low)", "Total Budget (Low To High) ", "No. of Responses (Low To High)", "No. of Responses (High To Low)", "Request Type"};
    AlertDialog levelDialog;
    private int dayOfMonth1;
    private int month1;
    private int year1;
    private OnApiDataChange listener;
    String sortItemPos = "0";
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLayoutManager;


    //new filter fields
    AutoCompleteTextView spinnerPCity, spinnerDCity, spinnerChatWid;
    RelativeLayout spinnerQuotedprice;
    EditText edtMembers;
    CheckBox checkboxFollow, checkboxShareDetail;
    String pickupCityId, destCityId;
    private ArrayList<pojoCity> pojoCities;


    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            this.listener = (OnApiDataChange) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.MyReq));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_myrequest, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.MyReq));
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(thisActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setRemoveDuration(1000);

        mRecyclerView.setItemAnimator(defaultItemAnimator);

        pojoMyResposneArrayList = new ArrayList<>();
        mAdapter = new adptMyRequest(thisActivity, pojoMyResposneArrayList);


        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value, String value1) {

                if (value.equals("remove")) {
                    showPopup(thisActivity, value1);
                } else if (value.equals("check")) {

                    if (value1.equals("noresposne")) {
                    } else {
                        Intent intent = new Intent(thisActivity, ViewCheckResponse.class);
                        intent.putExtra("refId", value1);
                        CM.startActivity(intent, thisActivity);
                    }
                } else if (value1.equals("showAgent")) {
                    Intent intent = new Intent(thisActivity, ViewAgentProfile.class);
                    intent.putExtra("userId", value);
                    CM.startActivity(intent, thisActivity);
                } else {
                    Intent intent = new Intent(thisActivity, ViewCommonDeatilView.class);
                    intent.putExtra("refId", value);
                    intent.putExtra("reqtype", value1);
                    intent.putExtra("title", getString(R.string.MyReq));
                    CM.startActivity(intent, thisActivity);
                }
            }
        });

        pojoCities = new ArrayList<>();
        parseJsonCity();

        initView(rootView);
        return rootView;
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


    private void initView(View rootView) {

        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showFilter();
            }
        });

        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showFilter();
                //mohit
                //change filterPopup
                showFilterPopup();
            }
        });
        myFab.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(true);
        if (CM.isInternetAvailable(thisActivity)) {
            webMyRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), "", "", "", "", "", "", "", "", "");
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.filter).setVisible(true);
        menu.findItem(R.id.noti).setVisible(false);
    }


   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                return true;
        }
        return false;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.filter);
    }*/


    public void showPopup(Context context, final String requestId) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to remove this request?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        webRemoveRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), requestId);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logonewnew).show();
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

    public void webRemoveRequest(String userId, String reqid) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getRemReq(v, userId, reqid, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getRemoveRequest(response);

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

    private void getRemoveRequest(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (jsonObject.optString("response_object").toString().equals("1")) {

                        listener.onItemClick(true);

                        if (CM.isInternetAvailable(thisActivity)) {
                            webMyRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), "", "", "", "", "", "", "", "", "");
                        } else {
                            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
                        }


                    } else {
                        CM.showToast(jsonObject.optString("response_object").toString(), thisActivity);
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

    public void showFilterPopup() {
        LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_filter, (ViewGroup) thisActivity.findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity)
                .setView(layout);
        builder.setTitle("Filter By:");

        final SearchView searchView = (SearchView) layout.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by Name");
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final TextView searchText = (TextView) searchView.findViewById(id);
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


        //
        //hide show new fields
        spinnerPCity.setVisibility(View.VISIBLE);
        spinnerDCity.setVisibility(View.VISIBLE);
        edtMembers.setVisibility(View.VISIBLE);
        spinnerChatWid.setVisibility(View.GONE);
        checkboxFollow.setVisibility(View.GONE);
        checkboxShareDetail.setVisibility(View.GONE);
        spinnerQuotedprice.setVisibility(View.GONE);

        AutocompleteAdapter adptCountry1 = new AutocompleteAdapter(thisActivity, R.layout.conntylayout, R.id.textViewSpinner, pojoCities);
        spinnerPCity.setThreshold(3);
        spinnerPCity.setAdapter(adptCountry1);
        spinnerDCity.setThreshold(3);
        spinnerDCity.setAdapter(adptCountry1);

        pickupCityId = "";
        destCityId = "";

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

        // String userid, String roleId, String reqType, String reqId, String startDate, String endDate, String budget,
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String reqType, reqId, startDate, endDate, budgetv;
                //new
                String member;

                reqType = spinnerRefType.getSelectedItem().toString();
                reqId = edtRefId.getText().toString();
                startDate = edtStartDate.getText().toString();
                endDate = edtEndDate.getText().toString();
                budgetv = spinnerBudget.getSelectedItem().toString();

                //new
                member = edtMembers.getText().toString();


                if (reqType.equals("Select Package")) {
                    reqType = "";
                }
                if (budgetv.equals("Select Budget")) {
                    budgetv = "";
                }

                //   webMyRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), CM.getReqTypeRev(reqType), reqId, startDate, endDate, budgetv, pickupCityId, destCityId, member, "");

                //String userid, String roleId, String reqType, String reqId, String startDate, String endDate, String budget, String pcity, String dcity, String member
                webMyRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), CM.getReqTypeRev(reqType), reqId, startDate, endDate, budgetv, pickupCityId, destCityId, member, "");
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

    /*public void showFilterPopup() {
        LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_filter, (ViewGroup) thisActivity.findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity)
                .setView(layout);
        builder.setTitle("Filter By:");

        final SearchView searchView = (SearchView) layout.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by Name");
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final TextView searchText = (TextView) searchView.findViewById(id);
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


        //
        //hide show new fields
        spinnerPCity.setVisibility(View.VISIBLE);
        spinnerDCity.setVisibility(View.VISIBLE);
        edtMembers.setVisibility(View.VISIBLE);
        spinnerChatWid.setVisibility(View.GONE);
        checkboxFollow.setVisibility(View.GONE);
        checkboxShareDetail.setVisibility(View.GONE);
        spinnerQuotedprice.setVisibility(View.GONE);

        AutocompleteAdapter adptCountry1 = new AutocompleteAdapter(thisActivity, R.layout.conntylayout, R.id.textViewSpinner, pojoCities);
        spinnerPCity.setThreshold(3);
        spinnerPCity.setAdapter(adptCountry1);
        spinnerDCity.setThreshold(3);
        spinnerDCity.setAdapter(adptCountry1);

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

        // String userid, String roleId, String reqType, String reqId, String startDate, String endDate, String budget,
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String reqType, reqId, startDate, endDate, budgetv;
                //new
                String member;

                reqType = spinnerRefType.getSelectedItem().toString();
                reqId = edtRefId.getText().toString();
                startDate = edtStartDate.getText().toString();
                endDate = edtEndDate.getText().toString();
                budgetv = spinnerBudget.getSelectedItem().toString();

                //new
                member = edtMembers.getText().toString();


                if (reqType.equals("Select Package")) {
                    reqType = "";
                }
                if (budgetv.equals("Select Budget")) {
                    budgetv = "";
                }


                //String userid, String roleId, String reqType, String reqId, String startDate, String endDate, String budget, String pcity, String dcity, String member
                webMyRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), CM.getReqTypeRev(reqType), reqId, startDate, endDate, budgetv, pickupCityId, destCityId, member, "");

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


    public void webMyRequest(String userid, String roleId, String reqType, String reqId, String startDate, String endDate, String budget, String pcity, String dcity, String member, String sort) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getMyReq(v, userid, roleId, reqType, reqId, startDate, endDate, budget, pcity, dcity, member, sort, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getMyRequest(response);

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

    private void getMyRequest(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":

                    if (!jsonObject.optString("response_object").toString().equals("") && jsonObject.optString("response_object") != null) {

                        JSONArray jsonArray = new JSONArray(jsonObject.optString("response_object").toString());

                        if (jsonArray.length() > 0) {
                            JSONObject jsonObject3 = new JSONObject(jsonObject.optString("countarr").toString());

                            JSONObject jsonObject2 = new JSONObject(jsonObject3.optString("responsecount"));
                            JSONObject jsonArray1 = new JSONObject(jsonObject.optString("citystate").toString());
                            JSONObject jsonObject1 = new JSONObject(jsonArray1.optString("citystate"));

                            pojoMyResposneArrayList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                pojoMyRequest myResposne = new pojoMyRequest();

                                myResposne.setCheckResCount(jsonObject2.optString(jsonArray.getJSONObject(i).get("id").toString()));
                                myResposne.setCategory_id(jsonArray.getJSONObject(i).get("category_id").toString());
                                myResposne.setRequest_id(jsonArray.getJSONObject(i).get("id").toString());
                                myResposne.setReference_id(jsonArray.getJSONObject(i).get("reference_id").toString());
                                myResposne.setTotal_budget(jsonArray.getJSONObject(i).get("total_budget").toString());
                                myResposne.setChildren(jsonArray.getJSONObject(i).get("children").toString());
                                myResposne.setAdult(jsonArray.getJSONObject(i).get("adult").toString());
                                myResposne.setRoom1(jsonArray.getJSONObject(i).get("room1").toString());
                                myResposne.setRoom2(jsonArray.getJSONObject(i).get("room2").toString());
                                myResposne.setRoom3(jsonArray.getJSONObject(i).get("room3").toString());
                                myResposne.setUserComment(jsonArray.getJSONObject(i).get("comment").toString());
                                myResposne.setChild_with_bed(jsonArray.getJSONObject(i).get("child_with_bed").toString());
                                myResposne.setChild_without_bed(jsonArray.getJSONObject(i).get("child_without_bed").toString());
                                myResposne.setHotel_rating(jsonArray.getJSONObject(i).get("hotel_rating").toString());
                                myResposne.setHotel_category(jsonArray.getJSONObject(i).get("hotel_category").toString());
                                myResposne.setMeal_plan(jsonArray.getJSONObject(i).get("meal_plan").toString());


                                //  JSONObject jsonArray2 = new JSONObject(jsonArray.getJSONObject(i).getInt("id"));


                                /* JSONObject jsonObject1 = new JSONObject(jsonArray.getJSONObject(i).get("city").toString());
                                if (jsonObject1 != null) {
                                    myResposne.setDestination_city(jsonObject1.optString("name"));
                                }*/
                                myResposne.setDestination_city(jsonObject1.optString(jsonArray.getJSONObject(i).get("id").toString()));

                                myResposne.setCheck_in(jsonArray.getJSONObject(i).get("check_in").toString());
                                myResposne.setCheck_out(jsonArray.getJSONObject(i).get("check_out").toString());
                                myResposne.setTransport_requirement(jsonArray.getJSONObject(i).get("transport_requirement").toString());
                                myResposne.setPickup_city(jsonArray.getJSONObject(i).get("pickup_city").toString());
                                myResposne.setPickup_state(jsonArray.getJSONObject(i).get("pickup_state").toString());
                                myResposne.setPickup_country(jsonArray.getJSONObject(i).get("pickup_country").toString());
                                myResposne.setPickup_locality(jsonArray.getJSONObject(i).get("pickup_locality").toString());
                                myResposne.setCity_id(jsonArray.getJSONObject(i).get("city_id").toString());
                                myResposne.setState_id(jsonArray.getJSONObject(i).get("state_id").toString());
                                myResposne.setFinal_city(jsonArray.getJSONObject(i).get("final_city").toString());
                                myResposne.setFinal_state(jsonArray.getJSONObject(i).get("final_state").toString());
                                myResposne.setFinal_country(jsonArray.getJSONObject(i).get("final_country").toString());
                                myResposne.setStart_date(jsonArray.getJSONObject(i).get("start_date").toString());
                                myResposne.setEnd_date(jsonArray.getJSONObject(i).get("end_date").toString());

                                JSONObject jsonObjectUser = new JSONObject(jsonArray.getJSONObject(i).get("user").toString());
                                myResposne.setFirst_name(jsonObjectUser.optString("first_name").toString());
                                myResposne.setLast_name(jsonObjectUser.optString("last_name").toString());
                                myResposne.setMobile_number(jsonObjectUser.optString("mobile_number").toString());
                                myResposne.setP_contact(jsonObjectUser.optString("p_contact").toString());
                                myResposne.setId(jsonObjectUser.optString("id").toString());
                                pojoMyResposneArrayList.add(myResposne);
                            }
                            pojoMyResposneArrayList.size();
                            mRecyclerView.setAdapter(mAdapter);
                           /* int totalItems = 100;
                            int currentPage = 0;
                            int pageSize = 10;
                            int numPages = (int) Math.ceil((float) totalItems / pageSize);

                            ArrayList<pojoMyRequest> items = new ArrayList<pojoMyRequest>(pojoMyResposneArrayList);

                            List<pojoMyRequest> page = items.subList(currentPage, pageSize);*/

                            // mRecyclerView.invalidate();
                        } else {
                            pojoMyResposneArrayList.clear();
                            mRecyclerView.setAdapter(mAdapter);
                            mRecyclerView.invalidate();
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

    public static Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

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

                String Comment;
                float Rating;

                Comment = edtComment.getText().toString();
                Rating = Ratingbar.getRating();


                if (!Comment.matches("")) {
                    if (Rating > 0) {
                        webReview(userId, profileuID, "" + Rating, "0", Comment, "");
                    } else {
                        CM.showToast("select Rating", thisActivity);
                    }
                } else {
                    CM.showToast("Enter Comment", thisActivity);
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
                if (wantToCloseDialog) {
                    levelDialog.dismiss();

                    webMyRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), "", "", "", "", "", "", "", "", setSort(sortItemPos));
                    wantToCloseDialog = false;

                }


                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });


    }
    //CharSequence[] values = {"Total Budget (High To Low)", "Total Budget (Low To High) ", "No. of Responses (Low To High)", "No. of Responses (High To Low)", "Request Type"};

    /*requesttype
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
            case "2":
                sortItem = "totalbudgetlh";
                break;
            case "3":
                sortItem = "totalbudgethl";
                break;
            case "4":
                sortItem = "requesttype";
                break;
        }

        return sortItem;
    }


}
