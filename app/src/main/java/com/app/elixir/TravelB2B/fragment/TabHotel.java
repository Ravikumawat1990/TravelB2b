package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.AutocompleteAdapter;
import com.app.elixir.TravelB2B.interfaceimpl.OnApiDataChange;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplEditText;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.numberPicker.NumberPicker;
import com.app.elixir.TravelB2B.pojos.pojoCity;
import com.app.elixir.TravelB2B.pojos.pojoCountry;
import com.app.elixir.TravelB2B.pojos.pojoPackage;
import com.app.elixir.TravelB2B.pojos.pojoState;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.utils.MultiSelectionSpinner;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;
import com.silencedut.expandablelayout.ExpandableLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by NetSupport on 02-06-2017.
 */

public class TabHotel extends Fragment implements View.OnClickListener, View.OnTouchListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    private static final String TAG = "TabHotel";
    Activity thisActivity;
    MtplButton btnAddAnother, btnSubmit;
    private LinearLayout parentLinearLayout;
    private ExpandableLayout expandableLayout;
    private NestedScrollView rootScrollView;
    NestedScrollView childScrollview;
    private MtplEditText checkIn, checkOut;
    int i;
    private MtplEditText edtlocality;
    private View rowView;

    ArrayList<String> strings;
    private MtplEditText checkIn1;
    private MtplEditText checkOut1;
    private ImageView imageView1, imageView2, imageView3;
    MtplEditText singleRoom, doubleRoom, tripalRoom, childWithbed, childWithoutbed, edtComment;
    Spinner spinnerHotelRating, spinnerMealPlane, spinnerTransport;
    MtplEditText refId, totBudget;
    NumberPicker numberPicker, childBelow;
    AutoCompleteTextView destiCity, trapickupCity, finalCity;
    MultiSelectionSpinner spinnerHotelCatMain;
    MtplEditText destiState, destiCountry, destiLocality;
    String countryId, cityId, stateId;

    ArrayList<pojoCity> pojoCities;
    ArrayList<pojoCountry> countryArrayList;
    ArrayList<pojoState> pojoStateArrayList;

    private int dayOfMonth1;
    private int month1;
    private int year1;
    private OnApiDataChange listener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            this.listener = (OnApiDataChange) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabhotel, container, false);
        thisActivity = getActivity();
        initView(rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    private void initView(View view) {

        countryArrayList = new ArrayList<>();
        pojoCities = new ArrayList<>();
        pojoStateArrayList = new ArrayList<>();
        //General Req
        refId = (MtplEditText) view.findViewById(R.id.edtRefId);
        totBudget = (MtplEditText) view.findViewById(R.id.edtTotalBudget);
        numberPicker = (NumberPicker) view.findViewById(R.id.number_pickerAdult);
        childBelow = (NumberPicker) view.findViewById(R.id.number_pickerChildBelow);


        //Stay Req
        singleRoom = (MtplEditText) view.findViewById(R.id.edtSingleRoom);
        doubleRoom = (MtplEditText) view.findViewById(R.id.edtDoubleRoom);
        tripalRoom = (MtplEditText) view.findViewById(R.id.edtTripleRoom);
        childWithbed = (MtplEditText) view.findViewById(R.id.edt_Child_with_bed);
        childWithoutbed = (MtplEditText) view.findViewById(R.id.edt_Child_without_bed);
        spinnerHotelRating = (Spinner) view.findViewById(R.id.spinnerHotelRating);
        spinnerHotelCatMain = (MultiSelectionSpinner) view.findViewById(R.id.mySpinner);
        String[] array = getResources().getStringArray(R.array.hotCatArray);
        spinnerHotelCatMain.setItems(array);
        spinnerHotelCatMain.setListener(this);
        spinnerMealPlane = (Spinner) view.findViewById(R.id.spinnerMealPlane);
        destiCity = (AutoCompleteTextView) view.findViewById(R.id.edtDestinationCity);
        destiState = (MtplEditText) view.findViewById(R.id.edtDestinationState);
        destiCountry = (MtplEditText) view.findViewById(R.id.edtDestinationCountry);
        destiLocality = (MtplEditText) view.findViewById(R.id.edtDestinationLocality);
        destiState.setEnabled(false);
        destiCountry.setEnabled(false);
        checkIn = (MtplEditText) view.findViewById(R.id.edtCheckIn);
        checkOut = (MtplEditText) view.findViewById(R.id.edtCheckOut);
        btnAddAnother = (MtplButton) view.findViewById(R.id.btnAddAnother);

        btnAddAnother.setVisibility(View.GONE);


        //Comment
        edtComment = (MtplEditText) view.findViewById(R.id.edtComment);

        btnSubmit = (MtplButton) view.findViewById(R.id.btnSubmit);

        ExpandableLayout expandableLayout1 = (ExpandableLayout) view.findViewById(R.id.expView1);
        ExpandableLayout expandableLayout2 = (ExpandableLayout) view.findViewById(R.id.expView2);
        ExpandableLayout expandableLayout3 = (ExpandableLayout) view.findViewById(R.id.expView4);

        imageView1 = (ImageView) view.findViewById(R.id.expImg1);
        imageView2 = (ImageView) view.findViewById(R.id.expImg2);
        imageView3 = (ImageView) view.findViewById(R.id.expImg3);

        imageView1.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorLightGray));
        imageView1.setBackgroundResource(R.drawable.ic_add_black_24dp);
        imageView2.setBackgroundResource(R.drawable.ic_add_black_24dp);
        imageView3.setBackgroundResource(R.drawable.ic_add_black_24dp);


        expandableLayout1.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
            @Override
            public void onExpand(boolean b) {
                if (b) {
                    imageView1.setBackgroundResource(R.drawable.ic_remove_black_24dp);

                } else {
                    imageView1.setBackgroundResource(R.drawable.ic_add_black_24dp);
                }

            }
        });
        expandableLayout2.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
            @Override
            public void onExpand(boolean b) {
                if (b) {
                    imageView2.setBackgroundResource(R.drawable.ic_remove_black_24dp);
                } else {
                    imageView2.setBackgroundResource(R.drawable.ic_add_black_24dp);
                }

            }
        });


        expandableLayout3.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
            @Override
            public void onExpand(boolean b) {
                if (b) {
                    imageView3.setBackgroundResource(R.drawable.ic_remove_black_24dp);
                } else {
                    imageView3.setBackgroundResource(R.drawable.ic_add_black_24dp);
                }

            }
        });


        parentLinearLayout = (LinearLayout) view.findViewById(R.id.parent_linear_layout);
        rootScrollView = (NestedScrollView) view.findViewById(R.id.rootScroolView);
        childScrollview = (NestedScrollView) view.findViewById(R.id.childScrollView);

        checkIn.setOnTouchListener(this);
        checkOut.setOnTouchListener(this);
        btnSubmit.setOnClickListener(this);
        btnAddAnother.setOnClickListener(this);
        strings = new ArrayList<>();

        destiCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("your selected item", "" + pojoCities.get(position).getId());
                String statename = "";
                cityId = pojoCities.get(position).getId();
                if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                    for (int i = 0; i < pojoStateArrayList.size(); i++) {
                        if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                            statename = pojoStateArrayList.get(i).getState_name();
                            stateId = pojoStateArrayList.get(i).getId();
                            destiState.setText(statename);
                            for (int j = 0; j < countryArrayList.size(); j++) {

                                if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                    destiCountry.setText(countryArrayList.get(j).getCountry_name());
                                    countryId = countryArrayList.get(j).getId();
                                    break;

                                }


                            }


                            break;
                        } else {

                        }
                    }
                } else {
                    destiState.setText("");
                }


            }
        });


        destiCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    for (int i = 0; i < pojoCities.size(); i++) {
                        if (destiCity.getText().toString().equals(pojoCities.get(i).getName())) {
                            for (int j = 0; j < pojoCities.size(); j++) {
                                if (destiCity.getText().toString().equals(pojoCities.get(i).getName())) {
                                    String id = pojoCities.get(i).getState_id();
                                    for (int k = 0; k < pojoStateArrayList.size(); k++) {
                                        if (id.equals(pojoStateArrayList.get(k).getId().toString())) {
                                            String statename = pojoStateArrayList.get(k).getState_name();
                                            destiState.setText(statename);


                                          /*  for (int l = 0; l < countryArrayList.size(); l++) {

                                                if (pojoStateArrayList.get(k).getCountry_id().toString().equals(countryArrayList.get(l).getId().toString())) {

                                                    destiCountry.setText(countryArrayList.get(l).getCountry_name());
                                                    break;

                                                }


                                            }*/


                                            break;
                                        } else {


                                        }

                                    }


                                } else {
                                    destiState.setText("");
                                    destiCountry.setText("");

                                }


                            }


                            break;
                        } else {
                            destiCity.setText("");
                            destiState.setText("");
                            destiCountry.setText("");

                        }
                    }

                    if (pojoCities != null && pojoCities.size() == 0) {
                        destiCity.setText("");
                        destiState.setText("");
                        destiCountry.setText("");
                    }

                } else {


                }
            }
        });


        if (CM.isInternetAvailable(thisActivity)) {
          /*  webCallCity();
            webCallState();
            webCallCountry();*/


            if (CM.getSp(thisActivity, "citydata", "").toString().equals("")) {
                webCallCity();
                webCallState();
                webCallCountry();
            } else {
                getResponseForCity(CM.getSp(thisActivity, "citydata", "").toString());
                getResponseForState(CM.getSp(thisActivity, "statedata", "").toString());
                getResponseForCountry(CM.getSp(thisActivity, "countrydata", "").toString());

              /*  webCallCity();
                webCallState();
                webCallCountry();*/
            }


        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
        }


    }

    public void onAddField(View v) {

        try {
            LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.addanotherdestination, null);
            rowView.setId(i++);
            MtplButton mtplButton = (MtplButton) rowView.findViewById(R.id.btnRemove1);
            edtlocality = (MtplEditText) rowView.findViewById(R.id.locality);
            checkIn1 = (MtplEditText) rowView.findViewById(R.id.edtCheckIn1);
            checkOut1 = (MtplEditText) rowView.findViewById(R.id.edtCheckout1);
            checkIn1.setOnTouchListener(this);
            checkOut1.setOnTouchListener(this);

            String[] array = getResources().getStringArray(R.array.hotCatArray);
            MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) rowView.findViewById(R.id.mySpinner);
            multiSelectionSpinner.setItems(array);
            multiSelectionSpinner.setListener(this);


            //rowView.setId(i++);
            // Log.i(TAG, "onAddField: "i++);

            mtplButton.setOnClickListener(this);
            Log.i(TAG, "onAddField: " + parentLinearLayout.getChildCount());
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
            CM.showToast("DESIGNATION ADDED", thisActivity);


        } catch (Exception e) {
            CM.showToast(e.getMessage(), thisActivity);
        }


    }

    public void onDelete(View v) {
        try {


            Log.i(TAG, "onDelete: ");

            parentLinearLayout.removeView((View) v.getParent());

            CM.showToast("DESIGNATION IS REMOVED", thisActivity);
        } catch (Exception e) {

            CM.showToast(e.getMessage(), thisActivity);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnAddAnother:
                onAddField(view);
                break;
            case R.id.btnRemove1:
                onDelete(view);
                break;
            case R.id.btnSubmit:


                if (!refId.getText().toString().equals("")) {


                    if (!totBudget.getText().toString().equals("")) {


                        if (!destiCity.getText().toString().equals("")) {


                            if (!checkIn.getText().toString().equals("")) {

                                if (!checkOut.getText().toString().equals("")) {

                                    JSONObject jsonObject = new JSONObject();


                                    JSONObject jsonObject1 = new JSONObject();

                                    pojoPackage pojoPackage = new pojoPackage();
                                    //General Req
                                    JSONObject generalReqObj = new JSONObject();
                                    JSONArray generalReqArray = new JSONArray();
                                    JSONObject obj = new JSONObject();
                                    try {
                                        obj.put("refId", refId.getText().toString());
                                        obj.put("totBudget", totBudget.getText().toString());
                                        obj.put("members", numberPicker.getValue());
                                        obj.put("childBelow", childBelow.getValue());
                                        generalReqArray.put(obj);
                                        generalReqObj.put("General_Req", generalReqArray);
                                    } catch (Exception e) {

                                    }


                                    //Stay Req

                                    JSONObject obj1 = new JSONObject();
                                    JSONObject stayReqReqObj = new JSONObject();
                                    JSONArray stayReqArray = new JSONArray();
                                    try {

                                        for (int j = 0; j < 2; j++) {
                                            obj1.put("singleRoom", singleRoom.getText().toString());
                                            obj1.put("doubleRoom", doubleRoom.getText().toString());
                                            obj1.put("tripalRoom", tripalRoom.getText().toString());
                                            obj1.put("childWithbed", childWithbed.getText().toString());
                                            obj1.put("childWithoutbed", childWithoutbed.getText().toString());
                                            obj1.put("HotelRating", spinnerHotelRating.getSelectedItem());
                                            obj1.put("HotelCat", spinnerHotelCatMain.getSelectedStrings());
                                            obj1.put("MealPlane", spinnerMealPlane.getSelectedItem());
                                            obj1.put("destiCity", destiCity.getText().toString());
                                            obj1.put("destiState", destiState.getText().toString());
                                            obj1.put("destiCountry", destiCountry.getText().toString());
                                            obj1.put("destiLocality", destiLocality.getText().toString());
                                            obj1.put("checkIn", checkIn.getText().toString());
                                            obj1.put("checkOut", checkOut.getText().toString());
                                            stayReqArray.put(obj1);
                                        }
                                        generalReqObj.put("Stay_Req", stayReqArray);


                                    } catch (Exception e) {

                                    }


                                    // JSONObject commentObj = new JSONObject();
                                    try {
                                        generalReqObj.put("comt", edtComment.getText().toString());
                                    } catch (Exception e) {

                                    }

                                    try {
                                        jsonObject1.put("responsneCode", "200");
                                        jsonObject1.put("responsneObject", generalReqObj);
                                    } catch (Exception e) {

                                    }
                                    ArrayList<pojoPackage> pojoPackages = new ArrayList<>();


                                    pojoPackage aPackage = new pojoPackage();
                                    aPackage.setCategory_id(CM.getSp(thisActivity, CV.PrefRole_Id, "").toString());
                                    aPackage.setUser_id(CM.getSp(thisActivity, CV.PrefID, "").toString());
                                    aPackage.setReference_id(refId.getText().toString());
                                    aPackage.setTotal_budget(totBudget.getText().toString());
                                    aPackage.setAdult(String.valueOf(numberPicker.getValue()));
                                    aPackage.setChildren(String.valueOf(childBelow.getValue()));
                                    aPackage.setRoom1(singleRoom.getText().toString());
                                    aPackage.setRoom2(doubleRoom.getText().toString());
                                    aPackage.setRoom3(tripalRoom.getText().toString());
                                    aPackage.setChild_with_bed(childWithbed.getText().toString());
                                    aPackage.setChild_without_bed(childWithoutbed.getText().toString());
                                    aPackage.setHotel_rating(CM.getHotelRating(spinnerHotelRating.getSelectedItem().toString()));

                                    List<String> words = spinnerHotelCatMain.getSelectedStrings();
                                    StringBuilder stringBuilder = new StringBuilder();
                                    ArrayList<String> strings = new ArrayList<>();
                                    for (int i1 = 0; i1 < words.size(); i1++) {

                                        Log.i(TAG, "onClick: " + CM.setHotelCatRev(words.get(i1).toString()));
                                        stringBuilder.append(CM.setHotelCatRev(words.get(i1).toString()));
                                        strings.add(CM.setHotelCatRev(words.get(i1).toString()));
                                        stringBuilder.append(",");

                                    }
                                    String stateList = strings.toString().replace("[", "").replace("]", "")
                                            .replace(", ", ",");

                                    aPackage.setHotel_category(stateList);
                                    aPackage.setMeal_plan(CM.getMealPlaneRev(spinnerMealPlane.getSelectedItem().toString()));
                                    aPackage.setLocality(destiLocality.getText().toString());
                                    aPackage.setCity_name(destiCity.getText().toString());
                                    aPackage.setCity_id(cityId);
                                    aPackage.setState_id(stateId);
                                    aPackage.setState_name(destiState.getText().toString());
                                    aPackage.setCountry_name(destiCountry.getText().toString());
                                    aPackage.setCountry_id(countryId);
                                    aPackage.setCheck_in(checkIn.getText().toString());
                                    aPackage.setCheck_out(checkOut.getText().toString());

                                    aPackage.setComment(edtComment.getText().toString());
                                    pojoPackages.add(aPackage);


                                    if (CM.isInternetAvailable(thisActivity)) {
                                        webGetHotel(pojoPackages);
                                    } else {
                                        CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
                                    }


                                    Log.i(TAG, "onClick: " + jsonObject1);

                                } else {
                                    CM.showToast("Please select check-out date.", thisActivity);
                                }

                            } else {
                                CM.showToast("Please select check-in date.", thisActivity);
                            }

                        } else {
                            CM.showToast("Please Select City", thisActivity);
                        }


                    } else {
                        CM.showToast("Please Enter Total Budget", thisActivity);
                    }
                } else {
                    CM.showToast("Please Enter Reference Id", thisActivity);

                }


                for (int i = 0; i < parentLinearLayout.getChildCount(); i++) {
                    View view1 = (View) parentLinearLayout.getChildAt(i);
                    if (view1 instanceof TextInputLayout) {
                        ((TextInputLayout) view1).getEditText().getText();
                        ((TextInputLayout) view1).getEditText().getId();
                        strings.add(((TextInputLayout) view1).getEditText().getText().toString());
                        //((TextInputLayout) view1).getHint().toString()


                    } else if (view1 instanceof TextInputLayout) {


                    } else if (view1 instanceof LinearLayout) {

                        if (((LinearLayout) view1).getChildCount() == 11) {    // New Created  Layout  Add New Destiantion

                            for (int j = 0; j < ((LinearLayout) view1).getChildCount(); j++) {
                                View view11 = (View) ((LinearLayout) view1).getChildAt(j);

                                if (view11 instanceof TextInputLayout) {
                                    ((TextInputLayout) view11).getEditText().getText();
                                    ((TextInputLayout) view11).getEditText().getId();
                                    Log.i(TAG, "onClick: " + ((TextInputLayout) view11).getEditText().getText().toString());
                                    strings.add(((TextInputLayout) view11).getEditText().getText().toString());
                                }


                            }

                        } else if (((LinearLayout) view1).getChildCount() == 2) {

                            for (int j = 0; j < ((LinearLayout) view1).getChildCount(); j++) {
                                View view11 = (View) ((LinearLayout) view1).getChildAt(j);
                                if (view11 instanceof RelativeLayout) {

                                    for (int k = 0; k < ((RelativeLayout) view11).getChildCount(); k++) {

                                        View view111 = (View) ((RelativeLayout) view11).getChildAt(k);

                                        if (view111 instanceof AppCompatSpinner) {

                                            if (((AppCompatSpinner) view111).getSelectedItem() != null) {
                                                ((AppCompatSpinner) view111).getSelectedItem().toString();
                                            } else {
                                                Log.i(TAG, "onClick: ");
                                            }


                                            Log.i(TAG, "onClick: ");
                                        }


                                    }


                                }

                            }

                        }


                    }
                }

                strings.size();
                break;

        }


    }


    public void showCalendar() {

        Calendar now = Calendar.getInstance();


        DatePickerDialog dpd = DatePickerDialog.newInstance(null,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        try {
            Calendar now1 = Calendar.getInstance();
            int day = now1.get(Calendar.DATE);
            int days = now1.getActualMaximum(Calendar.DAY_OF_MONTH);
            ArrayList<Calendar> calendars = new ArrayList<>();


            int month = now1.get(Calendar.MONTH) + 1;
            String yearMonth = now1.get(Calendar.YEAR) + "/" + month + "/";
            for (int i = day + 3; i <= days; i++) {
                calendars.add(DateToCalendar(new Date(yearMonth + i)));
            }

            Calendar tArray[] = calendars.toArray(new Calendar[calendars.size()]);
            dpd.setDisabledDays(tArray);
            dpd.setMinDate(DateToCalendar(new Date(System.currentTimeMillis() - 1000)));
            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


            LocalDate monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);
            // 2016-12-31
            if ((day == (monthEnd.getDayOfMonth() - 2)) || (day == (monthEnd.getDayOfMonth() - 1)) || (day == (monthEnd.getDayOfMonth()))) {

            } else {
                Calendar c = Calendar.getInstance();
                c.set(monthEnd.getYear(), monthEnd.getMonthOfYear() - 1, monthEnd.getDayOfMonth());//Year,Mounth -1,Day
                dpd.setMaxDate(c);
            }

        } catch (Exception e) {
            e.getMessage();

        }

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        switch (view.getId()) {
            case R.id.edtCheckIn:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn.getRight() - checkIn.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                      /*  Calendar now = Calendar.getInstance();

                        *//*if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = format.parse(CM.getSp(thisActivity, "serverDate", "").toString());
                                now.setTime(date);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }*//*

                        DatePickerDialog dpd = DatePickerDialog.newInstance(null,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));
                        try {
                            Calendar now1 = Calendar.getInstance();
                            int day = now1.get(Calendar.DATE);
                            int days = now1.getActualMaximum(Calendar.DAY_OF_MONTH);
                            ArrayList<Calendar> calendars = new ArrayList<>();


                            int month = now1.get(Calendar.MONTH) + 1;
                            String yearMonth = now1.get(Calendar.YEAR) + "/" + month + "/";
                            for (int i = day + 3; i <= days; i++) {
                                calendars.add(DateToCalendar(new Date(yearMonth + i)));
                            }


                            Calendar tArray[] = calendars.toArray(new Calendar[calendars.size()]);
                            dpd.setDisabledDays(tArray);
                            dpd.setMinDate(DateToCalendar(new Date(System.currentTimeMillis() - 1000)));
                            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


                            LocalDate monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);
                            // 2016-12-31
                            if ((day == (monthEnd.getDayOfMonth() - 2)) || (day == (monthEnd.getDayOfMonth() - 1)) || (day == (monthEnd.getDayOfMonth()))) {

                            } else {
                                Calendar c = Calendar.getInstance();
                                c.set(monthEnd.getYear(), monthEnd.getMonthOfYear() - 1, monthEnd.getDayOfMonth());//Year,Mounth -1,Day
                                dpd.setMaxDate(c);
                            }

                            dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


                                    Log.i(TAG, "onDateSet: ");

                                    int month = monthOfYear + 1;

                                    if (checkIn != null) {
                                        checkIn.setText(dayOfMonth + "-" + month + "-" + year);
                                    }

                                }
                            });

                        } catch (Exception e) {
                            e.getMessage();

                        }
                        return true;
                    }*/

                        Calendar now = Calendar.getInstance();
                        Log.i(ContentValues.TAG, "onTouch:" + checkIn.getId());
                        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(null,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));
                        try {


                            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
                            dpd.setMinDate(DateToCalendar(new Date(System.currentTimeMillis() - 1000)));
                            dpd.setOnDateSetListener(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                    Log.i(ContentValues.TAG, "onDateSet: ");
                                    dayOfMonth1 = dayOfMonth;
                                    month1 = monthOfYear;
                                    year1 = year;

                                    int month = monthOfYear + 1;
                                    if (checkIn != null) {


                                        checkIn.setText(month + "-" + dayOfMonth + "-" + year);
                                        checkIn.setSelection(checkIn.getText().length());
                                    }

                                }
                            });

                        } catch (Exception e) {
                            e.getMessage();

                        }
                    }
                }
                break;
            case R.id.edtCheckOut:

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut.getRight() - checkOut.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                       /* Calendar now = Calendar.getInstance();
                     *//*   if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = format.parse(CM.getSp(thisActivity, "serverDate", "").toString());
                                now.setTime(date);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }*//*

                        DatePickerDialog dpd = DatePickerDialog.newInstance(null,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));

                        try {
                            Calendar now1 = Calendar.getInstance();
                            int day = now1.get(Calendar.DATE);
                            int days = now1.getActualMaximum(Calendar.DAY_OF_MONTH);
                            ArrayList<Calendar> calendars = new ArrayList<>();


                            int month = now1.get(Calendar.MONTH) + 1;
                            String yearMonth = now1.get(Calendar.YEAR) + "/" + month + "/";
                            for (int i = day + 3; i <= days; i++) {
                                calendars.add(DateToCalendar(new Date(yearMonth + i)));
                            }


                            Calendar tArray[] = calendars.toArray(new Calendar[calendars.size()]);
                            dpd.setDisabledDays(tArray);
                            dpd.setMinDate(DateToCalendar(new Date(System.currentTimeMillis() - 1000)));
                            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


                            LocalDate monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);
                            // 2016-12-31
                            if ((day == (monthEnd.getDayOfMonth() - 2)) || (day == (monthEnd.getDayOfMonth() - 1)) || (day == (monthEnd.getDayOfMonth()))) {

                            } else {
                                Calendar c = Calendar.getInstance();
                                c.set(monthEnd.getYear(), monthEnd.getMonthOfYear() - 1, monthEnd.getDayOfMonth());//Year,Mounth -1,Day
                                dpd.setMaxDate(c);
                            }


                            dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


                                    Log.i(TAG, "onDateSet: ");

                                    int month = monthOfYear + 1;

                                    if (checkOut != null) {
                                        checkOut.setText(dayOfMonth + "-" + month + "-" + year);
                                    }

                                }
                            });

                        } catch (Exception e) {
                            e.getMessage();

                        }
                        return true;*/

                        Calendar now = Calendar.getInstance();
                        Log.i(ContentValues.TAG, "onTouch:" + checkOut.getId());
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
                                    if (checkOut != null) {
                                        checkOut.setText(month + "-" + dayOfMonth + "-" + year);
                                    }

                                }
                            });

                        } catch (Exception e) {
                            e.getMessage();

                        }
                    }
                }
                break;
            case R.id.edtCheckIn1:

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut.getRight() - checkOut.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                       /* Calendar now = Calendar.getInstance();
                     *//*   if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = format.parse(CM.getSp(thisActivity, "serverDate", "").toString());
                                now.setTime(date);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }*//*

                        DatePickerDialog dpd = DatePickerDialog.newInstance(null,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));

                        try {
                            Calendar now1 = Calendar.getInstance();
                            int day = now1.get(Calendar.DATE);
                            int days = now1.getActualMaximum(Calendar.DAY_OF_MONTH);
                            ArrayList<Calendar> calendars = new ArrayList<>();


                            int month = now1.get(Calendar.MONTH) + 1;
                            String yearMonth = now1.get(Calendar.YEAR) + "/" + month + "/";
                            for (int i = day + 3; i <= days; i++) {
                                calendars.add(DateToCalendar(new Date(yearMonth + i)));
                            }


                            Calendar tArray[] = calendars.toArray(new Calendar[calendars.size()]);
                            dpd.setDisabledDays(tArray);
                            dpd.setMinDate(DateToCalendar(new Date(System.currentTimeMillis() - 1000)));
                            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


                            LocalDate monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);
                            // 2016-12-31
                            if ((day == (monthEnd.getDayOfMonth() - 2)) || (day == (monthEnd.getDayOfMonth() - 1)) || (day == (monthEnd.getDayOfMonth()))) {

                            } else {
                                Calendar c = Calendar.getInstance();
                                c.set(monthEnd.getYear(), monthEnd.getMonthOfYear() - 1, monthEnd.getDayOfMonth());//Year,Mounth -1,Day
                                dpd.setMaxDate(c);
                            }


                            dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


                                    Log.i(TAG, "onDateSet: ");

                                    int month = monthOfYear + 1;
                                    if (checkIn1 != null) {
                                        checkIn1.setText(dayOfMonth + "-" + month + "-" + year);
                                    }

                                }
                            });

                        } catch (Exception e) {
                            e.getMessage();

                        }
                        return true;*/
                        Calendar now = Calendar.getInstance();
                        Log.i(ContentValues.TAG, "onTouch:" + checkIn1.getId());
                        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(null,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));
                        try {


                            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
                            dpd.setMinDate(DateToCalendar(new Date(System.currentTimeMillis() - 1000)));
                            dpd.setOnDateSetListener(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                    Log.i(ContentValues.TAG, "onDateSet: ");
                                    dayOfMonth1 = dayOfMonth;
                                    month1 = monthOfYear;
                                    year1 = year;

                                    int month = monthOfYear + 1;
                                    if (checkIn1 != null) {


                                        checkIn1.setText(month + "-" + dayOfMonth + "-" + year);
                                        checkIn1.setSelection(checkIn1.getText().length());
                                    }

                                }
                            });

                        } catch (Exception e) {
                            e.getMessage();

                        }
                    }
                }
                break;

            case R.id.edtCheckout1:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut.getRight() - checkOut.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                     /*   Calendar now = Calendar.getInstance();
                     *//*   if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = format.parse(CM.getSp(thisActivity, "serverDate", "").toString());
                                now.setTime(date);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }*//*

                        DatePickerDialog dpd = DatePickerDialog.newInstance(null,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));

                        try {
                            Calendar now1 = Calendar.getInstance();
                            int day = now1.get(Calendar.DATE);
                            int days = now1.getActualMaximum(Calendar.DAY_OF_MONTH);
                            ArrayList<Calendar> calendars = new ArrayList<>();


                            int month = now1.get(Calendar.MONTH) + 1;
                            String yearMonth = now1.get(Calendar.YEAR) + "/" + month + "/";
                            for (int i = day + 3; i <= days; i++) {
                                calendars.add(DateToCalendar(new Date(yearMonth + i)));
                            }


                            Calendar tArray[] = calendars.toArray(new Calendar[calendars.size()]);
                            dpd.setDisabledDays(tArray);
                            dpd.setMinDate(DateToCalendar(new Date(System.currentTimeMillis() - 1000)));
                            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


                            LocalDate monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);
                            // 2016-12-31
                            if ((day == (monthEnd.getDayOfMonth() - 2)) || (day == (monthEnd.getDayOfMonth() - 1)) || (day == (monthEnd.getDayOfMonth()))) {

                            } else {
                                Calendar c = Calendar.getInstance();
                                c.set(monthEnd.getYear(), monthEnd.getMonthOfYear() - 1, monthEnd.getDayOfMonth());//Year,Mounth -1,Day
                                dpd.setMaxDate(c);
                            }


                            dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


                                    Log.i(TAG, "onDateSet: ");

                                    int month = monthOfYear + 1;
                                    if (checkOut1 != null) {
                                        checkOut1.setText(dayOfMonth + "-" + month + "-" + year);
                                    }

                                }
                            });

                        } catch (Exception e) {
                            e.getMessage();

                        }
                        return true;*/
                        Calendar now = Calendar.getInstance();
                        Log.i(ContentValues.TAG, "onTouch:" + checkOut1.getId());
                        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(null,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));

                        try {
                            //Calendar now1 = Calendar.getInstance();
                            //     now1.set(year1, month1, dayOfMonth1);// you can pass your custom date
                            // dpd.setMinDate(now1);

                            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


                            LocalDate monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);


                            dpd.setOnDateSetListener(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


                                    Log.i(ContentValues.TAG, "onDateSet: ");

                                    int month = monthOfYear + 1;
                                    if (checkOut1 != null) {
                                        checkOut1.setText(month + "-" + dayOfMonth + "-" + year);
                                    }

                                }
                            });

                        } catch (Exception e) {
                            e.getMessage();

                        }
                    }
                }
                break;


        }


        return false;
    }


    public static Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

        Toast.makeText(thisActivity, strings.toString(), Toast.LENGTH_LONG).show();

    }

    public void webCallCity() {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getCity(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCity(response);

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

    private void getResponseForCity(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            CM.setSp(thisActivity, "citydata", "");
            CM.setSp(thisActivity, "citydata", response);


            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (jsonObject.optString("ResponseObject") != null) {
                        JSONObject object = new JSONObject(jsonObject.optString("ResponseObject"));
                        //    jsonArray.length()
                        JSONObject object1 = new JSONObject(object.optString("citystatefi"));
                        int count = jsonObject.optInt("TotalRecord");
                        for (int i = 1; i <= count; i++) {
                            pojoCity country = new pojoCity();
                            JSONObject jsonArray1 = new JSONObject(object1.optString("" + i));
                            //  JSONObject object=new JSONObject(jsonArray1.optString(""))
                            country.setId(jsonArray1.optString("cityid"));
                            //country.setPrice(jsonArray1.optString("price"));
                            //country.setCategory(jsonArray1.optString("category"));
                            country.setName(jsonArray1.optString("name"));
                            country.setState_id(jsonArray1.optString("stateid"));
                            pojoCities.add(country);

                        }

                       /* JSONArray jsonArray = new JSONArray(jsonObject.optString("ResponseObject"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            pojoCity country = new pojoCity();
                            country.setId(jsonArray.getJSONObject(i).optString("id"));
                            country.setPrice(jsonArray.getJSONObject(i).optString("price"));
                            country.setCategory(jsonArray.getJSONObject(i).optString("category"));
                            country.setName(jsonArray.getJSONObject(i).optString("name"));
                            country.setState_id(jsonArray.getJSONObject(i).optString("state_id"));
                            pojoCities.add(country);
                        }*/
                    }
                    AutocompleteAdapter adptCountry1 = new AutocompleteAdapter(thisActivity, R.layout.conntylayout, R.id.textViewSpinner, pojoCities);
                    destiCity.setThreshold(3);
                    destiCity.setAdapter(adptCountry1);
                    //   trapickupCity.setAdapter(adptCountry1);
                    // trapickupCity.setThreshold(1);
                    //  finalCity.setAdapter(adptCountry1);
                    //  finalCity.setThreshold(1);

                  /*  pojoCity cityPojo = new pojoCity();
                    cityPojo.setId(cityId);
                    int indexCity = pojoCities.indexOf(cityPojo);
                    Log.i(TAG, "getResponseForCity: " + indexCity);
                    destiCity.setText(pojoCities.get(indexCity).getName());*/


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

    public void webCallState() {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getState(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForState(response);

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


    private void getResponseForState(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {

            CM.setSp(thisActivity, "statedata", "");
            CM.setSp(thisActivity, "statedata", response);


            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (jsonObject.optString("ResponseObject") != null) {
                        JSONArray jsonArray = new JSONArray(jsonObject.optString("ResponseObject"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            pojoState pojoState = new pojoState();
                            pojoState.setId(jsonArray.getJSONObject(i).optString("id"));
                            pojoState.setCountry_id(jsonArray.getJSONObject(i).optString("country_id"));
                            pojoState.setState_name(jsonArray.getJSONObject(i).optString("state_name"));
                            pojoStateArrayList.add(pojoState);
                        }
                    }

                   /* for (int i = 0; i < pojoStateArrayList.size(); i++) {

                        person = new Person(pojoStateArrayList.get(i).getState_name(), pojoStateArrayList.get(i).getId());
                        pojoStates.add(person);

                    }*/


                   /* pojoState statePojo = new pojoState();
                    statePojo.setId(stateId);
                    int indexState = pojoStateArrayList.indexOf(statePojo);
                    destiState.setText(pojoStateArrayList.get(indexState).getState_name());*/


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


    public void webCallCountry() {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getCountry(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCountry(response);

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

    private void getResponseForCountry(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {

            CM.setSp(thisActivity, "countrydata", "");
            CM.setSp(thisActivity, "countrydata", response);


            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (jsonObject.optString("ResponseObject") != null) {
                        JSONArray jsonArray = new JSONArray(jsonObject.optString("ResponseObject"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            pojoCountry country = new pojoCountry();
                            country.setCountry_cod(jsonArray.getJSONObject(i).optString("country_cod"));
                            country.setCountry_name(jsonArray.getJSONObject(i).optString("country_name"));
                            country.setId(jsonArray.getJSONObject(i).optString("id"));
                            countryArrayList.add(country);
                        }

                        countryArrayList.size();
                    }
                   /* pojoCountry countryPojo = new pojoCountry();
                    countryPojo.setId(countryId);
                    int indexCountry = countryArrayList.indexOf(countryPojo);
                    destiCountry.setText(countryArrayList.get(indexCountry).getCountry_name());*/

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


    public void webGetHotel(ArrayList<pojoPackage> pojoPackages) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getHotel(v, pojoPackages, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getGetPackage(response);

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

    private void getGetPackage(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (jsonObject.optString("response_object") != null) {
                        listener.onItemClick(true);
                        CM.showToast(jsonObject.optString("response_object"), thisActivity);

                        refId.setText("");
                        totBudget.setText("");
                        numberPicker.setValue(0);
                        childBelow.setValue(0);
                        singleRoom.setText("");
                        doubleRoom.setText("");
                        tripalRoom.setText("");
                        childWithbed.setText("");
                        childWithoutbed.setText("");
                        spinnerHotelRating.setSelection(0);
                        spinnerHotelCatMain.setSelection(0);
                        spinnerMealPlane.setSelection(0);
                        destiLocality.setText("");
                        destiCity.setText("");
                        destiState.setText("");
                        destiCountry.setText("");
                        checkIn.setText("");
                        checkOut.setText("");
                        edtComment.setText("");

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
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.filter).setVisible(false);

    }
}
