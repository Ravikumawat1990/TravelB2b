package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptRespondToRequest;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnAdapterItemClickListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoMyResposne;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.view.ViewAgentProfile;
import com.app.elixir.TravelB2B.view.ViewRespondToRequestDetailView;
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

/**
 * Created by mtpl on 12/15/2015.
 */
public class FragRespondToRequest extends Fragment implements View.OnTouchListener {
    private static final String TAG = "FragRespondToRequest";
    Activity thisActivity;
    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private adptRespondToRequest mAdapter;
    ArrayList<pojoMyResposne> pojoMyResposneArrayList;
    FloatingActionButton myFab;
    EditText edtStartDate;
    EditText edtEndDate;
    EditText edtRefId;
    Spinner spinnerRefType, spinnerBudget, spinnerRating;

    private int dayOfMonth1;
    private int month1;
    private int year1;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.respondToReq));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_resposndtorequest, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.respondToReq));
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));

        pojoMyResposneArrayList = new ArrayList<>();
        mAdapter = new adptRespondToRequest(thisActivity, pojoMyResposneArrayList);

        setHasOptionsMenu(true);
       /* mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value, String value1) {

                if (value1.equals("detail")) {

                    Intent intent = new Intent(thisActivity, ViewRespondToRequestDetailView.class);
                    intent.putExtra("refId", value);
                    CM.startActivity(intent, thisActivity);
                } else {
                    ShowInterest();
                }
            }
        });*/
        mAdapter.SetOnItemClickListener(new OnAdapterItemClickListener() {
            @Override
            public void onItemClick(String value, String value1, String value2) {

                if (value1.equals("detail")) {

                    Intent intent = new Intent(thisActivity, ViewRespondToRequestDetailView.class);
                    intent.putExtra("refId", value);
                    intent.putExtra("reqtype", value2);
                    CM.startActivity(intent, thisActivity);
                } else if (value1.equals("showInt")) {

                    ShowInterest(value);
                } else if (value1.equals("showAgent")) {
                    Intent intent = new Intent(thisActivity, ViewAgentProfile.class);
                    intent.putExtra("userId", value);
                    CM.startActivity(intent, thisActivity);
                }
            }
        });

        webResponseToReq(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), "", "", "", "", "", "", "");


        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {


        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showFilter();
                //mohit
                //change filterPopup
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
                    // myFab.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });
        myFab.hide();

    }


   /* public void ShowInterest() {

        LayoutInflater factory = LayoutInflater.from(thisActivity);
        final View textEntryView = factory.inflate(R.layout.showinterest, null);
        final EditText input1 = (EditText) textEntryView.findViewById(R.id.quote_price);
        final EditText input2 = (EditText) textEntryView.findViewById(R.id.comment);
        final AlertDialog.Builder alert = new AlertDialog.Builder(thisActivity);

        alert.setView(textEntryView)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.i("AlertDialog", "TextEntry 1 Entered " + input1.getText().toString());
                                Log.i("AlertDialog", "TextEntry 2 Entered " + input2.getText().toString());
                    *//* User clicked OK so do some stuff *//*
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        });
        alert.show();
    }*/


    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(true);

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

    public void ShowInterest(final String reqId) {

        LayoutInflater factory = LayoutInflater.from(thisActivity);
        final View textEntryView = factory.inflate(R.layout.showinterest, null);
        final EditText edtqutPrice = (EditText) textEntryView.findViewById(R.id.quote_price);
        final EditText edtComment = (EditText) textEntryView.findViewById(R.id.comment);
        final AlertDialog.Builder alert = new AlertDialog.Builder(thisActivity);

        alert.setView(textEntryView)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String QutPrice, Comment;
                                QutPrice = edtqutPrice.getText().toString();
                                Comment = edtComment.getText().toString();
                                if (!QutPrice.matches("")) {
                                    if (!Comment.matches("")) {
                                        webAddResponse(QutPrice, Comment, reqId);
                                    } else {
                                        CM.showToast("Enter Comment", thisActivity);
                                    }
                                } else {
                                    CM.showToast("Enter Quote Price", thisActivity);
                                }
//Log.i("AlertDialog", "TextEntry 1 Entered " + input1.getText().toString());
//Log.i("AlertDialog", "TextEntry 2 Entered " + input2.getText().toString());
/* User clicked OK so do some stuff */
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        });
        alert.show();
    }

    public void webAddResponse(String quotPrice, String comment, String reqId) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getAddResponse(v, quotPrice, comment, reqId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getAddResponse(response);

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

    private void getAddResponse(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    CM.showToast(getString(R.string.intrest_submit), thisActivity);
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

    public void showFilterPopup() {
        LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_filter, (ViewGroup) thisActivity.findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity)
                .setView(layout);
        builder.setTitle("Filter By:");

        final SearchView searchView = (SearchView) layout.findViewById(R.id.searchView);
        searchView.setQueryHint("Agent Name");
        searchView.setVisibility(View.VISIBLE);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) searchView.findViewById(id);
        Typeface myCustomFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
        searchText.setTypeface(myCustomFont);
        builder.setIcon(R.drawable.logo3);

        RelativeLayout ratingRoot = layout.findViewById(R.id.ratingroot);
        ratingRoot.setVisibility(View.VISIBLE);
        spinnerBudget = (Spinner) layout.findViewById(R.id.spinnerbudget);
        edtStartDate = (EditText) layout.findViewById(R.id.edtstartdate1);
        edtStartDate.setOnTouchListener(this);
        edtEndDate = (EditText) layout.findViewById(R.id.edtenddate1);
        edtEndDate.setOnTouchListener(this);
        edtRefId = (EditText) layout.findViewById(R.id.edtrefid1);
        spinnerRefType = (Spinner) layout.findViewById(R.id.spinnerreftype);
        spinnerRating = (Spinner) layout.findViewById(R.id.spinnerRating);

// String userid, String roleId, String reqType, String reqId, String startDate, String endDate, String budget,
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String reqType, reqId, startDate, endDate, budgetv, rating, nameAgent;
                nameAgent = searchView.getQuery().toString();
                reqType = spinnerRefType.getSelectedItem().toString();
                reqId = edtRefId.getText().toString();
                startDate = edtStartDate.getText().toString();
                endDate = edtEndDate.getText().toString();
                budgetv = spinnerBudget.getSelectedItem().toString();
                rating = spinnerRating.getSelectedItem().toString();
                if (reqType.equals("Select Package")) {
                    reqType = "";
                }
                if (budgetv.equals("Select Budget")) {
                    budgetv = "";
                }
                if (rating.equals("Select Rating")) {
                    rating = "";
                }
                webResponseToReq(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(), CM.getReqTypeRev(reqType), reqId, startDate, endDate, budgetv, nameAgent, rating);
                //webMyRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString(),reqType,reqId,startDate,endDate,budgetv);

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

    public void webResponseToReq(String userId, String userRole, String reqType, String reqId, String startDate, String endDate, String budget, String agentName, String rating) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getMyResponseToReq(v, userId, userRole, reqType, reqId, startDate, endDate, budget, agentName, rating, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseToReq(response);

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

    private void getResponseToReq(String response) {
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
                    pojoMyResposneArrayList.clear();
                    JSONObject jsonArray1 = new JSONObject(jsonObject.optString("citystate").toString());
                    JSONObject jsonObject1 = new JSONObject(jsonArray1.optString("citystate"));

                    JSONObject jsonObject2 = new JSONObject(jsonObject.optString("rating").toString());

                    JSONObject jsonObject11 = new JSONObject(jsonObject2.optString("rating").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        pojoMyResposne myResposne = new pojoMyResposne();
                        myResposne.setUserId(jsonArray.getJSONObject(i).get("id").toString());
                        myResposne.setCategory_id(jsonArray.getJSONObject(i).get("category_id").toString());
                        myResposne.setReference_id(jsonArray.getJSONObject(i).get("reference_id").toString());
                        myResposne.setTotal_budget(jsonArray.getJSONObject(i).get("total_budget").toString());
                        myResposne.setChildren(jsonArray.getJSONObject(i).get("children").toString());
                        myResposne.setAdult(jsonArray.getJSONObject(i).get("adult").toString());
                        myResposne.setRoom1(jsonArray.getJSONObject(i).get("room1").toString());
                        myResposne.setRoom2(jsonArray.getJSONObject(i).get("room2").toString());
                        myResposne.setRoom3(jsonArray.getJSONObject(i).get("room3").toString());
                        myResposne.setChild_with_bed(jsonArray.getJSONObject(i).get("child_with_bed").toString());
                        myResposne.setChild_without_bed(jsonArray.getJSONObject(i).get("child_without_bed").toString());
                        myResposne.setHotel_rating(jsonArray.getJSONObject(i).get("hotel_rating").toString());
                        myResposne.setHotel_category(jsonArray.getJSONObject(i).get("hotel_category").toString());
                        myResposne.setMeal_plan(jsonArray.getJSONObject(i).get("meal_plan").toString());

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
                        myResposne.setUserComment(jsonArray.getJSONObject(i).get("comment").toString());
                        myResposne.setStart_date(jsonArray.getJSONObject(i).get("start_date").toString());
                        myResposne.setEnd_date(jsonArray.getJSONObject(i).get("end_date").toString());

                        JSONArray jsonArray2 = new JSONArray(jsonObject11.optString(jsonArray.getJSONObject(i).get("id").toString()));
                        for (int i1 = 0; i1 < jsonArray2.length(); i1++) {
                            myResposne.setRating(jsonArray2.getJSONObject(i1).optString("average_rating"));

                        }


                        JSONObject jsonObjectUser = new JSONObject(jsonArray.getJSONObject(i).get("user").toString());


                        myResposne.setId(jsonObjectUser.optString("id").toString());
                        myResposne.setFirst_name(jsonObjectUser.optString("first_name").toString());
                        myResposne.setLast_name(jsonObjectUser.optString("last_name").toString());
                        myResposne.setMobile_number(jsonObjectUser.optString("mobile_number").toString());
                        myResposne.setP_contact(jsonObjectUser.optString("p_contact").toString());
                        pojoMyResposneArrayList.add(myResposne);
                    }

                    pojoMyResposneArrayList.size();
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.invalidate();
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
        MenuItem item = menu.findItem(R.id.filter);
        menu.findItem(R.id.noti).setVisible(false);
        item.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                showFilterPopup();
                return true;
        }
        return false;
    }
}
