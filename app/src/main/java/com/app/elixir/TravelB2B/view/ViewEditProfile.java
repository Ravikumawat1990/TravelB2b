package com.app.elixir.TravelB2B.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    Spinner spinner;
    String countryId, cityId, stateId;
    List<String> prefArray;
    ImageView imageIata, imagetafipic, imageTaaipic, imageiatopic, imageadyo, imageiso, imageufta, imageadto;
    ImageView offilePic, offilePic1, compRegister;
    //mohit
    boolean boolIata, boolTafi, boolTaai, boolIato, boolAdyoi, boolIso9001, boolUftaa, boolAdtoi, boolProfilePic, boolOffice1, boolOffice2, boolPanCard, boolBussCard, boolComActReg;

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
            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_roboto_black));
            titleTextView.setTypeface(font);
            titleTextView.setTextSize(18);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                spinner = (Spinner) findViewById(R.id.spinner);
                spinner.setEnabled(false);
                final String[] cat = getResources().getStringArray(R.array.catArray);
                ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(ViewEditProfile.this, R.layout.support_simple_spinner_dropdown_item, cat) {

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
                spinner.setAdapter(langAdapter);

                pojoStates = new ArrayList<>();
                adapter = new ArrayAdapter<Person>(ViewEditProfile.this, android.R.layout.simple_list_item_1, pojoStates) {

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
                completionView.setDuplicateParentStateEnabled(true);

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
            }
        });

        webUserProfile(CM.getSp(ViewEditProfile.this, CV.PrefID, "").toString());


    }

    private void initView() {
        countryArrayList = new ArrayList<>();
        pojoCities = new ArrayList<>();
        pojoStateArrayList = new ArrayList<>();
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

        //Certificates
        imageIata = (ImageView) findViewById(R.id.imageIata);
        imagetafipic = (ImageView) findViewById(R.id.imagetafipic);
        imageTaaipic = (ImageView) findViewById(R.id.imageTaaipic);
        imageiatopic = (ImageView) findViewById(R.id.imageiatopic);
        imageadyo = (ImageView) findViewById(R.id.imageadyo);
        imageiso = (ImageView) findViewById(R.id.imageiso);
        imageufta = (ImageView) findViewById(R.id.imageufta);
        imageadto = (ImageView) findViewById(R.id.imageadto);

        //Uploades
        proPic = (ImageView) findViewById(R.id.proPic);
        offilePic = (ImageView) findViewById(R.id.offilePic);
        offilePic1 = (ImageView) findViewById(R.id.offilePic1);
        panCard = (ImageView) findViewById(R.id.panCard);
        businessCard = (ImageView) findViewById(R.id.businessCard);
        compRegister = (ImageView) findViewById(R.id.compRegister);


        MtplButton btnIata = (MtplButton) findViewById(R.id.btnIata);
        MtplButton btntafipic = (MtplButton) findViewById(R.id.btntafipic);
        MtplButton btntaaipic = (MtplButton) findViewById(R.id.btntaaipic);
        MtplButton btniatopic = (MtplButton) findViewById(R.id.btniatopic);
        MtplButton btnadyoipic = (MtplButton) findViewById(R.id.btnadyoipic);
        MtplButton btniso9001pic = (MtplButton) findViewById(R.id.btniso9001pic);
        MtplButton btnuftaapic = (MtplButton) findViewById(R.id.btnuftaapic);
        MtplButton btnadtoipic = (MtplButton) findViewById(R.id.btnadtoipic);

        btnIata.setOnClickListener(this);
        btntafipic.setOnClickListener(this);
        btntaaipic.setOnClickListener(this);
        btniatopic.setOnClickListener(this);
        btnadyoipic.setOnClickListener(this);
        btniso9001pic.setOnClickListener(this);
        btnuftaapic.setOnClickListener(this);
        btnadtoipic.setOnClickListener(this);


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
                                    proPic.setImageBitmap(CM.getResizedBitmap(currentImage, 75));
                                    edtProfilepic.setText(photoUri.getPath());
                                    boolProfilePic = true;
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
                                    officePic.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    edtOfficePic.setText(photoUri.getPath());
                                    boolOffice1 = true;
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
                                    officePic1.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    edtOfficePic1.setText(photoUri.getPath());
                                    boolOffice2 = true;
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
                                    panCard.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    edtPanCard.setText(photoUri.getPath());
                                    boolPanCard = true;


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
                                    businessCard.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    edtBusinessCard.setText(photoUri.getPath());
                                    boolBussCard = true;
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
                                    comRegister.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    edtCompReg.setText(photoUri.getPath());
                                    boolComActReg = true;


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case 7:

                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    imageIata.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    boolIata = true;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case 8:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    imagetafipic.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    boolTafi = true;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case 9:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    imageTaaipic.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    boolTaai = true;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case 10:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    imageiatopic.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    boolIato = true;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case 11:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    imageadyo.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    boolAdyoi = true;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case 12:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    imageiso.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    boolIso9001 = true;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case 13:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    imageufta.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));
                                    boolUftaa = true;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case 14:
                    if (photoUri != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap currentImage = MediaStore.Images.Media.getBitmap(ViewEditProfile.this.getContentResolver(), photoUri);
                                    imageadto.setImageBitmap(CM.getResizedBitmap(currentImage, 200, 200));

                                    boolAdtoi = true;
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


                    pojoCity cityPojo = new pojoCity();
                    cityPojo.setId(cityId);
                    int indexCity = pojoCities.indexOf(cityPojo);
                    Log.i(TAG, "getResponseForCity: " + indexCity);
                    city.setText(pojoCities.get(indexCity).getName());


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


                        person = new Person(pojoStateArrayList.get(i).getState_name(), pojoStateArrayList.get(i).getId());
                        pojoStates.add(person);

                    }


                    pojoState statePojo = new pojoState();
                    statePojo.setId(stateId);
                    int indexState = pojoStateArrayList.indexOf(statePojo);
                    state.setText(pojoStateArrayList.get(indexState).getState_name());

                    StringBuilder stringBuilder = new StringBuilder();

                    if (prefArray != null) {
                        for (int i = 0; i < prefArray.size(); i++) {

                            pojoState statePojo1 = new pojoState();
                            statePojo1.setId(prefArray.get(i));
                            int indexState1 = pojoStateArrayList.indexOf(statePojo1);
                            stringBuilder.append(pojoStateArrayList.get(indexState1).getState_name());
                            stringBuilder.append(" ");

                            Person person = new Person(pojoStateArrayList.get(indexState1).getState_name(), pojoStateArrayList.get(indexState1).getId());
                            completionView.addObject(person);


                        }
                    }


                    completionView.getObjects();

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
                    pojoCountry countryPojo = new pojoCountry();
                    countryPojo.setId(countryId);
                    int indexCountry = countryArrayList.indexOf(countryPojo);
                    country.setText(countryArrayList.get(indexCountry).getCountry_name());

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


    public void webSubmit(Map<String, String> params) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewEditProfile.this, true, true);
            WebService.getSubmitEditProfile(v, params, new OnVolleyHandler() {
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

                        CM.showToast(jsonObject.optString("ResponseObject").toString(), ViewEditProfile.this);

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
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        switch (view.getId()) {

            case R.id.profileupdatedetails:


                if (CM.isInternetAvailable(ViewEditProfile.this)) {


                    if (!first_name.getText().toString().equals("")) {

                        if (!last_name.getText().toString().equals("")) {

                            if (!compName.getText().toString().equals("")) {


                                if (!email.getText().toString().equals("") && CM.isEmailValid(email.getText().toString())) {

                                    if (!primMobileNo.getText().toString().equals("")) {

                                        if (primMobileNo.getText().length() == 10) {

                                            //Sec No.
                                            if (secMobileNo.getText().toString().length() > 0) {

                                                if (secMobileNo.getText().toString().length() == 10) {


                                                    if (!address1.getText().toString().equals("")) {


                                                        if (!locality.getText().toString().equals("")) {


                                                            if (!city.getText().toString().equals("")) {


                                                                if (!state.getText().toString().equals("")) {

                                                                    if (!country.getText().toString().equals("")) {

                                                                        if (!pinCode.getText().toString().equals("")) {


                                                                            List<Person> selectedItemList = completionView.getObjects();

                                                                            if (selectedItemList.size() == 5) {


                                                                                Map<String, String> params = new HashMap<>();
                                                                                // params.put("cateName", spinner.getSelectedItem().toString());
                                                                                params.put("user_id", CM.getSp(ViewEditProfile.this, CV.PrefID, "").toString());
                                                                                params.put("first_name", first_name.getText().toString());
                                                                                params.put("last_name", last_name.getText().toString());
                                                                                params.put("company_name", compName.getText().toString());
                                                                                //  params.put("email", email.getText().toString());
                                                                                params.put("mobile_number", primMobileNo.getText().toString());
                                                                                params.put("p_contact", secMobileNo.getText().toString());
                                                                                params.put("address", address1.getText().toString());
                                                                                params.put("address1", address2.getText().toString());
                                                                                params.put("locality", locality.getText().toString());
                                                                                params.put("city_id", cityId);
                                                                                params.put("state_id", stateId);
                                                                                params.put("country_id", countryId);
                                                                                params.put("pincode", pinCode.getText().toString());
                                                                                params.put("web_url", webUrl.getText().toString());

                                                                                StringBuilder stringBuilder = new StringBuilder();
                                                                                ArrayList<String> strings = new ArrayList<>();
                                                                                for (int i = 0; i < selectedItemList.size(); i++) {
                                                                                    strings.add(selectedItemList.get(i).getEmail());
                                                                                }
                                                                                String stateList = strings.toString().replace("[", "").replace("]", "")
                                                                                        .replace(", ", ",");

                                                                                params.put("preference", stateList);


                                                                                String iataImg = BASE64String(imageIata);
                                                                                String tafipic = BASE64String(imagetafipic);
                                                                                String taaipic = BASE64String(imageTaaipic);
                                                                                String iatopic = BASE64String(imageiatopic);
                                                                                String adyo = BASE64String(imageadyo);
                                                                                String iso900 = BASE64String(imageiso);
                                                                                String ufta = BASE64String(imageufta);
                                                                                String adto = BASE64String(imageadto);
                                                                                String proPics = BASE64String(proPic);
                                                                                String officePics = BASE64String(officePic);
                                                                                String officePic1s = BASE64String(officePic1);
                                                                                String panCards = BASE64String(panCard);
                                                                                String businessCards = BASE64String(businessCard);
                                                                                String comRegisters = BASE64String(comRegister);


                                                                               /* params.put("iata_pic", iataImg);
                                                                                params.put("tafi_pic", tafipic);
                                                                                params.put("taai_pic", taaipic);
                                                                                params.put("iato_pic", iatopic);
                                                                                params.put("adyoi_pic", adyo);
                                                                                params.put("iso9001_pic", iso900);
                                                                                params.put("uftaa_pic", ufta);
                                                                                params.put("adtoi_pic", adto);
                                                                                params.put("profile_pic", proPics);
                                                                                params.put("company_img_1", officePics);
                                                                                params.put("company_img_2", officePic1s);
                                                                                params.put("pancard", panCards);
                                                                                params.put("id_card", businessCards);
                                                                                params.put("company_shop_registration", comRegisters);*/


                                                                                if (boolIata) {
                                                                                    params.put("iata_pic", iataImg);
                                                                                } else {
                                                                                    params.put("iata_pic", "");
                                                                                }
                                                                                if (boolTafi) {
                                                                                    params.put("tafi_pic", tafipic);
                                                                                } else {
                                                                                    params.put("tafi_pic", "");
                                                                                }
                                                                                if (boolTaai) {
                                                                                    params.put("taai_pic", taaipic);
                                                                                } else {
                                                                                    params.put("taai_pic", "");
                                                                                }
                                                                                if (boolIato) {
                                                                                    params.put("iato_pic", iatopic);
                                                                                } else {
                                                                                    params.put("iato_pic", "");
                                                                                }
                                                                                if (boolAdyoi) {
                                                                                    params.put("adyoi_pic", adyo);
                                                                                } else {
                                                                                    params.put("adyoi_pic", "");
                                                                                }
                                                                                if (boolUftaa) {
                                                                                    params.put("uftaa_pic", ufta);
                                                                                } else {
                                                                                    params.put("uftaa_pic", "");
                                                                                }
                                                                                if (boolAdtoi) {
                                                                                    params.put("adtoi_pic", adto);
                                                                                } else {
                                                                                    params.put("adtoi_pic", "");
                                                                                }
                                                                                if (boolProfilePic) {
                                                                                    params.put("profile_pic", proPics);
                                                                                } else {
                                                                                    params.put("profile_pic", "");
                                                                                }
                                                                                if (boolOffice1) {
                                                                                    params.put("company_img_1", officePics);
                                                                                } else {
                                                                                    params.put("company_img_1", "");
                                                                                }
                                                                                if (boolOffice2) {
                                                                                    params.put("company_img_2", officePic1s);
                                                                                } else {
                                                                                    params.put("company_img_2", "");
                                                                                }
                                                                                if (boolPanCard) {
                                                                                    params.put("pancard", panCards);
                                                                                } else {
                                                                                    params.put("pancard", "");
                                                                                }
                                                                                if (boolBussCard) {
                                                                                    params.put("id_card", businessCards);
                                                                                } else {
                                                                                    params.put("id_card", "");
                                                                                }
                                                                                if (boolComActReg) {
                                                                                    params.put("company_shop_registration", comRegisters);
                                                                                } else {
                                                                                    params.put("company_shop_registration", "");
                                                                                }


                                                                                params.put("description", edtDis.getText().toString());
                                                                                webSubmit(params);


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


                                                                            Map<String, String> params = new HashMap<>();
                                                                            // params.put("cateName", spinner.getSelectedItem().toString());
                                                                            params.put("user_id", CM.getSp(ViewEditProfile.this, CV.PrefID, "").toString());
                                                                            params.put("first_name", first_name.getText().toString());
                                                                            params.put("last_name", last_name.getText().toString());
                                                                            params.put("company_name", compName.getText().toString());
                                                                            //  params.put("email", email.getText().toString());
                                                                            params.put("mobile_number", primMobileNo.getText().toString());
                                                                            params.put("p_contact", secMobileNo.getText().toString());
                                                                            params.put("address", address1.getText().toString());
                                                                            params.put("address1", address2.getText().toString());
                                                                            params.put("locality", locality.getText().toString());
                                                                            params.put("city_id", cityId);
                                                                            params.put("state_id", stateId);
                                                                            params.put("country_id", countryId);
                                                                            params.put("pincode", pinCode.getText().toString());
                                                                            params.put("web_url", webUrl.getText().toString());

                                                                            StringBuilder stringBuilder = new StringBuilder();
                                                                            ArrayList<String> strings = new ArrayList<>();
                                                                            for (int i = 0; i < selectedItemList.size(); i++) {
                                                                                strings.add(selectedItemList.get(i).getEmail());
                                                                            }
                                                                            String stateList = strings.toString().replace("[", "").replace("]", "")
                                                                                    .replace(", ", ",");

                                                                            params.put("preference", stateList);


                                                                            String iataImg = BASE64String(imageIata);
                                                                            String tafipic = BASE64String(imagetafipic);
                                                                            String taaipic = BASE64String(imageTaaipic);
                                                                            String iatopic = BASE64String(imageiatopic);
                                                                            String adyo = BASE64String(imageadyo);
                                                                            String iso900 = BASE64String(imageiso);
                                                                            String ufta = BASE64String(imageufta);
                                                                            String adto = BASE64String(imageadto);
                                                                            String proPics = BASE64String(proPic);
                                                                            String officePics = BASE64String(officePic);
                                                                            String officePic1s = BASE64String(officePic1);
                                                                            String panCards = BASE64String(panCard);
                                                                            String businessCards = BASE64String(businessCard);
                                                                            String comRegisters = BASE64String(comRegister);

                                                                            if (boolIata) {
                                                                                params.put("iata_pic", iataImg);
                                                                            } else {
                                                                                params.put("iata_pic", "");
                                                                            }
                                                                            if (boolTafi) {
                                                                                params.put("tafi_pic", tafipic);
                                                                            } else {
                                                                                params.put("tafi_pic", "");
                                                                            }
                                                                            if (boolTaai) {
                                                                                params.put("taai_pic", taaipic);
                                                                            } else {
                                                                                params.put("taai_pic", "");
                                                                            }
                                                                            if (boolIato) {
                                                                                params.put("iato_pic", iatopic);
                                                                            } else {
                                                                                params.put("iato_pic", "");
                                                                            }
                                                                            if (boolAdyoi) {
                                                                                params.put("adyoi_pic", adyo);
                                                                            } else {
                                                                                params.put("adyoi_pic", "");
                                                                            }
                                                                            if (boolUftaa) {
                                                                                params.put("uftaa_pic", ufta);
                                                                            } else {
                                                                                params.put("uftaa_pic", "");
                                                                            }
                                                                            if (boolAdtoi) {
                                                                                params.put("adtoi_pic", adto);
                                                                            } else {
                                                                                params.put("adtoi_pic", "");
                                                                            }
                                                                            if (boolProfilePic) {
                                                                                params.put("profile_pic", proPics);
                                                                            } else {
                                                                                params.put("profile_pic", "");
                                                                            }
                                                                            if (boolOffice1) {
                                                                                params.put("company_img_1", officePics);
                                                                            } else {
                                                                                params.put("company_img_1", "");
                                                                            }
                                                                            if (boolOffice2) {
                                                                                params.put("company_img_2", officePic1s);
                                                                            } else {
                                                                                params.put("company_img_2", "");
                                                                            }
                                                                            if (boolPanCard) {
                                                                                params.put("pancard", panCards);
                                                                            } else {
                                                                                params.put("pancard", "");
                                                                            }
                                                                            if (boolBussCard) {
                                                                                params.put("id_card", businessCards);
                                                                            } else {
                                                                                params.put("id_card", "");
                                                                            }
                                                                            if (boolComActReg) {
                                                                                params.put("company_shop_registration", comRegisters);
                                                                            } else {
                                                                                params.put("company_shop_registration", "");
                                                                            }
                                                                            webSubmit(params);


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
                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewEditProfile.this);
                }
                break;
            case R.id.btnIata:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 7);
                break;
            case R.id.btntafipic:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 8);
                break;
            case R.id.btntaaipic:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 9);
                break;
            case R.id.btniatopic:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 10);
                break;
            case R.id.btnadyoipic:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 11);
                break;
            case R.id.btniso9001pic:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 12);
                break;
            case R.id.btnuftaapic:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 13);
                break;
            case R.id.btnadtoipic:
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 14);
                break;
        }
    }

    public void webUserProfile(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewEditProfile.this, true, true);
            WebService.getEditProfile(v, userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForUserProfile(response);

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

    private void getResponseForUserProfile(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewEditProfile.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("user"));
                    if (jsonObject1.optString("role_id").toString().equals("1")) {
                        spinner.setSelection(0);
                    } else if (jsonObject1.optString("role_id").toString().equals("2")) {
                        spinner.setSelection(1);
                    } else {
                        spinner.setSelection(2);
                    }
                    first_name.setText(jsonObject1.optString("first_name"));
                    last_name.setText(jsonObject1.optString("last_name"));
                    primMobileNo.setText(jsonObject1.optString("mobile_number"));
                    secMobileNo.setText(jsonObject1.optString("p_contact"));
                    email.setText(jsonObject1.optString("email"));
                    address1.setText(jsonObject1.optString("address"));
                    locality.setText(jsonObject1.optString("locality"));
                    pinCode.setText(jsonObject1.optString("pincode"));
                    compName.setText(jsonObject1.optString("company_name"));
                    webUrl.setText(jsonObject1.optString("web_url"));
                    address2.setText("");
                    countryId = jsonObject1.optString("country_id");
                    cityId = jsonObject1.optString("city_id");
                    stateId = jsonObject1.optString("state_id");


                    // ImageView imageIata, imagetafipic, imageTaaipic, imageiatopic, imageadyo, imageiso, imageufta, imageadto;
                    // ImageView offilePic, offilePic1, compRegister;

                    try {

                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("iata_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(imageIata);

                    } catch (Exception e) {

                    }

                    try {
                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("tafi_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(imagetafipic);
                    } catch (Exception e) {

                    }
                    try {
                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("taai_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(imageTaaipic);
                    } catch (Exception e) {

                    }
                    try {
                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("iato_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(imageiatopic);
                    } catch (Exception e) {

                    }

                    try {
                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("adyoi_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(imageadyo);
                    } catch (Exception e) {

                    }
                    try {
                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("iso9001_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(imageiso);
                    } catch (Exception e) {

                    }
                    try {
                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("uftaa_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(imageufta);
                    } catch (Exception e) {

                    }

                    try {

                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("adtoi_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(imageadto);
                    } catch (Exception e) {

                    }

                    try {

                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("profile_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(proPic);
                    } catch (Exception e) {

                    }
                    try {
                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("company_img_1_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(offilePic);
                    } catch (Exception e) {

                    }
                    try {

                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("company_img_2_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(offilePic1);
                    } catch (Exception e) {

                    }
                    try {

                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("id_card_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(panCard);
                    } catch (Exception e) {

                    }

                    try {
                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("company_shop_registration_pic"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(compRegister);
                    } catch (Exception e) {

                    }

                    try {
                        Picasso.with(ViewEditProfile.this)
                                .load(jsonObject1.optString("travel_certificates"))
                                .placeholder(R.drawable.ic_photo_black_48dp).into(businessCard);
                    } catch (Exception e) {

                    }
                    if (jsonObject1.optString("preference").equals("null") || jsonObject1.optString("preference").equals("")) {

                    } else {
                        prefArray = Arrays.asList(jsonObject1.optString("preference").split("\\s*,\\s*"));

                    }


                    webCallCity();
                    webCallState();
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
            CM.showPopupCommonValidation(ViewEditProfile.this, e.getMessage(), false);
        }
    }


    public String BASE64String(ImageView image) {
        String encodedImage = "";
        try {
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            Log.i(TAG, "initView: " + encodedImage);

        } catch (Exception e) {
            encodedImage = "";
        }


        return encodedImage;
    }

}

