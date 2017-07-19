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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptMyResponse;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoMyResposne;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.view.ViewAgentProfile;
import com.app.elixir.TravelB2B.view.ViewMyResdetailView;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));
        pojoMyResposneArrayList = new ArrayList<>();
        mAdapter = new adptMyResponse(thisActivity, pojoMyResposneArrayList);


        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String key, String value1) {

                if (key.equals("detail")) {

                    Intent intent = new Intent(thisActivity, ViewMyResdetailView.class);
                    intent.putExtra("refId", value1);
                    CM.startActivity(intent, thisActivity);

                } else {
                    CM.startActivity(thisActivity, ViewAgentProfile.class);
                }


            }
        });


        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
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


        webMyResponse(CM.getSp(thisActivity, CV.PrefID, "").toString(), "", "", "", "", "");


    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(true);
    }

   /* public void webMyResponse(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getMyResponse(v, userId, new OnVolleyHandler() {
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


                    for (int i = 0; i < jsonArray.length(); i++) {

                        pojoMyResposne myResposne = new pojoMyResposne();
                        myResposne.setComment(jsonArray.getJSONObject(i).get("comment").toString());
                        myResposne.setRequest_id(jsonArray.getJSONObject(i).get("request_id").toString());
                        JSONObject jsonObjectReq = new JSONObject(jsonArray.getJSONObject(i).get("request").toString());
                        myResposne.setCategory_id(jsonObjectReq.optString("category_id").toString());
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
                        myResposne.setDestination_city(jsonObjectReq.optString("destination_city").toString());
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
    }*/


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
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
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

        SearchView searchView = (SearchView) layout.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by Name");
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) searchView.findViewById(id);
        Typeface myCustomFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
        searchText.setTypeface(myCustomFont);
        builder.setIcon(R.drawable.logo3);

        spinnerBudget = (Spinner) layout.findViewById(R.id.spinnerbudget);
        edtStartDate = (EditText) layout.findViewById(R.id.edtstartdate1);
        edtStartDate.setOnTouchListener(this);
        edtEndDate = (EditText) layout.findViewById(R.id.edtenddate1);
        edtEndDate.setOnTouchListener(this);
        edtRefId = (EditText) layout.findViewById(R.id.edtrefid1);
        spinnerRefType = (Spinner) layout.findViewById(R.id.spinnerreftype);


        // String userid, String reqType, String reqId, String startDate, String endDate,
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String reqType, reqId, startDate, endDate, budgetv;
                reqType = spinnerRefType.getSelectedItem().toString();
                reqId = edtRefId.getText().toString();
                startDate = edtStartDate.getText().toString();
                endDate = edtEndDate.getText().toString();
                budgetv = spinnerBudget.getSelectedItem().toString();
                if (reqType.equals("Select Package")) {
                    reqType = "";
                }
                if (budgetv.equals("Select Budget")) {
                    budgetv = "";
                }
                webMyResponse(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getReqTypeRev(reqType), reqId, startDate, endDate, budgetv);

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
                        edtStartDate.setText(month + "/" + dayOfMonth + "/" + year);
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

    public void webMyResponse(String userId, String reqType, String reqId, String startDate, String endDate, String budget) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getMyResponse(v, userId, reqType, reqId, startDate, endDate, budget, new OnVolleyHandler() {
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

                    pojoMyResposneArrayList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        pojoMyResposne myResposne = new pojoMyResposne();
                        myResposne.setComment(jsonArray.getJSONObject(i).get("comment").toString());
                        myResposne.setRequest_id(jsonArray.getJSONObject(i).get("request_id").toString());
                        JSONObject jsonObjectReq = new JSONObject(jsonArray.getJSONObject(i).get("request").toString());
                        myResposne.setCategory_id(jsonObjectReq.optString("category_id").toString());
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
                        myResposne.setDestination_city(jsonObjectReq.optString("destination_city").toString());
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
}
