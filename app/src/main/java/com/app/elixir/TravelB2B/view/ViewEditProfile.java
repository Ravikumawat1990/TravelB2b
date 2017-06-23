package com.app.elixir.TravelB2B.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.AutoCompletionView;
import com.app.elixir.TravelB2B.adapter.AutocompleteAdapter;
import com.app.elixir.TravelB2B.model.Person;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplEditText;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
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

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.app.elixir.TravelB2B.R.id.edtCatName;

public class ViewEditProfile extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {

    private ArrayAdapter<Person> adapter;
    private Person[] people;
    private AutoCompletionView completionView;
    private String TAG;
    private ImageView proPic, officePic, officePic1, panCard, businessCard, comRegister;
    MtplEditText edtProfilepic, edtOfficePic, edtOfficePic1, edtPanCard, edtBusinessCard, edtCompReg, edtDis;
    Toolbar toolbar;
    MtplEditText catName, edtCompName, first_name, last_name, compName, email, primMobileNo, secMobileNo, address1, address2, locality, state, country, pinCode, webUrl;
    AutoCompleteTextView city;

    ArrayList<pojoCity> pojoCities;
    ArrayList<pojoCountry> countryArrayList;
    ArrayList<pojoState> pojoStateArrayList;
    Person person;
    ArrayList<Person> pojoStates;
    private MtplButton btnUpdateDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.edtProfile));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewEditProfile.this);

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        TextView titleTextView = null;
        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_DroidSerif_Bold));
            titleTextView.setTypeface(font);
            titleTextView.setTextSize(18);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }


        pojoStates = new ArrayList<>();
        adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, pojoStates) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_DroidSerif_Bold));
                ((TextView) v).setTypeface(externalFont);

                return v;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_DroidSerif_Bold));
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.WHITE);
                v.setBackgroundColor(Color.parseColor("#1295a2"));

                return v;
            }
        };


        completionView = (AutoCompletionView) findViewById(R.id.searchView);
        Typeface face = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_roboto_regular));
        completionView.setTypeface(face);
        completionView.setAdapter(adapter);
        completionView.setTokenLimit(5);
        completionView.setDuplicateParentStateEnabled(false);

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

        initView();


        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("your selected item", "" + pojoCities.get(position).getId());
                String statename = "";
                if (pojoCities.get(position).getId() != null && !pojoCities.get(position).getId().equals("")) {
                    for (int i = 0; i < pojoStateArrayList.size(); i++) {
                        if (pojoCities.get(position).getState_id().equals(pojoStateArrayList.get(i).getId())) {

                            statename = pojoStateArrayList.get(i).getState_name();
                            state.setText(statename);
                            for (int j = 0; j < countryArrayList.size(); j++) {

                                if (pojoStateArrayList.get(i).getCountry_id().toString().equals(countryArrayList.get(j).getId().toString())) {

                                    country.setText(countryArrayList.get(j).getCountry_name());
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


        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    for (int i = 0; i < pojoCities.size(); i++) {
                        if (city.getText().toString().equals(pojoCities.get(i).getName())) {
                            for (int j = 0; j < pojoCities.size(); j++) {
                                if (city.getText().toString().equals(pojoCities.get(i).getName())) {
                                    String id = pojoCities.get(i).getState_id();
                                    for (int k = 0; k < pojoStateArrayList.size(); k++) {
                                        if (id.equals(pojoStateArrayList.get(k).getId().toString())) {
                                            String statename = pojoStateArrayList.get(k).getState_name();
                                            state.setText(statename);


                                            for (int l = 0; l < countryArrayList.size(); l++) {

                                                if (pojoStateArrayList.get(k).getCountry_id().toString().equals(countryArrayList.get(l).getId().toString())) {

                                                    country.setText(countryArrayList.get(l).getCountry_name());
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
                            city.setText("");
                            state.setText("");
                            country.setText("");

                        }
                    }

                    if (pojoCities != null && pojoCities.size() == 0) {
                        city.setText("");
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


    }

    private void initView() {
        countryArrayList = new ArrayList<>();
        pojoCities = new ArrayList<>();
        pojoStateArrayList = new ArrayList<>();

        catName = (MtplEditText) findViewById(edtCatName);
        edtCompName = (MtplEditText) findViewById(R.id.edtCompName);
        first_name = (MtplEditText) findViewById(R.id.edtFirstName);
        last_name = (MtplEditText) findViewById(R.id.edtlastName);
        compName = (MtplEditText) findViewById(R.id.edtCompName);
        email = (MtplEditText) findViewById(R.id.edtEmail);
        primMobileNo = (MtplEditText) findViewById(R.id.edtMobileNo);
        secMobileNo = (MtplEditText) findViewById(R.id.edtSecNo);
        address1 = (MtplEditText) findViewById(R.id.edtAddress1);
        address2 = (MtplEditText) findViewById(R.id.edtAddress2);
        locality = (MtplEditText) findViewById(R.id.edtLocality);
        city = (AutoCompleteTextView) findViewById(R.id.txtCity);
        state = (MtplEditText) findViewById(R.id.edtState);
        country = (MtplEditText) findViewById(R.id.edtCountry);
        pinCode = (MtplEditText) findViewById(R.id.edtPinCode);
        webUrl = (MtplEditText) findViewById(R.id.edtweburl);
        btnUpdateDetails = (MtplButton) findViewById(R.id.profileupdatedetails);


        proPic = (ImageView) findViewById(R.id.proPic);
        officePic = (ImageView) findViewById(R.id.offilePic);
        officePic1 = (ImageView) findViewById(R.id.offilePic1);
        panCard = (ImageView) findViewById(R.id.panCard);
        businessCard = (ImageView) findViewById(R.id.businessCard);
        comRegister = (ImageView) findViewById(R.id.compRegister);
        edtProfilepic = (MtplEditText) findViewById(R.id.edtProfilePic);
        edtOfficePic = (MtplEditText) findViewById(R.id.edtOfficePic);
        edtOfficePic1 = (MtplEditText) findViewById(R.id.edtOfficePic1);
        edtPanCard = (MtplEditText) findViewById(R.id.edtPanCard);
        edtBusinessCard = (MtplEditText) findViewById(R.id.edtBusinessCard);
        edtCompReg = (MtplEditText) findViewById(R.id.edtCompReg);
        edtDis = (MtplEditText) findViewById(R.id.edtDis);
        edtProfilepic.setOnFocusChangeListener(this);
        edtOfficePic.setOnFocusChangeListener(this);
        edtOfficePic1.setOnFocusChangeListener(this);
        edtPanCard.setOnFocusChangeListener(this);
        edtBusinessCard.setOnFocusChangeListener(this);
        edtCompReg.setOnFocusChangeListener(this);
        btnUpdateDetails.setOnClickListener(this);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewEditProfile.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CM.finishActivity(ViewEditProfile.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        switch (view.getId()) {
            case R.id.edtProfilePic:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
                break;
            case R.id.edtOfficePic:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);
                break;
            case R.id.edtOfficePic1:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 3);
                break;
            case R.id.edtPanCard:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 4);
                break;
            case R.id.edtBusinessCard:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 5);
                break;
            case R.id.edtCompReg:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 6);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // currentImage;
            final Uri photoUri = data.getData();
            switch (requestCode) {
                case 1:
                    if (photoUri != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    proPic.setImageBitmap(currentImage);
                                    edtProfilepic.setText(photoUri.getPath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                    }
                    break;
                case 2:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    officePic.setImageBitmap(currentImage);
                                    edtOfficePic.setText(photoUri.getPath());
                                } catch (Exception e) {

                                }
                            }
                        });


                    }
                    break;
                case 3:
                    if (photoUri != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    officePic1.setImageBitmap(currentImage);
                                    edtOfficePic1.setText(photoUri.getPath());
                                } catch (Exception e) {

                                }
                            }
                        });
                    }
                    break;
                case 4:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    panCard.setImageBitmap(currentImage);
                                    edtPanCard.setText(photoUri.getPath());


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case 5:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    businessCard.setImageBitmap(currentImage);
                                    edtBusinessCard.setText(photoUri.getPath());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case 6:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    comRegister.setImageBitmap(currentImage);
                                    edtCompReg.setText(photoUri.getPath());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;

            }


        }
    }


    public void webCallCity() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewEditProfile.this, true, true);
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
                    if (CM.isInternetAvailable(ViewEditProfile.this)) {
                        CM.showPopupCommonValidation(ViewEditProfile.this, error, false);
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
            CM.showPopupCommonValidation(ViewEditProfile.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
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
                    AutocompleteAdapter adptCountry1 = new AutocompleteAdapter(ViewEditProfile.this, R.layout.conntylayout, R.id.textViewSpinner, pojoCities);
                    city.setThreshold(1);
                    city.setAdapter(adptCountry1);
                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewEditProfile.this, e.getMessage(), false);
        }
    }

    public void webCallState() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewEditProfile.this, true, true);
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
                    if (CM.isInternetAvailable(ViewEditProfile.this)) {
                        CM.showPopupCommonValidation(ViewEditProfile.this, error, false);
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
            CM.showPopupCommonValidation(ViewEditProfile.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
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


                        person = new Person(pojoStateArrayList.get(i).getState_name(), pojoStateArrayList.get(i).getState_name());
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
            CM.showPopupCommonValidation(ViewEditProfile.this, e.getMessage(), false);
        }
    }


    public void webCallCountry() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewEditProfile.this, true, true);
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
                    if (CM.isInternetAvailable(ViewEditProfile.this)) {
                        CM.showPopupCommonValidation(ViewEditProfile.this, error, false);
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
            CM.showPopupCommonValidation(ViewEditProfile.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
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
            CM.showPopupCommonValidation(ViewEditProfile.this, e.getMessage(), false);
        }
    }


    public void webSubmit() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewEditProfile.this, true, true);
            WebService.getEditProfile(v, new OnVolleyHandler() {
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
                    if (CM.isInternetAvailable(ViewEditProfile.this)) {
                        CM.showPopupCommonValidation(ViewEditProfile.this, error, false);
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
            CM.showPopupCommonValidation(ViewEditProfile.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    if (jsonObject.optString("ResponseObject") != null) {

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
            CM.showPopupCommonValidation(ViewEditProfile.this, e.getMessage(), false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profileupdatedetails:


                if (!catName.getText().toString().equals("")) {

                    if (!first_name.getText().toString().equals("")) {

                        if (!last_name.getText().toString().equals("")) {

                            if (!compName.getText().toString().equals("")) {


                                if (!email.getText().toString().equals("") && CM.isEmailValid(email.getText().toString())) {

                                    if (!primMobileNo.getText().toString().equals("")) {

                                        if (primMobileNo.getText().length() == 10) {

                                            //Sec No.
                                            if (secMobileNo.getText().toString().length() > 0) {

                                                if (secMobileNo.getText().toString().length() == 10) {


                                                } else {
                                                    CM.showToast(getString(R.string.entvpno), ViewEditProfile.this);
                                                }


                                            } else {

                                                if (!address1.getText().toString().equals("")) {


                                                    if (!locality.getText().toString().equals("")) {


                                                        if (!city.getText().toString().equals("")) {


                                                            if (!state.getText().toString().equals("")) {

                                                                if (!country.getText().toString().equals("")) {

                                                                    if (!pinCode.getText().toString().equals("")) {


                                                                        List<Person> selectedItemList = completionView.getObjects();

                                                                        if (selectedItemList.size() == 5) {

                                                                        } else {
                                                                            CM.showToast(getString(R.string.entlist), ViewEditProfile.this);
                                                                        }


                                                                    } else {
                                                                        CM.showToast(getString(R.string.entpin), ViewEditProfile.this);
                                                                    }


                                                                } else {
                                                                    CM.showToast(getString(R.string.entcityfirst), ViewEditProfile.this);
                                                                }

                                                            } else {
                                                                CM.showToast(getString(R.string.entcityfirst), ViewEditProfile.this);
                                                            }


                                                        } else {
                                                            CM.showToast(getString(R.string.entcity), ViewEditProfile.this);
                                                        }


                                                    } else {
                                                        CM.showToast(getString(R.string.entlocality), ViewEditProfile.this);
                                                    }


                                                } else {

                                                    CM.showToast(getString(R.string.entAddres1), ViewEditProfile.this);

                                                }

                                            }


                                        } else {
                                            CM.showToast(getString(R.string.entvpno), ViewEditProfile.this);
                                        }


                                    } else {
                                        CM.showToast(getString(R.string.entpno), ViewEditProfile.this);
                                    }

                                } else {
                                    CM.showToast(getString(R.string.entvemail), ViewEditProfile.this);
                                }

                            } else {

                                CM.showToast(getString(R.string.entComName), ViewEditProfile.this);

                            }

                        } else {
                            CM.showToast(getString(R.string.entlname), ViewEditProfile.this);
                        }

                    } else {
                        CM.showToast(getString(R.string.entfname), ViewEditProfile.this);
                    }


                } else {
                    CM.showToast(getString(R.string.entcat), ViewEditProfile.this);
                }


                break;
        }
    }
}

