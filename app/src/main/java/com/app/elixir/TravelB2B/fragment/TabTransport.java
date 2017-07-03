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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplEditText;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.MultiSelectionSpinner;
import com.silencedut.expandablelayout.ExpandableLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by NetSupport on 02-06-2017.
 */

public class TabTransport extends Fragment implements View.OnClickListener, View.OnTouchListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    private static final String TAG = "TabTransport";
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
    private ImageView imageView1, imageView2, imageView3, imageView4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabtransport, container, false);
        thisActivity = getActivity();
        initView(rootView);
        return rootView;
    }

    private void initView(View view) {
        //  btnAddAnother = (MtplButton) view.findViewById(R.id.btnAddAnother);
        String[] array = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        ExpandableLayout expandableLayout1 = (ExpandableLayout) view.findViewById(R.id.expView1);
        ExpandableLayout expandableLayout2 = (ExpandableLayout) view.findViewById(R.id.expView3);
        ExpandableLayout expandableLayout3 = (ExpandableLayout) view.findViewById(R.id.expView4);
        //ExpandableLayout expandableLayout4 = (ExpandableLayout) view.findViewById(R.id.expView4);
        imageView1 = (ImageView) view.findViewById(R.id.expImg1);
        imageView2 = (ImageView) view.findViewById(R.id.expImg3);
        imageView3 = (ImageView) view.findViewById(R.id.expImg4);
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


        btnSubmit = (MtplButton) view.findViewById(R.id.btnSubmit);
        parentLinearLayout = (LinearLayout) view.findViewById(R.id.parent_linear_layout);
        rootScrollView = (NestedScrollView) view.findViewById(R.id.rootScroolView);
        childScrollview = (NestedScrollView) view.findViewById(R.id.childScrollView);

        btnSubmit.setOnClickListener(this);
        strings = new ArrayList<>();
    }

    public void onAddField(View v) {

        try {
            LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.addanotherdestination, null);
            rowView.setId(i++);
            MtplButton mtplButton = (MtplButton) rowView.findViewById(R.id.btnRemove);
            edtlocality = (MtplEditText) rowView.findViewById(R.id.locality);
            checkIn1 = (MtplEditText) rowView.findViewById(R.id.edtCheckIn1);
            checkOut1 = (MtplEditText) rowView.findViewById(R.id.edtCheckout1);
            checkIn1.setOnTouchListener(this);
            checkOut1.setOnTouchListener(this);

            String[] array = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
            MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) rowView.findViewById(R.id.mySpinner);
            multiSelectionSpinner.setItems(array);
            multiSelectionSpinner.setListener(this);


            rowView.setId(i++);
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
            case R.id.btnRemove:
                onDelete(view);
                break;
            case R.id.btnSubmit:

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

                        Calendar now = Calendar.getInstance();

                        /*if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
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
                    }
                }
                break;
            case R.id.edtCheckOut:

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut.getRight() - checkOut.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Calendar now = Calendar.getInstance();
                     /*   if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
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
                        return true;
                    }
                }
                break;
            case R.id.edtCheckIn1:

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut.getRight() - checkOut.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Calendar now = Calendar.getInstance();
                     /*   if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
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
                        return true;
                    }
                }
                break;

            case R.id.edtCheckout1:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (checkOut.getRight() - checkOut.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Calendar now = Calendar.getInstance();
                     /*   if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
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
}
