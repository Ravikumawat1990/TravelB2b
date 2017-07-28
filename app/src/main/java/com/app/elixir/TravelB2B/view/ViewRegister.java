package com.app.elixir.TravelB2B.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.AutoCompletionView;
import com.app.elixir.TravelB2B.adapter.AutocompleteAdapter;
import com.app.elixir.TravelB2B.model.Person;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplEditText;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.payu.PayMentGateWay;
import com.app.elixir.TravelB2B.pojos.pojoCity;
import com.app.elixir.TravelB2B.pojos.pojoCountry;
import com.app.elixir.TravelB2B.pojos.pojoState;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ViewRegister extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {

    private static final String TAG = "ViewRegister";
    private MtplEditText compName, firstName, lastName, email, password, confPassword, contact, locality, address, state, pinCode, country;
    AutoCompleteTextView cityAutoComplet;


    AutoCompletionView completionView;
    Person[] people;
    ArrayList<Person> pojoStates;
    ArrayAdapter<Person> adapter;
    private LinearLayout linearLayoutTv;
    private MtplButton btnSubmit;

    ArrayList<pojoCity> pojoCities;
    ArrayList<pojoCountry> countryArrayList;
    ArrayList<pojoState> pojoStateArrayList;
    Person person;
    private CheckBox checkTC;
    private String catName;
    Spinner spinner;
    String cityId = "";
    String stateId = "";
    String countryId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(getString(R.string.signup_title));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewRegister.this);

            }
        });


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


        initView();
        Intent intent = getIntent();
        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.edtCompNametxt);

        catName = intent.getStringExtra("category");
        if (catName.equals("Hotelier")) {
            textInputLayout.setHint("Hotel Name*");
        } else {
            textInputLayout.setHint("Company Name*");
        }

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setEnabled(false);
        final String[] cat = getResources().getStringArray(R.array.catArray);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(ViewRegister.this, R.layout.support_simple_spinner_dropdown_item, cat) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                return v;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.WHITE);
                v.setBackgroundColor(Color.parseColor("#1295a2"));

                return v;
            }
        };
        langAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(langAdapter);
        Log.i(TAG, "onCreate: " + langAdapter.getPosition(catName));
        spinner.setSelection(langAdapter.getPosition(catName));


        if (catName != null) {
            if (catName.equals("Travel Agent")) {
                linearLayoutTv.setVisibility(View.VISIBLE);
            } else {
                linearLayoutTv.setVisibility(View.GONE);

            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (cat[position].equals("Travel Agent")) {
                    linearLayoutTv.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutTv.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pojoStates = new ArrayList<>();


        adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, pojoStates) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);

                return v;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.WHITE);
                v.setBackgroundColor(Color.parseColor("#1295a2"));

                return v;
            }
        };

        completionView = (AutoCompletionView) findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        // completionView.setTokenLimit(5);
        completionView.allowDuplicates(false);
        completionView.setThreshold(3);
        completionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Person> strings = completionView.getObjects();
                Log.i(TAG, "onClick: " + strings.size());
                for (int i = 0; i < strings.size(); i++) {

                    strings.get(i);

                }


            }
        });


        cityAutoComplet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("your selected item", "" + pojoCities.get(position).getId());

                cityId = pojoCities.get(position).getId();
                String statename = "";
                if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                    for (int i = 0; i < pojoStateArrayList.size(); i++) {
                        if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                            statename = pojoStateArrayList.get(i).getState_name();
                            stateId = pojoStateArrayList.get(i).getId();
                            state.setText(statename);
                            for (int j = 0; j < countryArrayList.size(); j++) {

                                if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                    country.setText(countryArrayList.get(j).getCountry_name());
                                    countryId = countryArrayList.get(j).getId();
                                    break;

                                }


                            }


                            break;
                        } else {

                        }
                    }
                } else {
                    state.setText("");
                }


            }
        });


        cityAutoComplet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    for (int i = 0; i < pojoCities.size(); i++) {
                        if (cityAutoComplet.getText().toString().equals(pojoCities.get(i).getName())) {
                            cityId = pojoCities.get(i).getId();
                            for (int j = 0; j < pojoCities.size(); j++) {
                                if (cityAutoComplet.getText().toString().equals(pojoCities.get(i).getName())) {
                                    String id = pojoCities.get(i).getState_id();
                                    for (int k = 0; k < pojoStateArrayList.size(); k++) {
                                        if (id.equals(pojoStateArrayList.get(k).getId().toString())) {
                                            String statename = pojoStateArrayList.get(k).getState_name();
                                            stateId = pojoStateArrayList.get(k).getId();
                                            state.setText(statename);


                                            for (int l = 0; l < countryArrayList.size(); l++) {

                                                if (pojoStateArrayList.get(k).getCountry_id().toString().equals(countryArrayList.get(l).getId().toString())) {

                                                    country.setText(countryArrayList.get(l).getCountry_name());
                                                    countryId = countryArrayList.get(l).getId();
                                                    break;

                                                }


                                            }


                                            break;
                                        } else {


                                        }

                                    }


                                } else {
                                    state.setText("");
                                    country.setText("");

                                }


                            }


                            break;
                        } else {
                            cityAutoComplet.setText("");
                            state.setText("");
                            country.setText("");

                        }
                    }

                    if (pojoCities != null && pojoCities.size() == 0) {
                        cityAutoComplet.setText("");
                        state.setText("");
                        country.setText("");
                    }

                } else {


                }
            }
        });

        webCallCity();
        webCallState();
        webCallCountry();


        initView();


    }

    private void initView() {
        countryArrayList = new ArrayList<>();
        pojoCities = new ArrayList<>();
        pojoStateArrayList = new ArrayList<>();


        compName = (MtplEditText) findViewById(R.id.edtCompName);


        firstName = (MtplEditText) findViewById(R.id.edtFirstName);
        lastName = (MtplEditText) findViewById(R.id.edtLastName);
        email = (MtplEditText) findViewById(R.id.edtEmail);
        password = (MtplEditText) findViewById(R.id.edtPassword);
        confPassword = (MtplEditText) findViewById(R.id.edtConfPassword);
        contact = (MtplEditText) findViewById(R.id.edtContact);
        locality = (MtplEditText) findViewById(R.id.edtLocality);
        address = (MtplEditText) findViewById(R.id.edtAddress);
        cityAutoComplet = (AutoCompleteTextView) findViewById(R.id.txtCity);
        state = (MtplEditText) findViewById(R.id.txtState);
        pinCode = (MtplEditText) findViewById(R.id.edtPinCode);
        country = (MtplEditText) findViewById(R.id.edtCountry);
        linearLayoutTv = (LinearLayout) findViewById(R.id.layoutTv);
        btnSubmit = (MtplButton) findViewById(R.id.btnSubmit);

        checkTC = (CheckBox) findViewById(R.id.checkbox);
        btnSubmit.setOnClickListener(this);

        Typeface face = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_roboto_regular));

        Typeface face1 = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_roboto_regular));
        password.setTypeface(face1);
        confPassword.setTypeface(face1);
        compName.setTypeface(face);
        cityAutoComplet.setTypeface(face);

        password.setTypeface(face);
        password.setTransformationMethod(new PasswordTransformationMethod());

        confPassword.setTypeface(face);
        confPassword.setTransformationMethod(new PasswordTransformationMethod());


        email.setOnFocusChangeListener(this);
        confPassword.setOnFocusChangeListener(this);
        confPassword.setOnFocusChangeListener(this);


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edtEmail:
                if (!hasFocus) {
                    if (email.getText().length() > 0) {
                        if (CM.isEmailValid(email.getText().toString())) {

                        } else {
                            email.setError("Please enter valid email");
                        }
                    }
                }
                break;

            case R.id.edtConfPassword:

                if (!hasFocus) {

                    String pass = password.getText().toString();
                    String confPass = confPassword.getText().toString();
                    if (pass.equals(confPass)) {

                    } else {
                        confPassword.setError("Confirm password should be equal to password");
                    }


                }

                break;
            case R.id.edtContact:

                if (!hasFocus) {
                    String contactNo = contact.getText().toString();
                    if (contactNo.length() > 0) {
                        if (!contactNo.equals("")) {

                            if (contactNo.length() == 10) {

                            } else {
                                contact.setError("Please Enter Valid Contact No.");
                            }

                        } else {
                            contact.setError("Please Enter Valid Contact No.");
                        }
                    }


                }


                break;


        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:

                if (!compName.getText().toString().equals("")) {

                    if (!firstName.getText().toString().equals("")) {

                        if (!lastName.getText().toString().equals("")) {

                            if (!email.getText().toString().equals("") && CM.isEmailValid(email.getText().toString())) {

                                if (!password.getText().toString().equals("")) {

                                    if (!confPassword.getText().toString().equals("")) {


                                        if (password.getText().toString().equals(confPassword.getText().toString())) {


                                            if (!contact.getText().toString().equals("")) {

                                                if (contact.getText().toString().length() == 10) {
                                                    if (checkTC.isChecked()) {


                                                        List<Person> selectedCity = completionView.getObjects();
                                                        ArrayList<String> strings = new ArrayList<>();
                                                        for (int i = 0; i < selectedCity.size(); i++) {
                                                            strings.add(selectedCity.get(i).getEmail());
                                                        }

                                                        Log.i(TAG, "onClick: " + strings.toString());
                                                        String catId = "";

                                                        if (spinner.getSelectedItem().toString().equals("Travel Agent")) {

                                                            catId = "1";

                                                        } else if (spinner.getSelectedItem().toString().equals("Event Planner")) {

                                                            catId = "2";

                                                        } else {
                                                            catId = "3";

                                                        }

                                                        String stateList = strings.toString().replace("[", "").replace("]", "")
                                                                .replace(", ", ",");

                                                        webSubmit(catId, compName.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString(), confPassword.getText().toString()
                                                                , contact.getText().toString(), address.getText().toString(), locality.getText().toString(), cityId, stateId
                                                                , pinCode.getText().toString(), countryId, stateList, "1");

                                                    } else {
                                                        CM.showToast(getString(R.string.tandc), ViewRegister.this);
                                                    }
                                                } else {
                                                    CM.showToast(getString(R.string.entvpno), ViewRegister.this);
                                                }

                                            } else {
                                                CM.showToast(getString(R.string.entpno), ViewRegister.this);

                                            }

                                        } else {
                                            CM.showToast(getString(R.string.pwdnotmatch), ViewRegister.this);
                                        }
                                    } else {
                                        CM.showToast(getString(R.string.entcpass), ViewRegister.this);
                                    }


                                } else {
                                    CM.showToast(getString(R.string.entvpass), ViewRegister.this);
                                }
                            } else {
                                CM.showToast(getString(R.string.entvemail), ViewRegister.this);
                            }
                        } else {
                            CM.showToast(getString(R.string.entlname), ViewRegister.this);
                        }
                    } else {
                        CM.showToast(getString(R.string.entfname), ViewRegister.this);
                    }
                } else {
                    CM.showToast(getString(R.string.entComName), ViewRegister.this);
                }


                // makePayment();
                break;

        }
    }


    private void makePayment() {
        String getFname = "";
        String getEmail = "";
        String getPhone = "";
        String getAmt = "";

        Intent intent = new Intent(getApplicationContext(), PayMentGateWay.class);
        intent.putExtra("FIRST_NAME", "Ravi");
        intent.putExtra("PHONE_NUMBER", "7728877982");
        intent.putExtra("EMAIL_ADDRESS", "ravikumawat17@gmail.com");
        intent.putExtra("AMT", "500");
        startActivityForResult(intent, 555);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 555) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                String payidId = data.getStringExtra("transId");
                String msg = data.getStringExtra("msg");
                if (result.equals("success")) {
                    // webCallPaymentSuccess(payidId, result, CM.getSp(ViewCustomerDetail.this, "customerId", "").toString(), OrderId);
                } else {
                    // webCallPaymentFail(payidId, result, CM.getSp(ViewCustomerDetail.this, "customerId", "").toString(), msg, OrderId);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                CM.showToast("PAYMENT CANCELLED BY USER", ViewRegister.this);
            }
        }
    }


    public void webCallCity() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewRegister.this, true, true);
            WebService.getCity(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCity(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewRegister.this)) {
                        CM.showPopupCommonValidation(ViewRegister.this, error, false);
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
            CM.showPopupCommonValidation(ViewRegister.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
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
                    AutocompleteAdapter adptCountry1 = new AutocompleteAdapter(ViewRegister.this, R.layout.conntylayout, R.id.textViewSpinner, pojoCities);
                    cityAutoComplet.setThreshold(1);
                    cityAutoComplet.setAdapter(adptCountry1);
                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewRegister.this, e.getMessage(), false);
        }
    }

    public void webCallState() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewRegister.this, true, true);
            WebService.getState(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForState(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewRegister.this)) {
                        CM.showPopupCommonValidation(ViewRegister.this, error, false);
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
            CM.showPopupCommonValidation(ViewRegister.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
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

                    for (int i = 0; i < pojoStateArrayList.size(); i++) {


                        person = new Person(pojoStateArrayList.get(i).getState_name(), pojoStateArrayList.get(i).getId());
                        pojoStates.add(person);

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
            CM.showPopupCommonValidation(ViewRegister.this, e.getMessage(), false);
        }
    }


    public void webCallCountry() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewRegister.this, true, true);
            WebService.getCountry(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCountry(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewRegister.this)) {
                        CM.showPopupCommonValidation(ViewRegister.this, error, false);
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
            CM.showPopupCommonValidation(ViewRegister.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
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

                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewRegister.this, e.getMessage(), false);
        }
    }


    public void webSubmit(String catName, String comName, String fname, String lname, String email, String pass, String confPass, String contact, String address, String locality, String city, String state, String pincode, String country, String selectedCity, String checkStatus) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewRegister.this, true, true);
            WebService.getRegister(v, catName, comName, fname, lname, email, pass, confPass, contact, address, locality, city, state, pincode, country, selectedCity, checkStatus, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForRegister(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewRegister.this)) {
                        CM.showPopupCommonValidation(ViewRegister.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForRegister(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewRegister.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "209":
                    if (jsonObject.optString("ResponseObject") != null) {

                    }
                    break;
                case "200":
                    CM.showToast(getString(R.string.registerd), ViewRegister.this);
                    CM.finishActivity(ViewRegister.this);
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewRegister.this);
                    break;
                default:
                    CM.showToast(jsonObject.optString("msg"), ViewRegister.this);
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewRegister.this, e.getMessage(), false);
        }
    }


}

