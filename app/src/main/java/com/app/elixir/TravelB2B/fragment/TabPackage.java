package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.AutocompleteAdapter;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnApiDataChange;
import com.app.elixir.TravelB2B.model.Person;
import com.app.elixir.TravelB2B.model.PojoMyResponse;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplEditText;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.numberPicker.NumberPicker;
import com.app.elixir.TravelB2B.pojos.pojoCity;
import com.app.elixir.TravelB2B.pojos.pojoCountry;
import com.app.elixir.TravelB2B.pojos.pojoPackage;
import com.app.elixir.TravelB2B.pojos.pojoState;
import com.app.elixir.TravelB2B.pojos.pojoStayReq;
import com.app.elixir.TravelB2B.pojos.pojoTransportReq;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.utils.MultiSelectionSpinner;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;
import com.silencedut.expandablelayout.ExpandableLayout;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.app.elixir.TravelB2B.R.id.btnRemove1;
import static com.app.elixir.TravelB2B.R.id.btnRemove2;
import static com.app.elixir.TravelB2B.R.id.edtCheckOut;


/**
 * Created by NetSupport on 02-06-2017.
 */

public class TabPackage extends Fragment implements View.OnClickListener, View.OnTouchListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener, View.OnFocusChangeListener {
    private static final String TAG = "TabPackage";
    Activity thisActivity;
    MtplButton btnAddAnother, btnSubmit;
    private LinearLayout parentLinearLayout, parentLinearLayout1;
    private ExpandableLayout expandableLayout;
    private NestedScrollView rootScrollView;
    NestedScrollView childScrollview;
    private MtplEditText checkIn, checkOut;
    int i;
    private MtplEditText edtlocality;
    private View rowViewMain;
    MtplEditText edtStopLocality, edtStopLocality5, edtStopLocality4, edtStopLocality3, edtStopLocality2, edtStopLocality1;
    ArrayList<String> strings;
    MtplEditText checkIn1, checkOut1;
    MtplEditText checkIn2, checkOut2;
    MtplEditText checkIn3, checkOut3;
    MtplEditText checkIn4, checkOut4;
    MtplEditText checkIn5, checkOut5;


    MtplEditText edtStopState, edtStopState1, edtStopState2, edtStopState3, edtStopState4;
    private ImageView imageView1, imageView2, imageView3, imageView4;
    MtplEditText refId, totBudget;
    NumberPicker numberPicker, childBelow;
    ImageView imageViewNoOfRoomImg;
    MtplEditText singleRoom, doubleRoom, tripalRoom, childWithbed, childWithoutbed;
    Spinner spinnerHotelRating, spinnerMealPlane, spinnerTransport;
    MultiSelectionSpinner spinnerHotelCat;
    MtplEditText destiState, destiCountry, destiLocality;
    AutoCompleteTextView destiCity, trapickupCity, finalCity;
    AutoCompleteTextView edtStopCity, edtStopCity1, edtStopCity2, edtStopCity3, edtStopCity4;
    ArrayList<pojoCity> pojoCities;
    ArrayList<pojoCountry> countryArrayList;
    ArrayList<pojoState> pojoStateArrayList;
    Person person;
    ArrayList<Person> pojoStates;
    String transcountryId, transcityId, transstateId;
    String finalcityId, finalstateId;
    ArrayList<String> stringArrayList, stringArrayListStayReq;
    String countryId, cityId, stateId;
    MtplEditText transStatrDate, transEndDate, pickupLocality, pickupState, finalLocality, finalState, edtComment;
    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;
    View rootView;
    MultiSelectionSpinner spinnerHotelCatMain;
    MtplEditText edtStopStateID4, edtStopCityID4, edtStopStateID5, edtStopCityID5, edtStopStateID3, edtStopCityID3, edtStopStateID2, edtStopCityID2, edtStopStateID1, edtStopCityID1;

    private int dayOfMonth1;
    private int month1;
    private int year1;
    private View rowView;
    AutocompleteAdapter adptCountry1;
    MtplButton btnAddStop;
    MtplEditText cityId1, stateId1, countryId1;
    MtplEditText cityId2, stateId2, countryId2;
    MtplEditText cityId3, stateId3, countryId3;
    MtplEditText cityId4, stateId4, countryId4;
    MtplEditText cityId5, stateId5, countryId5;


    int j = 0;

    MtplEditText singleRoom1, doubleRoom1, tripalRoom1, childWithbed1, childWithoutbed1, destiState1, destiCountry1, destiLocality1;
    MtplEditText singleRoom2, doubleRoom2, tripalRoom2, childWithbed2, childWithoutbed2, destiState2, destiCountry2, destiLocality2;
    MtplEditText singleRoom3, doubleRoom3, tripalRoom3, childWithbed3, childWithoutbed3, destiState3, destiCountry3, destiLocality3;
    MtplEditText singleRoom4, doubleRoom4, tripalRoom4, childWithbed4, childWithoutbed4, destiState4, destiCountry4, destiLocality4;
    MtplEditText singleRoom5, doubleRoom5, tripalRoom5, childWithbed5, childWithoutbed5, destiState5, destiCountry5, destiLocality5;


    MultiSelectionSpinner spinnerHotelCat1;
    MultiSelectionSpinner spinnerHotelCat2;
    MultiSelectionSpinner spinnerHotelCat3;
    MultiSelectionSpinner spinnerHotelCat4;
    MultiSelectionSpinner spinnerHotelCat5;

    Spinner spinnerMealPlane1, spinnerHotelRating1;
    Spinner spinnerMealPlane2, spinnerHotelRating2;
    Spinner spinnerMealPlane3, spinnerHotelRating3;
    Spinner spinnerMealPlane4, spinnerHotelRating4;
    Spinner spinnerMealPlane5, spinnerHotelRating5;

    AutoCompleteTextView destiCity1;
    AutoCompleteTextView destiCity2;
    AutoCompleteTextView destiCity3;
    AutoCompleteTextView destiCity4;
    AutoCompleteTextView destiCity5;

    private OnApiDataChange listener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (OnApiDataChange) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.placeReq));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rowViewMain = inflater.inflate(R.layout.tabpackage, container, false);
        thisActivity = getActivity();

        initView(rowViewMain);
        setHasOptionsMenu(true);


        return rowViewMain;


    }

    private void initView(View view) {


        countryArrayList = new ArrayList<>();
        pojoCities = new ArrayList<>();
        pojoStateArrayList = new ArrayList<>();
        stringArrayList = new ArrayList<>();
        stringArrayListStayReq = new ArrayList<>();
        destiCity = (AutoCompleteTextView) view.findViewById(R.id.edtDestinationCity);

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
        destiState = (MtplEditText) view.findViewById(R.id.edtDestinationState);
        destiCountry = (MtplEditText) view.findViewById(R.id.edtDestinationCountry);
        destiLocality = (MtplEditText) view.findViewById(R.id.edtDestinationLocality);
        destiState.setEnabled(false);
        destiCountry.setEnabled(false);
        checkIn = (MtplEditText) view.findViewById(R.id.edtCheckIn);
        checkOut = (MtplEditText) view.findViewById(edtCheckOut);
        btnAddAnother = (MtplButton) view.findViewById(R.id.btnAddAnother);


        //Transport Req
        spinnerTransport = (Spinner) view.findViewById(R.id.spinnerTransport);
        transStatrDate = (MtplEditText) view.findViewById(R.id.transStatrDate);
        transEndDate = (MtplEditText) view.findViewById(R.id.transEndDate);
        pickupLocality = (MtplEditText) view.findViewById(R.id.pickupLocality);
        trapickupCity = (AutoCompleteTextView) view.findViewById(R.id.trapickupCity);
        pickupState = (MtplEditText) view.findViewById(R.id.trapickupState);
        finalLocality = (MtplEditText) view.findViewById(R.id.finalLocality);
        finalCity = (AutoCompleteTextView) view.findViewById(R.id.finalCity);
        finalState = (MtplEditText) view.findViewById(R.id.finalState);
        //destiCountry = (MtplEditText) view.findViewById(R.id.edtDestinationCountry);

        transStatrDate = (MtplEditText) view.findViewById(R.id.transStatrDate);
        transEndDate = (MtplEditText) view.findViewById(R.id.transEndDate);

        transStatrDate.setOnClickListener(this);
        transEndDate.setOnClickListener(this);

        btnAddStop = (MtplButton) view.findViewById(R.id.btnAddStop);

        //Comment
        edtComment = (MtplEditText) view.findViewById(R.id.edtComment);


        btnSubmit = (MtplButton) view.findViewById(R.id.btnSubmit);

        ExpandableLayout expandableLayout1 = (ExpandableLayout) view.findViewById(R.id.expView1);
        ExpandableLayout expandableLayout2 = (ExpandableLayout) view.findViewById(R.id.expView2);
        ExpandableLayout expandableLayout3 = (ExpandableLayout) view.findViewById(R.id.expView3);
        ExpandableLayout expandableLayout4 = (ExpandableLayout) view.findViewById(R.id.expView4);
        ExpandableLayout expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandablrNoOfRoom);

        imageViewNoOfRoomImg = (ImageView) view.findViewById(R.id.expandablrNoOfRoomImg);
        imageView1 = (ImageView) view.findViewById(R.id.expImg1);
        imageView2 = (ImageView) view.findViewById(R.id.expImg2);
        imageView3 = (ImageView) view.findViewById(R.id.expImg3);
        imageView4 = (ImageView) view.findViewById(R.id.expImg4);
        imageView1.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorLightGray));
        imageView1.setBackgroundResource(R.drawable.ic_add_black_24dp);
        imageView2.setBackgroundResource(R.drawable.ic_add_black_24dp);
        imageView3.setBackgroundResource(R.drawable.ic_add_black_24dp);
        imageView4.setBackgroundResource(R.drawable.ic_add_black_24dp);
        imageViewNoOfRoomImg.setBackgroundResource(R.drawable.ic_add_black_24dp);

        expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
            @Override
            public void onExpand(boolean b) {
                if (b) {
                    imageViewNoOfRoomImg.setBackgroundResource(R.drawable.ic_remove_black_24dp);

                } else {
                    imageViewNoOfRoomImg.setBackgroundResource(R.drawable.ic_add_black_24dp);
                }

            }
        });

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

        expandableLayout4.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
            @Override
            public void onExpand(boolean b) {
                if (b) {
                    imageView4.setBackgroundResource(R.drawable.ic_remove_black_24dp);
                } else {
                    imageView4.setBackgroundResource(R.drawable.ic_add_black_24dp);
                }

            }
        });
        parentLinearLayout1 = (LinearLayout) view.findViewById(R.id.parent_linear_layout1);

        parentLinearLayout = (LinearLayout) view.findViewById(R.id.parent_linear_layout);
        rootScrollView = (NestedScrollView) view.findViewById(R.id.rootScroolView);
        childScrollview = (NestedScrollView) view.findViewById(R.id.childScrollView);


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


        /*destiCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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


                                          *//*  for (int l = 0; l < countryArrayList.size(); l++) {

                                                if (pojoStateArrayList.get(k).getCountry_id().toString().equals(countryArrayList.get(l).getId().toString())) {

                                                    destiCountry.setText(countryArrayList.get(l).getCountry_name());
                                                    break;

                                                }


                                            }*//*


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
        });*/


        trapickupCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("your selected item", "" + pojoCities.get(position).getId());
                String statename = "";
                transcityId = pojoCities.get(position).getId();
                if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                    for (int i = 0; i < pojoStateArrayList.size(); i++) {
                        if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                            statename = pojoStateArrayList.get(i).getState_name();
                            transstateId = pojoStateArrayList.get(i).getId();
                            pickupState.setText(statename);


                            break;
                        } else {

                        }
                    }
                } else {
                    pickupState.setText("");
                }


            }
        });


      /*  trapickupCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    for (int i = 0; i < pojoCities.size(); i++) {
                        if (trapickupCity.getText().toString().equals(pojoCities.get(i).getName())) {
                            for (int j = 0; j < pojoCities.size(); j++) {
                                if (trapickupCity.getText().toString().equals(pojoCities.get(i).getName())) {
                                    String id = pojoCities.get(i).getState_id();
                                    for (int k = 0; k < pojoStateArrayList.size(); k++) {
                                        if (id.equals(pojoStateArrayList.get(k).getId().toString())) {
                                            String statename = pojoStateArrayList.get(k).getState_name();
                                            pickupState.setText(statename);


                                            for (int l = 0; l < countryArrayList.size(); l++) {

                                                if (pojoStateArrayList.get(k).getCountry_id().toString().equals(countryArrayList.get(l).getId().toString())) {

                                                    destiCountry.setText(countryArrayList.get(l).getCountry_name());
                                                    //   transcountryId = countryArrayList.get(l).getId();
                                                    break;

                                                }


                                            }


                                            break;
                                        } else {


                                        }

                                    }


                                } else {
                                    pickupState.setText("");
                                    // destiCountry.setText("");

                                }


                            }


                            break;
                        } else {
                            trapickupCity.setText("");
                            pickupState.setText("");


                        }
                    }

                    if (pojoCities != null && pojoCities.size() == 0) {
                        trapickupCity.setText("");
                        pickupState.setText("");

                    }

                } else {


                }
            }
        });*/


        finalCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("your selected item", "" + pojoCities.get(position).getId());
                String statename = "";
                finalcityId = pojoCities.get(position).getId();
                if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                    for (int i = 0; i < pojoStateArrayList.size(); i++) {
                        if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                            statename = pojoStateArrayList.get(i).getState_name();
                            finalstateId = pojoStateArrayList.get(i).getId();
                            finalState.setText(statename);


                            break;
                        } else {

                        }
                    }
                } else {
                    finalState.setText("");
                }


            }
        });


       /* finalCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    for (int i = 0; i < pojoCities.size(); i++) {
                        if (finalCity.getText().toString().equals(pojoCities.get(i).getName())) {
                            for (int j = 0; j < pojoCities.size(); j++) {
                                if (finalCity.getText().toString().equals(pojoCities.get(i).getName())) {
                                    String id = pojoCities.get(i).getState_id();
                                    for (int k = 0; k < pojoStateArrayList.size(); k++) {
                                        if (id.equals(pojoStateArrayList.get(k).getId().toString())) {
                                            String statename = pojoStateArrayList.get(k).getState_name();
                                            finalState.setText(statename);


                                            break;
                                        } else {


                                        }

                                    }


                                } else {
                                    finalState.setText("");
                                    // destiCountry.setText("");

                                }


                            }


                            break;
                        } else {
                            finalCity.setText("");
                            finalState.setText("");


                        }
                    }

                    if (pojoCities != null && pojoCities.size() == 0) {
                        finalCity.setText("");
                        finalState.setText("");

                    }

                } else {


                }
            }
        });*/


        checkIn.setOnTouchListener(this);
        checkOut.setOnTouchListener(this);
        transStatrDate.setOnClickListener(this);
        transStatrDate.setOnTouchListener(this);
        transEndDate.setOnTouchListener(this);
        btnSubmit.setOnClickListener(this);
        btnAddAnother.setOnClickListener(this);
        btnAddStop.setOnClickListener(this);


        strings = new ArrayList<>();

        if (CM.isInternetAvailable(thisActivity)) {

          /*  final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {*/
            webCallCity();

            /*if (CM.getSp(thisActivity, "citydata", "").toString().equals("")) {

            } else {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getResponseForCity(CM.getSp(thisActivity, "citydata", "").toString());
                    }
                }, 100);

                final Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getResponseForState(CM.getSp(thisActivity, "statedata", "").toString());
                    }
                }, 150);


                final Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getResponseForCountry(CM.getSp(thisActivity, "countrydata", "").toString());
                    }
                }, 200);


            }*/
            // }
            //   }, 100);


        } else

        {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
        }

    }

    public void onAddField(View v) {

        try {

            LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            if (j == 0) {
                setPositionForStayReq("1", inflater);
            } else if (j == 1) {
                setPositionForStayReq("2", inflater);

            } else if (j == 2) {

                setPositionForStayReq("3", inflater);
            } else if (j == 3) {

                setPositionForStayReq("4", inflater);
            } else if (j == 4) {
                setPositionForStayReq("5", inflater);
            }


            if (j > 4) {


                if (!stringArrayListStayReq.contains("1")) { //&& (stringArrayList.contains("2") && stringArrayList.contains("3") && stringArrayList.contains("4") && stringArrayList.contains("5"))
                    Log.i(TAG, "onAddField: 1");
                    setPositionForStayReq("1", inflater);
                } else if (!stringArrayListStayReq.contains("2")) { //&& (stringArrayList.contains("1") && stringArrayList.contains("3") && stringArrayList.contains("4") && stringArrayList.contains("5"))
                    Log.i(TAG, "onAddField: 2");
                    setPositionForStayReq("2", inflater);
                } else if (!stringArrayListStayReq.contains("3")) { //&& (stringArrayList.contains("1") && stringArrayList.contains("2") && stringArrayList.contains("4") && stringArrayList.contains("5"))
                    Log.i(TAG, "onAddField: 3");
                    setPositionForStayReq("3", inflater);
                } else if (!stringArrayListStayReq.contains("4")) { //&& (stringArrayList.contains("1") && stringArrayList.contains("2") && stringArrayList.contains("3") && stringArrayList.contains("5"))
                    Log.i(TAG, "onAddField: 4");
                    setPositionForStayReq("4", inflater);
                } else if (!stringArrayListStayReq.contains("5")) { //&& (stringArrayList.contains("1") && stringArrayList.contains("2") && stringArrayList.contains("3") && stringArrayList.contains("4"))
                    Log.i(TAG, "onAddField: 5");
                    setPositionForStayReq("5", inflater);
                }

            }


            //  parentLinearLayout.addView(rootView, parentLinearLayout1.getChildCount());


            CM.showToast("DESIGNATION ADDED", thisActivity);


        } catch (
                Exception e)

        {
            CM.showToast(e.getMessage(), thisActivity);
        }


    }

    public void onDelete(View v) {
        try {
            Log.i(TAG, "onDelete: ");
            //  parentLinearLayout.removeView((View) v.getParent());


            Log.i(TAG, "onDelete: ");

            switch (v.getId()) {
                case R.id.btnRemove1:


                    if (stringArrayListStayReq.contains("1")) {

                        int position = -1;
                        position = stringArrayListStayReq.indexOf("1");
                        if (position == -1) {
                            Log.e(TAG, "Object not found in List");
                        } else {
                            Log.i(TAG, "" + position);
                            stringArrayListStayReq.remove(position);
                            parentLinearLayout.removeView((View) v.getParent());
                        }

                    }


                    break;
                case R.id.btnRemove2:

                    if (stringArrayListStayReq.contains("2")) {
                        int position = -1;
                        position = stringArrayListStayReq.indexOf("2");
                        if (position == -1) {
                            Log.e(TAG, "Object not found in List");
                        } else {
                            Log.i(TAG, "" + position);
                            stringArrayListStayReq.remove(position);
                            parentLinearLayout.removeView((View) v.getParent());
                        }
                    }
                    break;
                case R.id.btnRemove3:
                    if (stringArrayListStayReq.contains("3")) {
                        int position = -1;
                        position = stringArrayListStayReq.indexOf("3");
                        if (position == -1) {
                            Log.e(TAG, "Object not found in List");
                        } else {
                            Log.i(TAG, "" + position);
                            stringArrayListStayReq.remove(position);
                            parentLinearLayout.removeView((View) v.getParent());
                        }
                    }
                    //   stringArrayList.remove(2);
                    break;
                case R.id.btnRemove4:
                    if (stringArrayListStayReq.contains("4")) {
                        int position = -1;
                        position = stringArrayListStayReq.indexOf("4");
                        if (position == -1) {
                            Log.e(TAG, "Object not found in List");
                        } else {
                            Log.i(TAG, "" + position);
                            stringArrayListStayReq.remove(position);
                            parentLinearLayout.removeView((View) v.getParent());
                        }
                    }

                    break;
                case R.id.btnRemove5:
                    if (stringArrayListStayReq.contains("5")) {
                        int position = -1;
                        position = stringArrayListStayReq.indexOf("5");
                        if (position == -1) {
                            Log.e(TAG, "Object not found in List");
                        } else {
                            Log.i(TAG, "" + position);
                            stringArrayListStayReq.remove(position);
                            parentLinearLayout.removeView((View) v.getParent());
                        }
                    }

                    break;
            }


            CM.showToast("DESIGNATION IS REMOVED", thisActivity);
        } catch (Exception e) {
            CM.showToast(e.getMessage(), thisActivity);
        }
    }


    public void onAddField1(View v) {

        try {
            LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (i == 0) {
                setPosition("1", inflater);
            } else if (i == 1) {
                setPosition("2", inflater);

            } else if (i == 2) {

                setPosition("3", inflater);
            } else if (i == 3) {

                setPosition("4", inflater);
            } else if (i == 4) {
                setPosition("5", inflater);
            }

            if (i > 4) {


                if (!stringArrayList.contains("1")) { //&& (stringArrayList.contains("2") && stringArrayList.contains("3") && stringArrayList.contains("4") && stringArrayList.contains("5"))
                    Log.i(TAG, "onAddField: 1");
                    setPosition("1", inflater);
                } else if (!stringArrayList.contains("2")) { //&& (stringArrayList.contains("1") && stringArrayList.contains("3") && stringArrayList.contains("4") && stringArrayList.contains("5"))
                    Log.i(TAG, "onAddField: 2");
                    setPosition("2", inflater);
                } else if (!stringArrayList.contains("3")) { //&& (stringArrayList.contains("1") && stringArrayList.contains("2") && stringArrayList.contains("4") && stringArrayList.contains("5"))
                    Log.i(TAG, "onAddField: 3");
                    setPosition("3", inflater);
                } else if (!stringArrayList.contains("4")) { //&& (stringArrayList.contains("1") && stringArrayList.contains("2") && stringArrayList.contains("3") && stringArrayList.contains("5"))
                    Log.i(TAG, "onAddField: 4");
                    setPosition("4", inflater);
                } else if (!stringArrayList.contains("5")) { //&& (stringArrayList.contains("1") && stringArrayList.contains("2") && stringArrayList.contains("3") && stringArrayList.contains("4"))
                    Log.i(TAG, "onAddField: 5");
                    setPosition("5", inflater);
                }

            }


            Log.i(TAG, "onAddField: " + parentLinearLayout1.getChildCount());


        } catch (Exception e) {
            CM.showToast(e.getMessage(), thisActivity);
        }


    }

    public void onDelete1(View v) {
        try {


            Log.i(TAG, "onDelete: ");

            switch (v.getId()) {
                case R.id.btnRemoveStop:


                    if (stringArrayList.contains("1")) {
                        //    stringArrayList.remove(0);
                        int position = -1;
                        position = stringArrayList.indexOf("1");
                        if (position == -1) {
                            Log.e(TAG, "Object not found in List");
                        } else {
                            Log.i(TAG, "" + position);
                            stringArrayList.remove(position);
                            parentLinearLayout1.removeView((View) v.getParent());
                        }

                        // parentLinearLayout1.removeView((View) v.getParent());
                    }
                    break;
                case R.id.btnRemoveStop1:

                    if (stringArrayList.contains("2")) {
                        // stringArrayList.remove(1);
                        int position = -1;
                        position = stringArrayList.indexOf("2");
                        if (position == -1) {
                            Log.e(TAG, "Object not found in List");
                        } else {
                            Log.i(TAG, "" + position);
                            stringArrayList.remove(position);
                            parentLinearLayout1.removeView((View) v.getParent());
                        }

                        //  parentLinearLayout1.removeView((View) v.getParent());
                    }
                    break;
                case R.id.btnRemoveStop2:
                    if (stringArrayList.contains("3")) {
                        //  stringArrayList.remove(2);

                        int position = -1;
                        position = stringArrayList.indexOf("3");
                        if (position == -1) {
                            Log.e(TAG, "Object not found in List");
                        } else {
                            Log.i(TAG, "" + position);
                            stringArrayList.remove(position);
                            parentLinearLayout1.removeView((View) v.getParent());
                        }

                        //  parentLinearLayout1.removeView((View) v.getParent());
                    }
                    //   stringArrayList.remove(2);
                    break;
                case R.id.btnRemoveStop3:
                    if (stringArrayList.contains("4")) {
                        //stringArrayList.remove(3);
                        int position = -1;
                        position = stringArrayList.indexOf("4");
                        if (position == -1) {
                            Log.e(TAG, "Object not found in List");
                        } else {
                            Log.i(TAG, "" + position);
                            stringArrayList.remove(position);
                            parentLinearLayout1.removeView((View) v.getParent());
                        }


                        // parentLinearLayout1.removeView((View) v.getParent());
                    }

                    break;
                case R.id.btnRemoveStop4:
                    if (stringArrayList.contains("5")) {


                        int position = -1;
                        position = stringArrayList.indexOf("5");
                        if (position == -1) {
                            Log.e(TAG, "Object not found in List");
                        } else {
                            Log.i(TAG, "" + position);
                            stringArrayList.remove(position);
                            parentLinearLayout1.removeView((View) v.getParent());
                        }


                    }

                    break;

            }


            CM.showToast("DESIGNATION IS REMOVED", thisActivity);
        } catch (Exception e) {

            CM.showToast(e.getMessage(), thisActivity);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnAddStop:
                onAddField1(view);
                break;
            case R.id.btnRemoveStop:
                onDelete1(view);
                break;
            case R.id.btnRemoveStop1:
                onDelete1(view);
                break;
            case R.id.btnRemoveStop2:
                onDelete1(view);
                break;
            case R.id.btnRemoveStop3:
                onDelete1(view);
                break;
            case R.id.btnRemoveStop4:
                onDelete1(view);
                break;

            case R.id.btnAddAnother:
                onAddField(view);
                break;
            case btnRemove1:
                onDelete(view);
                break;
            case R.id.btnRemove2:
                onDelete(view);
                break;
            case R.id.btnRemove3:
                onDelete(view);
                break;
            case R.id.btnRemove4:
                onDelete(view);
                break;
            case R.id.btnRemove5:
                onDelete(view);
                break;
            case R.id.btnSubmit:


                if (!refId.getText().toString().equals("")) {


                    if (!totBudget.getText().toString().equals("")) {


                        if (!destiCity.getText().toString().equals("")) {


                            if (!checkIn.getText().toString().equals("")) {

                                if (!checkOut.getText().toString().equals("")) {
                                    if (!transStatrDate.getText().toString().equals("")) {
                                        if (!transEndDate.getText().toString().equals("")) {
                                          /*  if (!pickupLocality.getText().toString().equals("")) {*/

                                            if (!trapickupCity.getText().toString().equals("")) {

                                                if (!pickupState.getText().toString().equals("")) {

                                                    if (!finalCity.getText().toString().equals("")) {


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


                                                        //Transport Req
                                                        JSONObject transportReqObj = new JSONObject();
                                                        JSONArray transportReqArray = new JSONArray();
                                                        JSONObject obj3 = new JSONObject();
                                                        try {
                                                            obj3.put("transport", spinnerTransport.getSelectedItem());
                                                            obj3.put("StatrDate", transStatrDate.getText().toString());
                                                            obj3.put("EndDate", transEndDate.getText().toString());
                                                            obj3.put("Locality", pickupLocality.getText().toString());
                                                            obj3.put("pickupCity", trapickupCity.getText().toString());
                                                            obj3.put("pickupState", pickupState.getText().toString());
                                                            obj3.put("finalLocality", finalLocality.getText().toString());
                                                            obj3.put("finalCity", finalCity.getText().toString());
                                                            obj3.put("finalState", finalState.getText().toString());
                                                            transportReqArray.put(obj3);
                                                            generalReqObj.put("Transport_Req", transportReqArray);
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

                                                        for (int i1 = 0; i1 < words.size(); i1++) {

                                                            if (words.size() == 1) {
                                                                stringBuilder.append(CM.setHotelCatRev(words.get(i1).toString()));
                                                            } else {
                                                                stringBuilder.append(",");
                                                                stringBuilder.append(CM.setHotelCatRev(words.get(i1).toString()));

                                                            }
                                                        }
                                                        String stateList = stringBuilder.toString().replace("[", "").replace("]", "")
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
                                                        aPackage.setTransport_requirement(CM.setVichelRev(spinnerTransport.getSelectedItem().toString()));
                                                        aPackage.setStart_date(transStatrDate.getText().toString());
                                                        aPackage.setEnd_date(transEndDate.getText().toString());
                                                        aPackage.setPickup_locality(pickupLocality.getText().toString());
                                                        aPackage.setPickup_city_name(trapickupCity.getText().toString());
                                                        aPackage.setPickup_city_id(transcityId);
                                                        aPackage.setPickup_state_id(transstateId);
                                                        aPackage.setPickup_state_name(pickupState.getText().toString());
                                                        aPackage.setPickup_country_name(pickupLocality.getText().toString());
                                                        aPackage.setPickup_country_id(countryId);
                                                        aPackage.setFinalLocality(finalLocality.getText().toString());
                                                        aPackage.setP_final_city_name(finalCity.getText().toString());
                                                        aPackage.setP_final_state_id(finalstateId);
                                                        aPackage.setP_final_state_name(finalState.getText().toString());
                                                        aPackage.setP_final_city_id(finalcityId);
                                                        aPackage.setComment(edtComment.getText().toString());
                                                        pojoPackages.add(aPackage);

                                                        ArrayList<pojoStayReq> pojoStayReqs = new ArrayList<>();
                                                        for (int i1 = 0; i1 < stringArrayListStayReq.size(); i1++) {
                                                            pojoStayReq stayReq = new pojoStayReq();

                                                            if (i1 == 0) {


                                                                stayReq.setSingleRoom(singleRoom1.getText().toString());
                                                                stayReq.setDoubleRoom(doubleRoom1.getText().toString());
                                                                stayReq.setTripleRoom(tripalRoom1.getText().toString());
                                                                stayReq.setChildwithbed(childWithbed1.getText().toString());
                                                                stayReq.setChildwithouthbed(childWithoutbed1.getText().toString());
                                                                stayReq.setHotelRating(CM.getHotelRating(spinnerHotelRating1.getSelectedItem().toString()));


                                                                List<String> selectedStrings = spinnerHotelCat1.getSelectedStrings();
                                                                StringBuilder stringBuilder1 = new StringBuilder();
                                                                for (int j1 = 0; j1 < selectedStrings.size(); j1++) {


                                                                    if (selectedStrings.size() == 1) {
                                                                        stringBuilder1.append(CM.setHotelCatRev(selectedStrings.get(j1).toString()));
                                                                    } else {
                                                                        stringBuilder1.append(",");
                                                                        stringBuilder1.append(CM.setHotelCatRev(selectedStrings.get(j1).toString()));
                                                                    }
                                                                }
                                                                String stateList1 = stringBuilder1.toString().replace("[", "").replace("]", "")
                                                                        .replace(", ", ",");

                                                                stayReq.setHotlCat(stateList1);
                                                                stayReq.setMealPlane(CM.getMealPlaneRev(spinnerMealPlane1.getSelectedItem().toString()));
                                                                stayReq.setDestiCity(cityId1.getText().toString());
                                                                stayReq.setDestiState(stateId1.getText().toString());
                                                                stayReq.setDestiCountry(countryId1.getText().toString());
                                                                stayReq.setLocality(destiLocality1.getText().toString());
                                                                stayReq.setCheckIn(checkIn1.getText().toString());
                                                                stayReq.setCheckOut(checkOut1.getText().toString());
                                                                pojoStayReqs.add(stayReq);

                                                            } else if (i1 == 1) {

                                                                stayReq.setSingleRoom(singleRoom2.getText().toString());
                                                                stayReq.setDoubleRoom(doubleRoom2.getText().toString());
                                                                stayReq.setTripleRoom(tripalRoom2.getText().toString());
                                                                stayReq.setChildwithbed(childWithbed2.getText().toString());
                                                                stayReq.setChildwithouthbed(childWithoutbed2.getText().toString());
                                                                stayReq.setHotelRating(CM.getHotelRating(spinnerHotelRating2.getSelectedItem().toString()));


                                                                List<String> selectedStrings = spinnerHotelCat2.getSelectedStrings();
                                                                StringBuilder stringBuilder1 = new StringBuilder();

                                                                for (int j1 = 0; j1 < selectedStrings.size(); j1++) {

                                                                    // Log.i(TAG, "onClick: " + CM.setHotelCatRev(selectedStrings.get(j1).toString()));

                                                                    if (selectedStrings.size() == 1) {
                                                                        stringBuilder1.append(CM.setHotelCatRev(selectedStrings.get(j1).toString()));

                                                                    } else {
                                                                        stringBuilder1.append(",");
                                                                        stringBuilder1.append(CM.setHotelCatRev(selectedStrings.get(j1).toString()));

                                                                    }

                                                                }
                                                                String stateList1 = stringBuilder1.toString().replace("[", "").replace("]", "")
                                                                        .replace(", ", ",");


                                                                stayReq.setHotlCat(stateList1);
                                                                stayReq.setMealPlane(CM.getMealPlaneRev(spinnerMealPlane2.getSelectedItem().toString()));
                                                                stayReq.setDestiCity(cityId2.getText().toString());
                                                                stayReq.setDestiState(stateId2.getText().toString());
                                                                stayReq.setDestiCountry(countryId2.getText().toString());
                                                                stayReq.setLocality(destiLocality2.getText().toString());
                                                                stayReq.setCheckIn(checkIn2.getText().toString());
                                                                stayReq.setCheckOut(checkOut2.getText().toString());
                                                                pojoStayReqs.add(stayReq);

                                                            } else if (i1 == 2) {

                                                                stayReq.setSingleRoom(singleRoom3.getText().toString());
                                                                stayReq.setDoubleRoom(doubleRoom3.getText().toString());
                                                                stayReq.setTripleRoom(tripalRoom3.getText().toString());
                                                                stayReq.setChildwithbed(childWithbed3.getText().toString());
                                                                stayReq.setChildwithouthbed(childWithoutbed3.getText().toString());
                                                                stayReq.setHotelRating(CM.getHotelRating(spinnerHotelRating3.getSelectedItem().toString()));

                                                                List<String> selectedStrings = spinnerHotelCat3.getSelectedStrings();
                                                                StringBuilder stringBuilder1 = new StringBuilder();

                                                                for (int j1 = 0; j1 < selectedStrings.size(); j1++) {

                                                                    Log.i(TAG, "onClick: " + CM.setHotelCatRev(selectedStrings.get(j1).toString()));
                                                                    if (selectedStrings.size() == 1) {

                                                                        stringBuilder1.append(CM.setHotelCatRev(selectedStrings.get(j1).toString()));

                                                                    } else {
                                                                        stringBuilder1.append(",");
                                                                        stringBuilder1.append(CM.setHotelCatRev(selectedStrings.get(j1).toString()));
                                                                    }

                                                                }
                                                                String stateList1 = stringBuilder1.toString().replace("[", "").replace("]", "")
                                                                        .replace(", ", ",");

                                                                stayReq.setHotlCat(stateList1);
                                                                stayReq.setMealPlane(CM.getMealPlaneRev(spinnerMealPlane3.getSelectedItem().toString()));
                                                                stayReq.setDestiCity(cityId3.getText().toString());
                                                                stayReq.setDestiState(stateId3.getText().toString());
                                                                stayReq.setDestiCountry(countryId3.getText().toString());
                                                                stayReq.setLocality(destiLocality3.getText().toString());
                                                                stayReq.setCheckIn(checkIn3.getText().toString());
                                                                stayReq.setCheckOut(checkOut3.getText().toString());
                                                                pojoStayReqs.add(stayReq);

                                                            } else if (i1 == 3) {

                                                                stayReq.setSingleRoom(singleRoom4.getText().toString());
                                                                stayReq.setDoubleRoom(doubleRoom4.getText().toString());
                                                                stayReq.setTripleRoom(tripalRoom4.getText().toString());
                                                                stayReq.setChildwithbed(childWithbed4.getText().toString());
                                                                stayReq.setChildwithouthbed(childWithoutbed4.getText().toString());
                                                                stayReq.setHotelRating(CM.getHotelRating(spinnerHotelRating4.getSelectedItem().toString()));

                                                                List<String> selectedStrings = spinnerHotelCat3.getSelectedStrings();
                                                                StringBuilder stringBuilder1 = new StringBuilder();

                                                                for (int j1 = 0; j1 < selectedStrings.size(); j1++) {

                                                                    Log.i(TAG, "onClick: " + CM.setHotelCatRev(selectedStrings.get(j1).toString()));
                                                                    if (selectedStrings.size() == 1) {
                                                                        stringBuilder1.append(CM.setHotelCatRev(selectedStrings.get(j1).toString()));
                                                                    } else {
                                                                        stringBuilder1.append(",");
                                                                        stringBuilder1.append(CM.setHotelCatRev(selectedStrings.get(j1).toString()));

                                                                    }


                                                                }
                                                                String stateList1 = stringBuilder1.toString().replace("[", "").replace("]", "").replace(", ", ",");
                                                                stayReq.setHotlCat(stateList1);
                                                                stayReq.setMealPlane(CM.getMealPlaneRev(spinnerMealPlane4.getSelectedItem().toString()));
                                                                stayReq.setDestiCity(cityId4.getText().toString());
                                                                stayReq.setDestiState(stateId4.getText().toString());
                                                                stayReq.setDestiCountry(countryId4.getText().toString());
                                                                stayReq.setLocality(destiLocality4.getText().toString());
                                                                stayReq.setCheckIn(checkIn4.getText().toString());
                                                                stayReq.setCheckOut(checkOut4.getText().toString());
                                                                pojoStayReqs.add(stayReq);

                                                            } else if (i1 == 4) {

                                                                stayReq.setSingleRoom(singleRoom5.getText().toString());
                                                                stayReq.setDoubleRoom(doubleRoom5.getText().toString());
                                                                stayReq.setTripleRoom(tripalRoom5.getText().toString());
                                                                stayReq.setChildwithbed(childWithbed5.getText().toString());
                                                                stayReq.setChildwithouthbed(childWithoutbed5.getText().toString());
                                                                stayReq.setHotelRating(CM.getHotelRating(spinnerHotelRating5.getSelectedItem().toString()));

                                                                List<String> selectedStrings = spinnerHotelCat3.getSelectedStrings();
                                                                StringBuilder stringBuilder1 = new StringBuilder();

                                                                for (int j1 = 0; j1 < selectedStrings.size(); j1++) {

                                                                    Log.i(TAG, "onClick: " + CM.setHotelCatRev(selectedStrings.get(j1).toString()));
                                                                    if (selectedStrings.size() == 1) {
                                                                        stringBuilder1.append(CM.setHotelCatRev(selectedStrings.get(j1).toString()));

                                                                    } else {
                                                                        stringBuilder1.append(",");
                                                                        stringBuilder1.append(CM.setHotelCatRev(selectedStrings.get(j1).toString()));
                                                                    }

                                                                }
                                                                String stateList1 = stringBuilder1.toString().replace("[", "").replace("]", "")
                                                                        .replace(", ", ",");


                                                                stayReq.setHotlCat(stateList1);
                                                                stayReq.setMealPlane(CM.getMealPlaneRev(spinnerMealPlane5.getSelectedItem().toString()));
                                                                stayReq.setDestiCity(cityId5.getText().toString());
                                                                stayReq.setDestiState(stateId5.getText().toString());
                                                                stayReq.setDestiCountry(countryId5.getText().toString());
                                                                stayReq.setLocality(destiLocality5.getText().toString());
                                                                stayReq.setCheckIn(checkIn5.getText().toString());
                                                                stayReq.setCheckOut(checkOut5.getText().toString());
                                                                pojoStayReqs.add(stayReq);

                                                            }


                                                        }


                                                        ArrayList<pojoTransportReq> pojoTransportReqs = new ArrayList<>();


                                                        for (int i1 = 0; i1 < stringArrayList.size(); i1++) {

                                                            pojoTransportReq transportReq = new pojoTransportReq();


                                                            if (i1 == 0) {

                                                                transportReq.setLocality(edtStopLocality1.getText().toString());
                                                                transportReq.setCity(edtStopCityID1.getText().toString());
                                                                transportReq.setState(edtStopStateID1.getText().toString());
                                                                pojoTransportReqs.add(transportReq);

                                                            } else if (i1 == 1) {

                                                                transportReq.setLocality(edtStopLocality2.getText().toString());
                                                                transportReq.setCity(edtStopCityID2.getText().toString());
                                                                transportReq.setState(edtStopStateID2.getText().toString());
                                                                pojoTransportReqs.add(transportReq);

                                                            } else if (i1 == 2) {

                                                                transportReq.setLocality(edtStopLocality3.getText().toString());
                                                                transportReq.setCity(edtStopCityID3.getText().toString());
                                                                transportReq.setState(edtStopStateID3.getText().toString());
                                                                pojoTransportReqs.add(transportReq);


                                                            } else if (i1 == 3) {

                                                                transportReq.setLocality(edtStopLocality4.getText().toString());
                                                                transportReq.setCity(edtStopCityID4.getText().toString());
                                                                transportReq.setState(edtStopStateID4.getText().toString());
                                                                pojoTransportReqs.add(transportReq);

                                                            } else if (i1 == 4) {

                                                                transportReq.setLocality(edtStopLocality5.getText().toString());
                                                                transportReq.setCity(edtStopCityID5.getText().toString());
                                                                transportReq.setState(edtStopStateID5.getText().toString());
                                                                pojoTransportReqs.add(transportReq);

                                                            }

                                                        }
                                                        pojoStayReqs.size();
                                                        pojoTransportReqs.size();

                                                        if (CM.isInternetAvailable(thisActivity)) {
                                                            webGetPackage(pojoPackages, pojoStayReqs, pojoTransportReqs);
                                                        } else {
                                                            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
                                                        }


                                                        Log.i(TAG, "onClick: " + jsonObject1);

                                                    } else {

                                                        CM.showToast("Please select finale City", thisActivity);

                                                    }

                                                } else {
                                                    CM.showToast("Please select Pickup State", thisActivity);

                                                }
                                            } else {
                                                CM.showToast("Please select Pickup City", thisActivity);
                                            }
                                           /* } else {
                                                CM.showToast("Please select Pickup Locality.", thisActivity);

                                            }*/
                                        } else {
                                            CM.showToast("Please select end date.", thisActivity);

                                        }
                                    } else {
                                        CM.showToast("Please select Start date.", thisActivity);

                                    }


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
                break;

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
                        checkIn(checkIn);
                        return true;
                    }
                }
                break;
            case R.id.edtCheckOut:

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut.getRight() - checkOut.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkOut);
                        return true;
                    }
                }
                break;
            case R.id.edtCheckIn1:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn1.getRight() - checkIn1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkIn1);
                        return true;
                    }
                }
                break;
            case R.id.edtCheckout1:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut1.getRight() - checkOut1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkOut1);
                        return true;
                    }
                }
                break;
            case R.id.edtCheckIn2:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn2.getRight() - checkIn2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(checkIn2);
                        return true;
                    }
                }
                break;

            case R.id.edtCheckout2:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut2.getRight() - checkOut2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkOut2);
                        return true;
                    }
                }
                break;

            case R.id.edtCheckIn3:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn3.getRight() - checkIn3.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(checkIn3);
                        return true;
                    }
                }
                break;

            case R.id.edtCheckout3:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut3.getRight() - checkOut3.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkOut3);
                        return true;
                    }
                }
                break;

            case R.id.edtCheckIn4:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn4.getRight() - checkIn4.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(checkIn4);
                        return true;
                    }
                }
                break;

            case R.id.edtCheckout4:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut4.getRight() - checkOut4.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkOut4);
                        return true;
                    }
                }
                break;
            case R.id.edtCheckIn5:
                // final EditText checkIn5 = (EditText) rowViewMain.findViewById(R.id.checkin5);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn5.getRight() - checkIn5.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(checkIn5);
                        return true;
                    }
                }
                break;

            case R.id.edtCheckout5:
                // final EditText checkout5 = (EditText) rowViewMain.findViewById(R.id.checkout5);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut5.getRight() - checkOut5.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkOut5);
                        return true;
                    }
                }

            case R.id.transStatrDate:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (transStatrDate.getRight() - transStatrDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(transStatrDate);
                        return true;
                    }
                }
                break;

            case R.id.transEndDate:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (transEndDate.getRight() - transEndDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(transEndDate);
                        return true;
                    }
                }
                break;

        }


        return false;
    }


    /*@Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;


        Log.i(TAG, "onDateSet: " + view.getTag());
        switch (view.getId()) {
            case R.id.edtCheckIn:
                Log.i(TAG, "onDateSet: ");
                break;
            case R.id.edtCheckOut:
                Log.i(TAG, "onDateSet: ");
                break;

        }
       *//* if (editTextDate != null) {
            editTextDate.setText(dayOfMonth + "-" + month + "-" + year);
        }*//*
    }*/

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
                        /*JSONArray jsonArray = new JSONArray(jsonObject.optString("ResponseObject"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            pojoCity country = new pojoCity();
                            country.setId(jsonArray.getJSONObject(i).optString("id"));
                            country.setPrice(jsonArray.getJSONObject(i).optString("price"));
                            country.setCategory(jsonArray.getJSONObject(i).optString("category"));
                            country.setName(jsonArray.getJSONObject(i).optString("name"));
                            country.setState_id(jsonArray.getJSONObject(i).optString("state_id"));
                            pojoCities.add(country);
                        }*/

                        JSONObject object = new JSONObject(jsonObject.optString("ResponseObject"));
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

                    }
                    adptCountry1 = new AutocompleteAdapter(thisActivity, R.layout.conntylayout, R.id.textViewSpinner, pojoCities);
                    destiCity.setThreshold(3);
                    destiCity.setAdapter(adptCountry1);
                    trapickupCity.setAdapter(adptCountry1);
                    trapickupCity.setThreshold(3);
                    finalCity.setAdapter(adptCountry1);
                    finalCity.setThreshold(3);

                    webCallState();

                  /*pojoCity cityPojo = new pojoCity();
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
                    webCallCountry();

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


    public void checkIn(final EditText ed) {
        Calendar now = Calendar.getInstance();
        Log.i(ContentValues.TAG, "onTouch:" + ed.getId());
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
                    if (ed != null) {


                    /*dayOfMonth1 = dayOfMonth;
                    month1 = month;
                    year1 = year;*/
                        ed.setText(month + "-" + dayOfMonth + "-" + year);
                        ed.setSelection(ed.getText().length());
                    }

                }
            });

        } catch (Exception e) {
            e.getMessage();

        }


    }



   /* public void checkIn(final EditText ed) {
        Calendar now = Calendar.getInstance();
        Log.i(TAG, "onTouch:" + ed.getId());
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
                    if (ed != null) {
                        ed.setText(dayOfMonth + "-" + month + "-" + year);
                    }

                }
            });

        } catch (Exception e) {
            e.getMessage();

        }
    }
*/

    public void checkOut(final EditText edt) {

        Calendar now = Calendar.getInstance();
        Log.i(ContentValues.TAG, "onTouch:" + edt.getId());
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(null,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        try {
            // Calendar now1 = Calendar.getInstance();
            //  now1.set(year1, month1, dayOfMonth1);// you can pass your custom date
            //  dpd.setMinDate(now1);

            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


            LocalDate monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);


            dpd.setOnDateSetListener(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


                    Log.i(ContentValues.TAG, "onDateSet: ");

                    int month = monthOfYear + 1;
                    if (edt != null) {
                        edt.setText(month + "-" + dayOfMonth + "-" + year);
                    }

                }
            });

        } catch (Exception e) {
            e.getMessage();

        }
    }

   /* public void checkOut(final EditText edt) {

        Calendar now = Calendar.getInstance();
        Log.i(TAG, "onTouch:" + edt.getId());
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
                    if (edt != null) {
                        edt.setText(dayOfMonth + "-" + month + "-" + year);
                    }

                }
            });

        } catch (Exception e) {
            e.getMessage();

        }
    }*/


    public void webGetPackage(ArrayList<pojoPackage> pojoPackages, ArrayList<pojoStayReq> pojoStayReqs, ArrayList<pojoTransportReq> pojoTransportReqs) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getPackage(v, pojoPackages, pojoStayReqs, pojoTransportReqs, new OnVolleyHandler() {
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

                        spinnerTransport.setSelection(0);
                        transStatrDate.setText("");
                        transEndDate.setText("");
                        pickupLocality.setText("");
                        trapickupCity.setText("");
                        pickupState.setText("");
                        pickupLocality.setText("");
                        finalLocality.setText("");
                        finalCity.setText("");

                        finalState.setText("");
                        edtComment.setText("");


                        for (int i1 = 0; i1 < stringArrayListStayReq.size(); i1++) {

                            if (i1 == 0) {
                                singleRoom1.setText("");
                                doubleRoom1.setText("");
                                tripalRoom1.setText("");
                                childWithbed1.setText("");
                                childWithoutbed1.setText("");
                                spinnerHotelRating1.setSelection(0);
                                spinnerHotelCat1.setSelection(0);
                                spinnerMealPlane1.setSelection(0);

                                destiCity1.setText("");
                                destiState1.setText("");
                                destiCountry1.setText("");

                                cityId1.setText("");
                                stateId1.setText("");
                                countryId1.setText("");
                                destiLocality1.setText("");
                                checkIn1.setText("");
                                checkOut1.setText("");

                            } else if (i1 == 1) {

                                singleRoom2.setText("");
                                doubleRoom2.setText("");
                                tripalRoom2.setText("");
                                childWithbed2.setText("");
                                childWithoutbed2.setText("");
                                spinnerHotelRating2.setSelection(0);
                                spinnerHotelCat2.setSelection(0);
                                spinnerMealPlane2.setSelection(0);

                                destiCity2.setText("");
                                destiState2.setText("");
                                destiCountry2.setText("");


                                cityId2.setText("");
                                stateId2.setText("");
                                countryId2.setText("");
                                destiLocality2.setText("");
                                checkIn2.setText("");
                                checkOut2.setText("");

                            } else if (i1 == 2) {


                                singleRoom3.setText("");
                                doubleRoom3.setText("");
                                tripalRoom3.setText("");
                                childWithbed3.setText("");
                                childWithoutbed3.setText("");
                                spinnerHotelRating3.setSelection(0);
                                spinnerHotelCat3.setSelection(0);
                                spinnerMealPlane3.setSelection(0);

                                destiCity3.setText("");
                                destiState3.setText("");
                                destiCountry3.setText("");

                                cityId3.setText("");
                                stateId3.setText("");
                                countryId3.setText("");
                                destiLocality3.setText("");
                                checkIn3.setText("");
                                checkOut3.setText("");

                            } else if (i1 == 3) {

                                singleRoom4.setText("");
                                doubleRoom4.setText("");
                                tripalRoom4.setText("");
                                childWithbed4.setText("");
                                childWithoutbed4.setText("");
                                spinnerHotelRating4.setSelection(0);
                                spinnerHotelCat4.setSelection(0);
                                spinnerMealPlane4.setSelection(0);

                                destiCity4.setText("");
                                destiState4.setText("");
                                destiCountry4.setText("");


                                cityId4.setText("");
                                stateId4.setText("");
                                countryId4.setText("");
                                destiLocality4.setText("");
                                checkIn4.setText("");
                                checkOut4.setText("");

                            } else if (i1 == 4) {

                                singleRoom5.setText("");
                                doubleRoom5.setText("");
                                tripalRoom5.setText("");
                                childWithbed5.setText("");
                                childWithoutbed5.setText("");
                                spinnerHotelRating5.setSelection(0);
                                spinnerHotelCat5.setSelection(0);
                                spinnerMealPlane5.setSelection(0);

                                destiCity5.setText("");
                                destiState5.setText("");
                                destiCountry5.setText("");

                                cityId5.setText("");
                                stateId5.setText("");
                                countryId5.setText("");
                                destiLocality5.setText("");
                                checkIn5.setText("");
                                checkOut5.setText("");


                            }

                        }


                        for (int i1 = 0; i1 < stringArrayList.size(); i1++) {

                            if (i1 == 0) {
                                edtStopLocality1.setText("");
                                edtStopCityID1.setText("");
                                edtStopStateID1.setText("");


                            } else if (i1 == 1) {

                                edtStopLocality2.setText("");
                                edtStopCityID2.setText("");
                                edtStopStateID2.setText("");

                            } else if (i1 == 2) {

                                edtStopLocality3.setText("");
                                edtStopCityID3.setText("");
                                edtStopStateID3.setText("");

                            } else if (i1 == 3) {
                                edtStopLocality4.setText("");
                                edtStopCityID4.setText("");
                                edtStopStateID4.setText("");

                            } else if (i1 == 4) {

                                edtStopLocality5.setText("");
                                edtStopCityID5.setText("");
                                edtStopStateID5.setText("");

                            }


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
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.filter).setVisible(false);

    }


    public void setPosition(String pos, LayoutInflater inflater) {
        switch (pos) {
            case "1":
                if (stringArrayList.contains("1")) {

                } else {
                    stringArrayList.add("1");
                    rowView = inflater.inflate(R.layout.addstoplayout, null);
                    MtplButton mtplButton = (MtplButton) rowView.findViewById(R.id.btnRemoveStop);
                    edtStopLocality1 = (MtplEditText) rowView.findViewById(R.id.edtStopLocality);
                    edtStopCity = (AutoCompleteTextView) rowView.findViewById(R.id.edtStopCity);
                    edtStopState = (MtplEditText) rowView.findViewById(R.id.edtStopState);
                    edtStopStateID1 = (MtplEditText) rowView.findViewById(R.id.edtStopStateID1);
                    edtStopCityID1 = (MtplEditText) rowView.findViewById(R.id.edtStopCityID1);


                    MtplTextView txtStop = (MtplTextView) rowView.findViewById(R.id.txtStop);
                    txtStop.setText("Stop 1");
                    i++;
                    //  edtStopCity.setOnItemClickListener(this);
                    edtStopCity.setOnFocusChangeListener(this);
                    edtStopCity.setAdapter(adptCountry1);
                    edtStopCity.setThreshold(3);
                    mtplButton.setOnClickListener(this);
                    parentLinearLayout1.addView(rowView, parentLinearLayout1.getChildCount());
                    CM.showToast("STOP ADDED", thisActivity);
                    edtStopCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.d("your selected item", "" + pojoCities.get(position).getId());
                            String statename = "";
                            // transcityId = pojoCities.get(position).getId();
                            edtStopCityID1.setText(pojoCities.get(position).getId());
                            if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                                for (int i = 0; i < pojoStateArrayList.size(); i++) {
                                    if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                                        statename = pojoStateArrayList.get(i).getState_name();
                                        // transstateId = pojoStateArrayList.get(i).getId();
                                        edtStopState.setText(statename);
                                        edtStopStateID1.setText(pojoStateArrayList.get(i).getId());

                                        for (int j = 0; j < countryArrayList.size(); j++) {

                                            if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                                //   destiCountry.setText(countryArrayList.get(j).getCountry_name());
                                                // transcountryId = countryArrayList.get(j).getId();
                                                break;

                                            }


                                        }


                                        break;
                                    } else {

                                    }
                                }
                            } else {
                                edtStopState.setText("");
                            }


                        }
                    });
                }

                break;
            case "2":

                if (stringArrayList.contains("2")) {

                } else {

                    stringArrayList.add("2");
                    rowView = inflater.inflate(R.layout.addstoplayout1, null);
                    MtplButton mtplButton = (MtplButton) rowView.findViewById(R.id.btnRemoveStop1);
                    edtStopLocality2 = (MtplEditText) rowView.findViewById(R.id.edtStopLocality);
                    edtStopCity1 = (AutoCompleteTextView) rowView.findViewById(R.id.edtStopCity1);
                    edtStopState1 = (MtplEditText) rowView.findViewById(R.id.edtStopState1);
                    MtplTextView txtStop = (MtplTextView) rowView.findViewById(R.id.txtStop);
                    edtStopStateID2 = (MtplEditText) rowView.findViewById(R.id.edtStopStateID2);
                    edtStopCityID2 = (MtplEditText) rowView.findViewById(R.id.edtStopCityID2);

                    txtStop.setText("Stop 2");
                    i++;
                    // edtStopCity1.setOnItemClickListener(this);
                    edtStopCity1.setOnFocusChangeListener(this);
                    edtStopCity1.setAdapter(adptCountry1);
                    edtStopCity1.setThreshold(3);
                    mtplButton.setOnClickListener(this);
                    parentLinearLayout1.addView(rowView, parentLinearLayout1.getChildCount());
                    CM.showToast("STOP ADDED", thisActivity);

                    edtStopCity1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.d("your selected item", "" + pojoCities.get(position).getId());
                            String statename = "";
                            //  transcityId = pojoCities.get(position).getId();
                            edtStopCityID2.setText(pojoCities.get(position).getId());
                            if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                                for (int i = 0; i < pojoStateArrayList.size(); i++) {
                                    if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                                        statename = pojoStateArrayList.get(i).getState_name();
                                        //    transstateId = pojoStateArrayList.get(i).getId();
                                        edtStopState1.setText(statename);
                                        edtStopStateID2.setText(pojoStateArrayList.get(i).getId());
                                        for (int j = 0; j < countryArrayList.size(); j++) {

                                            if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                                //   destiCountry.setText(countryArrayList.get(j).getCountry_name());
                                                // transcountryId = countryArrayList.get(j).getId();
                                                break;

                                            }


                                        }


                                        break;
                                    } else {

                                    }
                                }
                            } else {
                                edtStopState1.setText("");
                            }


                        }
                    });


                }
                break;
            case "3":

                if (stringArrayList.contains("3")) {

                } else {
                    stringArrayList.add("3");
                    rowView = inflater.inflate(R.layout.addstoplayout2, null);
                    MtplButton mtplButton = (MtplButton) rowView.findViewById(R.id.btnRemoveStop2);
                    edtStopLocality3 = (MtplEditText) rowView.findViewById(R.id.edtStopLocality);
                    edtStopCity2 = (AutoCompleteTextView) rowView.findViewById(R.id.edtStopCity2);
                    edtStopState2 = (MtplEditText) rowView.findViewById(R.id.edtStopState2);
                    MtplTextView txtStop = (MtplTextView) rowView.findViewById(R.id.txtStop);
                    edtStopStateID3 = (MtplEditText) rowView.findViewById(R.id.edtStopStateID3);
                    edtStopCityID3 = (MtplEditText) rowView.findViewById(R.id.edtStopCityID3);

                    txtStop.setText("Stop 3");
                    i++;
                    //edtStopCity2.setOnItemClickListener(this);
                    edtStopCity2.setOnFocusChangeListener(this);
                    edtStopCity2.setAdapter(adptCountry1);
                    edtStopCity2.setThreshold(3);
                    mtplButton.setOnClickListener(this);

                    parentLinearLayout1.addView(rowView, parentLinearLayout1.getChildCount());
                    CM.showToast("STOP ADDED", thisActivity);

                    edtStopCity2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.d("your selected item", "" + pojoCities.get(position).getId());
                            String statename = "";
                            //  transcityId = pojoCities.get(position).getId();
                            edtStopCityID3.setText(pojoCities.get(position).getId());
                            if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                                for (int i = 0; i < pojoStateArrayList.size(); i++) {
                                    if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                                        statename = pojoStateArrayList.get(i).getState_name();
                                        //    transstateId = pojoStateArrayList.get(i).getId();
                                        edtStopState2.setText(statename);
                                        edtStopStateID3.setText(pojoStateArrayList.get(i).getId());
                                        for (int j = 0; j < countryArrayList.size(); j++) {

                                            if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                                //   destiCountry.setText(countryArrayList.get(j).getCountry_name());
                                                // transcountryId = countryArrayList.get(j).getId();
                                                break;

                                            }


                                        }


                                        break;
                                    } else {

                                    }
                                }
                            } else {
                                edtStopState2.setText("");
                            }


                        }
                    });


                }
                break;
            case "4":

                if (stringArrayList.contains("4")) {

                } else {
                    stringArrayList.add("4");
                    rowView = inflater.inflate(R.layout.addstoplayout3, null);
                    MtplButton mtplButton = (MtplButton) rowView.findViewById(R.id.btnRemoveStop3);
                    edtStopLocality4 = (MtplEditText) rowView.findViewById(R.id.edtStopLocality);
                    edtStopCity3 = (AutoCompleteTextView) rowView.findViewById(R.id.edtStopCity3);
                    edtStopState3 = (MtplEditText) rowView.findViewById(R.id.edtStopState3);
                    MtplTextView txtStop = (MtplTextView) rowView.findViewById(R.id.txtStop);
                    edtStopStateID4 = (MtplEditText) rowView.findViewById(R.id.edtStopStateID4);
                    edtStopCityID4 = (MtplEditText) rowView.findViewById(R.id.edtStopCityID4);


                    // edtStopCity3.setOnItemClickListener(this);
                    edtStopCity3.setOnFocusChangeListener(this);
                    edtStopCity3.setAdapter(adptCountry1);
                    edtStopCity3.setThreshold(3);
                    txtStop.setText("Stop 4");
                    i++;
                    mtplButton.setOnClickListener(this);
                    parentLinearLayout1.addView(rowView, parentLinearLayout1.getChildCount());
                    CM.showToast("STOP ADDED", thisActivity);

                    edtStopCity3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.d("your selected item", "" + pojoCities.get(position).getId());
                            String statename = "";
                            //  transcityId = pojoCities.get(position).getId();
                            edtStopCityID4.setText(pojoCities.get(position).getId());
                            if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                                for (int i = 0; i < pojoStateArrayList.size(); i++) {
                                    if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                                        statename = pojoStateArrayList.get(i).getState_name();
                                        //    transstateId = pojoStateArrayList.get(i).getId();
                                        edtStopState3.setText(statename);
                                        edtStopStateID4.setText(pojoStateArrayList.get(i).getId());

                                        for (int j = 0; j < countryArrayList.size(); j++) {

                                            if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                                //   destiCountry.setText(countryArrayList.get(j).getCountry_name());
                                                // transcountryId = countryArrayList.get(j).getId();

                                                break;

                                            }


                                        }


                                        break;
                                    } else {

                                    }
                                }
                            } else {
                                edtStopState2.setText("");
                            }


                        }
                    });

                }
                break;
            case "5":

                if (stringArrayList.contains("5")) {

                } else {
                    stringArrayList.add("5");
                    rowView = inflater.inflate(R.layout.addstoplayout4, null);
                    MtplButton mtplButton = (MtplButton) rowView.findViewById(R.id.btnRemoveStop4);
                    edtStopLocality5 = (MtplEditText) rowView.findViewById(R.id.edtStopLocality);
                    edtStopCity4 = (AutoCompleteTextView) rowView.findViewById(R.id.edtStopCity4);
                    edtStopState4 = (MtplEditText) rowView.findViewById(R.id.edtStopState4);
                    MtplTextView txtStop = (MtplTextView) rowView.findViewById(R.id.txtStop);
                    edtStopStateID5 = (MtplEditText) rowView.findViewById(R.id.edtStopStateID5);
                    edtStopCityID5 = (MtplEditText) rowView.findViewById(R.id.edtStopCityID5);

                    //  edtStopCity4.setOnItemClickListener(this);
                    edtStopCity4.setOnFocusChangeListener(this);
                    edtStopCity4.setAdapter(adptCountry1);
                    edtStopCity4.setThreshold(3);
                    txtStop.setText("Stop 5");
                    i++;
                    //i = 0;
                    mtplButton.setOnClickListener(this);
                    parentLinearLayout1.addView(rowView, parentLinearLayout1.getChildCount());
                    CM.showToast("STOP ADDED", thisActivity);


                    edtStopCity4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.d("your selected item", "" + pojoCities.get(position).getId());
                            String statename = "";
                            //  transcityId = pojoCities.get(position).getId();
                            edtStopCityID5.setText(pojoCities.get(position).getId());
                            if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                                for (int i = 0; i < pojoStateArrayList.size(); i++) {
                                    if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                                        statename = pojoStateArrayList.get(i).getState_name();
                                        //    transstateId = pojoStateArrayList.get(i).getId();
                                        edtStopState4.setText(statename);
                                        edtStopStateID5.setText(pojoStateArrayList.get(i).getId());

                                        for (int j = 0; j < countryArrayList.size(); j++) {

                                            if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                                //   destiCountry.setText(countryArrayList.get(j).getCountry_name());
                                                // transcountryId = countryArrayList.get(j).getId();
                                                break;

                                            }


                                        }


                                        break;
                                    } else {

                                    }
                                }
                            } else {
                                edtStopState4.setText("");
                            }


                        }
                    });

                }
                break;

        }

    }


    public void setPositionForStayReq(String pos, LayoutInflater inflater) {
        switch (pos) {
            case "1":
                if (stringArrayListStayReq.contains("1")) {

                } else {
                    stringArrayListStayReq.add("1");
                    rootView = inflater.inflate(R.layout.addanotherdestination, null);

                    singleRoom1 = (MtplEditText) rootView.findViewById(R.id.edtSingleRoom1);
                    doubleRoom1 = (MtplEditText) rootView.findViewById(R.id.edtDoubleRoom1);
                    tripalRoom1 = (MtplEditText) rootView.findViewById(R.id.edtTripleRoom1);
                    childWithbed1 = (MtplEditText) rootView.findViewById(R.id.edt_Child_with_bed1);
                    childWithoutbed1 = (MtplEditText) rootView.findViewById(R.id.edt_Child_without_bed1);
                    spinnerHotelRating1 = (Spinner) rootView.findViewById(R.id.spinnerHotelRating1);
                    spinnerHotelCat1 = (MultiSelectionSpinner) rootView.findViewById(R.id.hotCatSpinner);
                    String[] array = getResources().getStringArray(R.array.hotCatArray);
                    spinnerHotelCat1.setItems(array);
                    spinnerHotelCat1.setListener(this);
                    spinnerMealPlane1 = (Spinner) rootView.findViewById(R.id.spinnerMealPlane1);
                    destiCity1 = (AutoCompleteTextView) rootView.findViewById(R.id.edtDestinationCity1);
                    destiState1 = (MtplEditText) rootView.findViewById(R.id.edtDestinationState1);
                    destiCountry1 = (MtplEditText) rootView.findViewById(R.id.edtDestinationCountry1);
                    destiLocality1 = (MtplEditText) rootView.findViewById(R.id.edtDestinationLocality1);
                    destiState1.setEnabled(false);
                    destiCountry1.setEnabled(false);
                    checkIn1 = (MtplEditText) rootView.findViewById(R.id.edtCheckIn1);
                    checkOut1 = (MtplEditText) rootView.findViewById(R.id.edtCheckout1);

                    cityId1 = (MtplEditText) rootView.findViewById(R.id.cityId1);
                    stateId1 = (MtplEditText) rootView.findViewById(R.id.stateId1);
                    countryId1 = (MtplEditText) rootView.findViewById(R.id.countryId1);
                    j++;
                    MtplButton btnRemove = (MtplButton) rootView.findViewById(btnRemove1);
                    destiCity1.setAdapter(adptCountry1);
                    destiCity1.setThreshold(3);
                    checkIn1.setOnTouchListener(this);
                    checkOut1.setOnTouchListener(this);
                    btnRemove.setOnClickListener(this);
                    parentLinearLayout.addView(rootView, parentLinearLayout.getChildCount());
                    destiCity1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.d("your selected item", "" + pojoCities.get(position).getId());
                            String statename = "";
                            //  transcityId = pojoCities.get(position).getId();
                            cityId1.setText(pojoCities.get(position).getId());
                            if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                                for (int i = 0; i < pojoStateArrayList.size(); i++) {
                                    if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                                        statename = pojoStateArrayList.get(i).getState_name();
                                        //    transstateId = pojoStateArrayList.get(i).getId();
                                        destiState1.setText(statename);
                                        stateId1.setText(pojoStateArrayList.get(i).getId());

                                        for (int j = 0; j < countryArrayList.size(); j++) {

                                            if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                                destiCountry1.setText(countryArrayList.get(j).getCountry_name());
                                                countryId1.setText(countryArrayList.get(j).getId());
                                                break;

                                            }


                                        }


                                        break;
                                    } else {

                                    }
                                }
                            } else {
                                destiState1.setText("");
                            }


                        }
                    });
                }

                break;
            case "2":

                if (stringArrayListStayReq.contains("2")) {

                } else {

                    stringArrayListStayReq.add("2");
                    rootView = inflater.inflate(R.layout.addanotherdestination1, null);

                    singleRoom2 = (MtplEditText) rootView.findViewById(R.id.edtSingleRoom1);
                    doubleRoom2 = (MtplEditText) rootView.findViewById(R.id.edtDoubleRoom1);
                    tripalRoom2 = (MtplEditText) rootView.findViewById(R.id.edtTripleRoom1);
                    childWithbed2 = (MtplEditText) rootView.findViewById(R.id.edt_Child_with_bed1);
                    childWithoutbed2 = (MtplEditText) rootView.findViewById(R.id.edt_Child_without_bed1);
                    spinnerHotelRating2 = (Spinner) rootView.findViewById(R.id.spinnerHotelRating1);
                    spinnerHotelCat2 = (MultiSelectionSpinner) rootView.findViewById(R.id.hotCatSpinner);
                    String[] array = getResources().getStringArray(R.array.hotCatArray);
                    spinnerHotelCat2.setItems(array);
                    spinnerHotelCat2.setListener(this);
                    spinnerMealPlane2 = (Spinner) rootView.findViewById(R.id.spinnerMealPlane1);
                    destiCity2 = (AutoCompleteTextView) rootView.findViewById(R.id.edtDestinationCity1);
                    destiState2 = (MtplEditText) rootView.findViewById(R.id.edtDestinationState1);
                    destiCountry2 = (MtplEditText) rootView.findViewById(R.id.edtDestinationCountry1);
                    destiLocality2 = (MtplEditText) rootView.findViewById(R.id.edtDestinationLocality1);
                    destiState2.setEnabled(false);
                    destiCountry2.setEnabled(false);
                    checkIn2 = (MtplEditText) rootView.findViewById(R.id.edtCheckIn2);
                    checkOut2 = (MtplEditText) rootView.findViewById(R.id.edtCheckout2);

                    cityId2 = (MtplEditText) rootView.findViewById(R.id.cityId2);
                    stateId2 = (MtplEditText) rootView.findViewById(R.id.stateId2);
                    countryId2 = (MtplEditText) rootView.findViewById(R.id.countryId2);
                    j++;
                    MtplButton btnRemove = (MtplButton) rootView.findViewById(btnRemove2);
                    destiCity2.setAdapter(adptCountry1);
                    destiCity2.setThreshold(3);
                    checkIn2.setOnTouchListener(this);
                    checkOut2.setOnTouchListener(this);
                    btnRemove.setOnClickListener(this);
                    parentLinearLayout.addView(rootView, parentLinearLayout.getChildCount());
                    destiCity2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.d("your selected item", "" + pojoCities.get(position).getId());
                            String statename = "";
                            //  transcityId = pojoCities.get(position).getId();
                            cityId2.setText(pojoCities.get(position).getId());
                            if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                                for (int i = 0; i < pojoStateArrayList.size(); i++) {
                                    if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                                        statename = pojoStateArrayList.get(i).getState_name();
                                        //    transstateId = pojoStateArrayList.get(i).getId();
                                        destiState2.setText(statename);
                                        stateId2.setText(pojoStateArrayList.get(i).getId());

                                        for (int j = 0; j < countryArrayList.size(); j++) {

                                            if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                                destiCountry2.setText(countryArrayList.get(j).getCountry_name());
                                                countryId2.setText(countryArrayList.get(j).getId());
                                                break;

                                            }


                                        }


                                        break;
                                    } else {

                                    }
                                }
                            } else {
                                destiState2.setText("");
                            }


                        }
                    });
                }
                break;
            case "3":

                if (stringArrayListStayReq.contains("3")) {

                } else {
                    stringArrayListStayReq.add("3");
                    rootView = inflater.inflate(R.layout.addanotherdestination2, null);

                    singleRoom3 = (MtplEditText) rootView.findViewById(R.id.edtSingleRoom1);
                    doubleRoom3 = (MtplEditText) rootView.findViewById(R.id.edtDoubleRoom1);
                    tripalRoom3 = (MtplEditText) rootView.findViewById(R.id.edtTripleRoom1);
                    childWithbed3 = (MtplEditText) rootView.findViewById(R.id.edt_Child_with_bed1);
                    childWithoutbed3 = (MtplEditText) rootView.findViewById(R.id.edt_Child_without_bed1);
                    spinnerHotelRating3 = (Spinner) rootView.findViewById(R.id.spinnerHotelRating1);
                    spinnerHotelCat3 = (MultiSelectionSpinner) rootView.findViewById(R.id.hotCatSpinner);
                    String[] array = getResources().getStringArray(R.array.hotCatArray);
                    spinnerHotelCat3.setItems(array);
                    spinnerHotelCat3.setListener(this);
                    spinnerMealPlane3 = (Spinner) rootView.findViewById(R.id.spinnerMealPlane1);
                    destiCity3 = (AutoCompleteTextView) rootView.findViewById(R.id.edtDestinationCity1);
                    destiState3 = (MtplEditText) rootView.findViewById(R.id.edtDestinationState1);
                    destiCountry3 = (MtplEditText) rootView.findViewById(R.id.edtDestinationCountry1);
                    destiLocality3 = (MtplEditText) rootView.findViewById(R.id.edtDestinationLocality1);
                    destiState3.setEnabled(false);
                    destiCountry3.setEnabled(false);
                    checkIn3 = (MtplEditText) rootView.findViewById(R.id.edtCheckIn3);
                    checkOut3 = (MtplEditText) rootView.findViewById(R.id.edtCheckout3);

                    cityId3 = (MtplEditText) rootView.findViewById(R.id.cityId3);
                    stateId3 = (MtplEditText) rootView.findViewById(R.id.stateId3);
                    countryId3 = (MtplEditText) rootView.findViewById(R.id.countryId3);
                    j++;
                    MtplButton btnRemove = (MtplButton) rootView.findViewById(R.id.btnRemove3);
                    destiCity3.setAdapter(adptCountry1);
                    destiCity3.setThreshold(3);
                    checkIn3.setOnTouchListener(this);
                    checkOut3.setOnTouchListener(this);
                    btnRemove.setOnClickListener(this);
                    parentLinearLayout.addView(rootView, parentLinearLayout.getChildCount());
                    destiCity3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.d("your selected item", "" + pojoCities.get(position).getId());
                            String statename = "";
                            //  transcityId = pojoCities.get(position).getId();
                            cityId3.setText(pojoCities.get(position).getId());
                            if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                                for (int i = 0; i < pojoStateArrayList.size(); i++) {
                                    if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                                        statename = pojoStateArrayList.get(i).getState_name();
                                        //    transstateId = pojoStateArrayList.get(i).getId();
                                        destiState3.setText(statename);
                                        stateId3.setText(pojoStateArrayList.get(i).getId());

                                        for (int j = 0; j < countryArrayList.size(); j++) {

                                            if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                                destiCountry3.setText(countryArrayList.get(j).getCountry_name());
                                                countryId3.setText(countryArrayList.get(j).getId());
                                                break;

                                            }


                                        }


                                        break;
                                    } else {

                                    }
                                }
                            } else {
                                destiState3.setText("");
                            }


                        }
                    });
                }
                break;
            case "4":

                if (stringArrayListStayReq.contains("4")) {

                } else {
                    stringArrayListStayReq.add("4");
                    rootView = inflater.inflate(R.layout.addanotherdestination3, null);

                    singleRoom4 = (MtplEditText) rootView.findViewById(R.id.edtSingleRoom1);
                    doubleRoom4 = (MtplEditText) rootView.findViewById(R.id.edtDoubleRoom1);
                    tripalRoom4 = (MtplEditText) rootView.findViewById(R.id.edtTripleRoom1);
                    childWithbed4 = (MtplEditText) rootView.findViewById(R.id.edt_Child_with_bed1);
                    childWithoutbed4 = (MtplEditText) rootView.findViewById(R.id.edt_Child_without_bed1);
                    spinnerHotelRating4 = (Spinner) rootView.findViewById(R.id.spinnerHotelRating1);
                    spinnerHotelCat4 = (MultiSelectionSpinner) rootView.findViewById(R.id.hotCatSpinner);
                    String[] array = getResources().getStringArray(R.array.hotCatArray);
                    spinnerHotelCat4.setItems(array);
                    spinnerHotelCat4.setListener(this);
                    spinnerMealPlane4 = (Spinner) rootView.findViewById(R.id.spinnerMealPlane1);
                    destiCity4 = (AutoCompleteTextView) rootView.findViewById(R.id.edtDestinationCity1);
                    destiState4 = (MtplEditText) rootView.findViewById(R.id.edtDestinationState1);
                    destiCountry4 = (MtplEditText) rootView.findViewById(R.id.edtDestinationCountry1);
                    destiLocality4 = (MtplEditText) rootView.findViewById(R.id.edtDestinationLocality1);
                    destiState4.setEnabled(false);
                    destiCountry4.setEnabled(false);
                    checkIn4 = (MtplEditText) rootView.findViewById(R.id.edtCheckIn4);
                    checkOut4 = (MtplEditText) rootView.findViewById(R.id.edtCheckout4);

                    cityId4 = (MtplEditText) rootView.findViewById(R.id.cityId4);
                    stateId4 = (MtplEditText) rootView.findViewById(R.id.stateId4);
                    countryId4 = (MtplEditText) rootView.findViewById(R.id.countryId4);
                    j++;
                    MtplButton btnRemove = (MtplButton) rootView.findViewById(R.id.btnRemove4);
                    destiCity4.setAdapter(adptCountry1);
                    destiCity4.setThreshold(3);
                    checkIn4.setOnTouchListener(this);
                    checkOut4.setOnTouchListener(this);
                    btnRemove.setOnClickListener(this);
                    parentLinearLayout.addView(rootView, parentLinearLayout.getChildCount());
                    destiCity4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.d("your selected item", "" + pojoCities.get(position).getId());
                            String statename = "";
                            //  transcityId = pojoCities.get(position).getId();
                            cityId4.setText(pojoCities.get(position).getId());
                            if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                                for (int i = 0; i < pojoStateArrayList.size(); i++) {
                                    if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                                        statename = pojoStateArrayList.get(i).getState_name();
                                        //    transstateId = pojoStateArrayList.get(i).getId();
                                        destiState4.setText(statename);
                                        stateId4.setText(pojoStateArrayList.get(i).getId());

                                        for (int j = 0; j < countryArrayList.size(); j++) {

                                            if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                                destiCountry4.setText(countryArrayList.get(j).getCountry_name());
                                                countryId4.setText(countryArrayList.get(j).getId());
                                                break;

                                            }


                                        }


                                        break;
                                    } else {

                                    }
                                }
                            } else {
                                destiState4.setText("");
                            }


                        }
                    });
                }
                break;
            case "5":

                if (stringArrayListStayReq.contains("5")) {

                } else {
                    stringArrayListStayReq.add("5");
                    rootView = inflater.inflate(R.layout.addanotherdestination4, null);

                    singleRoom5 = (MtplEditText) rootView.findViewById(R.id.edtSingleRoom1);
                    doubleRoom5 = (MtplEditText) rootView.findViewById(R.id.edtDoubleRoom1);
                    tripalRoom5 = (MtplEditText) rootView.findViewById(R.id.edtTripleRoom1);
                    childWithbed5 = (MtplEditText) rootView.findViewById(R.id.edt_Child_with_bed1);
                    childWithoutbed5 = (MtplEditText) rootView.findViewById(R.id.edt_Child_without_bed1);
                    spinnerHotelRating5 = (Spinner) rootView.findViewById(R.id.spinnerHotelRating1);
                    spinnerHotelCat5 = (MultiSelectionSpinner) rootView.findViewById(R.id.hotCatSpinner);
                    String[] array = getResources().getStringArray(R.array.hotCatArray);
                    spinnerHotelCat5.setItems(array);
                    spinnerHotelCat5.setListener(this);
                    spinnerMealPlane5 = (Spinner) rootView.findViewById(R.id.spinnerMealPlane1);
                    destiCity5 = (AutoCompleteTextView) rootView.findViewById(R.id.edtDestinationCity1);
                    destiState5 = (MtplEditText) rootView.findViewById(R.id.edtDestinationState1);
                    destiCountry5 = (MtplEditText) rootView.findViewById(R.id.edtDestinationCountry1);
                    destiLocality5 = (MtplEditText) rootView.findViewById(R.id.edtDestinationLocality1);
                    destiState5.setEnabled(false);
                    destiCountry5.setEnabled(false);
                    checkIn5 = (MtplEditText) rootView.findViewById(R.id.edtCheckIn5);
                    checkOut5 = (MtplEditText) rootView.findViewById(R.id.edtCheckout5);

                    cityId5 = (MtplEditText) rootView.findViewById(R.id.cityId5);
                    stateId5 = (MtplEditText) rootView.findViewById(R.id.stateId5);
                    countryId5 = (MtplEditText) rootView.findViewById(R.id.countryId5);
                    j++;
                    MtplButton btnRemove = (MtplButton) rootView.findViewById(R.id.btnRemove5);
                    destiCity5.setAdapter(adptCountry1);
                    destiCity5.setThreshold(3);
                    checkIn5.setOnTouchListener(this);
                    checkOut5.setOnTouchListener(this);
                    btnRemove.setOnClickListener(this);
                    parentLinearLayout.addView(rootView, parentLinearLayout.getChildCount());
                    destiCity5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Log.d("your selected item", "" + pojoCities.get(position).getId());
                            String statename = "";
                            //  transcityId = pojoCities.get(position).getId();
                            cityId1.setText(pojoCities.get(position).getId());
                            if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                                for (int i = 0; i < pojoStateArrayList.size(); i++) {
                                    if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                                        statename = pojoStateArrayList.get(i).getState_name();
                                        //    transstateId = pojoStateArrayList.get(i).getId();
                                        destiState5.setText(statename);
                                        stateId5.setText(pojoStateArrayList.get(i).getId());

                                        for (int j = 0; j < countryArrayList.size(); j++) {

                                            if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                                destiCountry5.setText(countryArrayList.get(j).getCountry_name());
                                                countryId5.setText(countryArrayList.get(j).getId());
                                                break;

                                            }


                                        }


                                        break;
                                    } else {

                                    }
                                }
                            } else {
                                destiState5.setText("");
                            }


                        }
                    });
                }
                break;

        }

    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }
}
