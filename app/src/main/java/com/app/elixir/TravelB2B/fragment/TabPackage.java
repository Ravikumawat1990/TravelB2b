package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.AutocompleteAdapter;
import com.app.elixir.TravelB2B.model.Person;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplEditText;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.numberPicker.NumberPicker;
import com.app.elixir.TravelB2B.pojos.pojoCity;
import com.app.elixir.TravelB2B.pojos.pojoCountry;
import com.app.elixir.TravelB2B.pojos.pojoState;
import com.app.elixir.TravelB2B.utils.CM;
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

import static com.app.elixir.TravelB2B.R.id.checkin2;
import static com.app.elixir.TravelB2B.R.id.checkout4;
import static com.app.elixir.TravelB2B.R.id.checkout5;


/**
 * Created by NetSupport on 02-06-2017.
 */

public class TabPackage extends Fragment implements View.OnClickListener, View.OnTouchListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    private static final String TAG = "TabPackage";
    Activity thisActivity;
    MtplButton btnAddAnother, btnSubmit;
    private LinearLayout parentLinearLayout;
    private ExpandableLayout expandableLayout;
    private NestedScrollView rootScrollView;
    NestedScrollView childScrollview;
    private MtplEditText checkIn, checkOut;
    int i;
    private MtplEditText edtlocality;
    private View rowViewMain;

    ArrayList<String> strings;
    MtplEditText checkIn1;
    MtplEditText checkOut1;
    private ImageView imageView1, imageView2, imageView3, imageView4;
    MtplEditText refId, totBudget;
    NumberPicker numberPicker, childBelow;
    ImageView imageViewNoOfRoomImg;
    MtplEditText singleRoom, doubleRoom, tripalRoom, childWithbed, childWithoutbed;
    Spinner spinnerHotelRating, spinnerMealPlane, spinnerTransport;
    MultiSelectionSpinner spinnerHotelCat;
    MtplEditText destiState, destiCountry, destiLocality;
    AutoCompleteTextView destiCity;

    ArrayList<pojoCity> pojoCities;
    ArrayList<pojoCountry> countryArrayList;
    ArrayList<pojoState> pojoStateArrayList;
    Person person;
    ArrayList<Person> pojoStates;
    String countryId, cityId, stateId;
    MtplEditText transStatrDate, transEndDate, pickupLocality, trapickupCity, pickupState, finalLocality, finalCity, finalState, edtComment;
    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rowViewMain = inflater.inflate(R.layout.tabpackage, container, false);
        thisActivity = getActivity();
        initView(rowViewMain);


        return rowViewMain;


    }

    private void initView(View view) {

        countryArrayList = new ArrayList<>();
        pojoCities = new ArrayList<>();
        pojoStateArrayList = new ArrayList<>();
        btnAddAnother = (MtplButton) view.findViewById(R.id.btnAddAnother);
        String[] array = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) view.findViewById(R.id.mySpinner);
        multiSelectionSpinner.setItems(array);
        multiSelectionSpinner.setListener(this);
        spinnerHotelRating = (Spinner) view.findViewById(R.id.spinnerHotelCat);
        spinnerMealPlane = (Spinner) view.findViewById(R.id.spinnerMealPlane);
        spinnerTransport = (Spinner) view.findViewById(R.id.spinnerTransport);


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

       /* expandableLayout1.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
            @Override
            public void onExpand(boolean b) {
                if (b) {
                    imageViewNoOfRoomImg.setBackgroundResource(R.drawable.ic_remove_black_24dp);

                } else {
                    imageViewNoOfRoomImg.setBackgroundResource(R.drawable.ic_add_black_24dp);
                }

            }
        });*/

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

        refId = (MtplEditText) view.findViewById(R.id.edtRefId);
        totBudget = (MtplEditText) view.findViewById(R.id.edtTotalBudget);
        numberPicker = (NumberPicker) view.findViewById(R.id.number_pickerAdult);
        childBelow = (NumberPicker) view.findViewById(R.id.number_pickerChildBelow);


        singleRoom = (MtplEditText) view.findViewById(R.id.edtSingleRoom);
        doubleRoom = (MtplEditText) view.findViewById(R.id.edtDoubleRoom);
        tripalRoom = (MtplEditText) view.findViewById(R.id.edtTripleRoom);
        childWithbed = (MtplEditText) view.findViewById(R.id.edt_Child_with_bed);
        childWithoutbed = (MtplEditText) view.findViewById(R.id.edt_Child_without_bed);


        btnSubmit = (MtplButton) view.findViewById(R.id.btnSubmit);
        parentLinearLayout = (LinearLayout) view.findViewById(R.id.parent_linear_layout);
        rootScrollView = (NestedScrollView) view.findViewById(R.id.rootScroolView);
        childScrollview = (NestedScrollView) view.findViewById(R.id.childScrollView);
        checkIn = (MtplEditText) view.findViewById(R.id.edtCheckIn);
        checkOut = (MtplEditText) view.findViewById(R.id.edtCheckOut);


        destiCity = (AutoCompleteTextView) view.findViewById(R.id.edtDestinationCity);
        destiState = (MtplEditText) view.findViewById(R.id.edtDestinationState);
        destiCountry = (MtplEditText) view.findViewById(R.id.edtDestinationCountry);
        destiLocality = (MtplEditText) view.findViewById(R.id.edtDestinationLocality);
        destiState.setEnabled(false);
        destiCountry.setEnabled(false);


        transStatrDate = (MtplEditText) view.findViewById(R.id.transStatrDate);
        transEndDate = (MtplEditText) view.findViewById(R.id.transEndDate);
        pickupLocality = (MtplEditText) view.findViewById(R.id.pickupLocality);
        trapickupCity = (MtplEditText) view.findViewById(R.id.trapickupCity);
        pickupState = (MtplEditText) view.findViewById(R.id.trapickupState);

        finalLocality = (MtplEditText) view.findViewById(R.id.finalLocality);
        finalCity = (MtplEditText) view.findViewById(R.id.finalCity);
        finalState = (MtplEditText) view.findViewById(R.id.finalState);
        edtComment = (MtplEditText) view.findViewById(R.id.edtComment);


        destiCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("your selected item", "" + pojoCities.get(position).getId());
                String statename = "";
                if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                    for (int i = 0; i < pojoStateArrayList.size(); i++) {
                        if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                            statename = pojoStateArrayList.get(i).getState_name();
                            destiState.setText(statename);
                            for (int j = 0; j < countryArrayList.size(); j++) {

                                if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                    destiCountry.setText(countryArrayList.get(j).getCountry_name());
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


                                            for (int l = 0; l < countryArrayList.size(); l++) {

                                                if (pojoStateArrayList.get(k).getCountry_id().toString().equals(countryArrayList.get(l).getId().toString())) {

                                                    destiCountry.setText(countryArrayList.get(l).getCountry_name());
                                                    break;

                                                }


                                            }


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


        checkIn.setOnTouchListener(this);
        checkOut.setOnTouchListener(this);
        transStatrDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnAddAnother.setOnClickListener(this);
        strings = new ArrayList<>();
        webCallCity();
        webCallState();
        webCallCountry();

    }

    public void onAddField(View v) {

        try {
            LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.addanotherdestination, null);
            //   rowView.setId(i++);
            MtplButton mtplButton = (MtplButton) rootView.findViewById(R.id.btnRemove);
            edtlocality = (MtplEditText) rootView.findViewById(R.id.locality);
            checkIn1 = (MtplEditText) rootView.findViewById(R.id.edtCheckIn1);
            checkOut1 = (MtplEditText) rootView.findViewById(R.id.edtCheckout1);
            i++;

            if (i == 1) {
                checkIn1.setId(R.id.checkin1);
                checkOut1.setId(R.id.checkout1);
            } else if (i == 2) {
                checkIn1.setId(checkin2);
                checkOut1.setId(R.id.checkout2);
            } else if (i == 3) {
                checkIn1.setId(R.id.checkin3);
                checkOut1.setId(R.id.checkout3);
            } else if (i == 4) {
                checkIn1.setId(R.id.checkin4);
                checkOut1.setId(checkout4);
            } else if (i == 5) {
                checkIn1.setId(R.id.checkin5);
                checkOut1.setId(checkout5);
            }


            checkIn1.setOnTouchListener(this);
            checkOut1.setOnTouchListener(this);

            String[] array = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
            spinnerHotelCat = (MultiSelectionSpinner) rootView.findViewById(R.id.mySpinner);
            spinnerHotelCat.setItems(array);
            spinnerHotelCat.setListener(this);
            //   rowView.setId(i++);
            mtplButton.setOnClickListener(this);
            Log.i(TAG, "onAddField: " + parentLinearLayout.getChildCount());
            parentLinearLayout.addView(rootView, parentLinearLayout.getChildCount());


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
            case R.id.btnRemove:
                onDelete(view);
                break;
            case R.id.btnSubmit:

                if (!refId.getText().toString().equals("")) {


                    if (totBudget.getText().toString().equals("")) {


                        if (destiCity.getText().toString().equals("")) {


                            if (checkIn.getText().toString().equals("")) {

                                if (checkOut.getText().toString().equals("")) {

                                    numberPicker.getValue();
                                    childBelow.getValue();
                                    singleRoom.getText().toString();
                                    doubleRoom.getText().toString();
                                    tripalRoom.getText().toString();
                                    childWithbed.getText().toString();
                                    childWithoutbed.getText().toString();
                                    spinnerHotelCat.getSelectedItemsAsString();
                                    spinnerHotelRating.getSelectedItem();
                                    spinnerMealPlane.getSelectedItem();
                                    destiCity.getText().toString();
                                    destiState.getText().toString();
                                    destiCountry.getText().toString();
                                    destiLocality.getText().toString();
                                    checkIn.getText().toString();
                                    checkOut.getText().toString();

                                    spinnerTransport.getSelectedItem();
                                    edtComment.getText().toString();


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

                                                CM.showToast(((AppCompatSpinner) view111).getSelectedItem().toString(), thisActivity);

                                            } else {
                                                Log.i(TAG, "onClick: ");
                                            }


                                            Log.i(TAG, "onClick: ");
                                        } else if (view111 instanceof MultiSelectionSpinner) {

                                            if (((MultiSelectionSpinner) view111).getSelectedItemsAsString() != null) {
                                                ((MultiSelectionSpinner) view111).getSelectedItemsAsString().toString();
                                                CM.showToast(((MultiSelectionSpinner) view111).getSelectedItemsAsString().toString(), thisActivity);
                                            } else {
                                                Log.i(TAG, "onClick: ");
                                            }
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

      /*  if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = format.parse(CM.getSp(thisActivity, "serverDate", "").toString());
                now.setTime(date);
            } catch (Exception e) {
                e.getMessage();
            }
        }*/

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
            case R.id.checkin1:
                EditText checkIn1 = (EditText) rowViewMain.findViewById(R.id.checkin1);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn1.getRight() - checkIn1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(checkIn1);
                        return true;
                    }
                }
                break;

            case R.id.checkout1:
                EditText checkOut1 = (EditText) rowViewMain.findViewById(R.id.checkout1);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut1.getRight() - checkOut1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkOut1);
                        return true;
                    }
                }
                break;
            case R.id.checkin2:
                EditText checkIn2 = (EditText) rowViewMain.findViewById(R.id.checkin2);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn2.getRight() - checkIn2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(checkIn2);
                        return true;
                    }
                }
                break;

            case R.id.checkout2:
                EditText checkOut2 = (EditText) rowViewMain.findViewById(R.id.checkout2);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut2.getRight() - checkOut2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkOut2);
                        return true;
                    }
                }
                break;

            case R.id.checkin3:
                EditText checkIn3 = (EditText) rowViewMain.findViewById(R.id.checkin3);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn3.getRight() - checkIn3.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(checkIn3);
                        return true;
                    }
                }
                break;

            case R.id.checkout3:
                EditText checkOut3 = (EditText) rowViewMain.findViewById(R.id.checkout3);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut3.getRight() - checkOut3.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkOut3);
                        return true;
                    }
                }
                break;

            case R.id.checkin4:
                final EditText checkIn4 = (EditText) rowViewMain.findViewById(R.id.checkin4);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn4.getRight() - checkIn4.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(checkIn4);
                        return true;
                    }
                }
                break;

            case R.id.checkout4:
                EditText checkout4 = (EditText) rowViewMain.findViewById(R.id.checkout4);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkout4.getRight() - checkout4.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkout4);
                        return true;
                    }
                }
                break;
            case R.id.checkin5:
                final EditText checkIn5 = (EditText) rowViewMain.findViewById(R.id.checkin5);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkIn5.getRight() - checkIn5.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkIn(checkIn5);
                        return true;
                    }
                }
                break;

            case checkout5:
                final EditText checkout5 = (EditText) rowViewMain.findViewById(R.id.checkout5);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkout5.getRight() - checkout5.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkOut(checkout5);
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
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (jsonObject.optString("ResponseObject") != null) {
                        JSONArray jsonArray = new JSONArray(jsonObject.optString("ResponseObject"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            pojoCity country = new pojoCity();
                            country.setId(jsonArray.getJSONObject(i).optString("id"));
                            country.setPrice(jsonArray.getJSONObject(i).optString("price"));
                            country.setCategory(jsonArray.getJSONObject(i).optString("category"));
                            country.setName(jsonArray.getJSONObject(i).optString("name"));
                            country.setState_id(jsonArray.getJSONObject(i).optString("state_id"));
                            pojoCities.add(country);
                        }
                    }
                    AutocompleteAdapter adptCountry1 = new AutocompleteAdapter(thisActivity, R.layout.conntylayout, R.id.textViewSpinner, pojoCities);
                    destiCity.setThreshold(1);
                    destiCity.setAdapter(adptCountry1);


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


    public void checkOut(final EditText edt) {

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
    }


}
