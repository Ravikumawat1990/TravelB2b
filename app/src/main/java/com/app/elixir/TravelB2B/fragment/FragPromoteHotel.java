package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.AutoCompletionView;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.model.Person;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplEditText;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoPromoCities;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.utils.URLS;
import com.app.elixir.TravelB2B.utils.Utility;
import com.app.elixir.TravelB2B.view.Popup_Hotel_Promote_preview;
import com.app.elixir.TravelB2B.volly.MtplProgressDialog;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;


/**
 * Created by NetSupport on 05-06-2017.
 */

//mohit
public class FragPromoteHotel extends Fragment implements View.OnClickListener {

    public String TAG = "Promote Hotel";
    private ArrayAdapter<Person> adapter;
    private int SELECT_FILE = 1;
    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    MtplEditText editUserName, editHotelName, editTariffCheapestRoom, editTariffExpensiveRoom, editHotelWebsite, editDuration, editCharge;
    Spinner spinnerHotelType;
    Bitmap PromoPic = null;
    MtplTextView textTotal, editUploadPhoto;
    MtplButton btnCart, btnPreview;
    AutoCompletionView promoteCity;
    MtplEditText autotxtState;
    ArrayList<String> hotelCateArray;
    ArrayList<Person> pojoStateArrayList;
    ArrayList<pojoPromoCities> seletedCityList;
    int totalAmt = 0, charge = 0, month = 1;
    String cityId = "", cityName = "", cityprices = "";
    String CheckCityResponse = "";

    MtplTextView webView;
    LinearLayout linearLayout;
    String userName, hotelName, hotelType, hotelCateId, hotelcheaproom, hotelexpensiveroom, hotelwebsite, location, city, duration, chargeString;
    private MtplProgressDialog mtplDialog;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle("Promote your hotel");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    ScrollView scroll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.promote, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle("Promote your hotel");
        scroll = (ScrollView) rootView.findViewById(R.id.root);

        setHasOptionsMenu(true);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        webView = (MtplTextView) rootView.findViewById(R.id.webView);


        if (CM.isInternetAvailable(thisActivity)) {
            sendRequest();
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
        }

        editUserName = (MtplEditText) rootView.findViewById(R.id.edtUserName);
        editHotelName = (MtplEditText) rootView.findViewById(R.id.edtHotelName);
        editTariffCheapestRoom = (MtplEditText) rootView.findViewById(R.id.edtCheapRoom);
        editTariffExpensiveRoom = (MtplEditText) rootView.findViewById(R.id.edtExpenciveRoom);
        editHotelWebsite = (MtplEditText) rootView.findViewById(R.id.edtHoelWebsite);
        editDuration = (MtplEditText) rootView.findViewById(R.id.edtDuration);
        editCharge = (MtplEditText) rootView.findViewById(R.id.edtCharge);
        editUploadPhoto = (MtplTextView) rootView.findViewById(R.id.edtPromoPic);
        spinnerHotelType = (Spinner) rootView.findViewById(R.id.spinnerHotel);
        textTotal = (MtplTextView) rootView.findViewById(R.id.txtTotal1);
        btnCart = (MtplButton) rootView.findViewById(R.id.btnCart);
        btnPreview = (MtplButton) rootView.findViewById(R.id.btnPriview);
        autotxtState = (MtplEditText) rootView.findViewById(R.id.autotextState);
        promoteCity = (AutoCompletionView) rootView.findViewById(R.id.citypromote);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.layoutTop);


        editUserName.setText(CM.getSp(thisActivity, CV.Preffirst_name, "").toString() + " " + CM.getSp(thisActivity, CV.Preflast_name, "").toString());
        editUserName.setEnabled(false);
        editDuration.setText("" + month);
        editCharge.setEnabled(false);

        btnPreview.setOnClickListener(this);
        pojoStateArrayList = new ArrayList<>();
        seletedCityList = new ArrayList<>();

        thisActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webCallcities();

            }
        });

        hotelCateArray = new ArrayList<>();
        hotelCateArray = CM.getHotelCate(CM.getSp(thisActivity, "hotelCate", "").toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(thisActivity, android.R.layout.simple_spinner_item, hotelCateArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHotelType.setAdapter(adapter);

        spinnerHotelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int k = i + 1;
                hotelCateId = "" + k;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        promoteCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Person personArrayList = (Person) adapterView.getAdapter().getItem(i);
                personArrayList.getName();
                personArrayList.getEmail();

                String lable = personArrayList.getName().substring(0, personArrayList.getName().indexOf(" "));
                String id = personArrayList.getEmail().substring(0, personArrayList.getEmail().indexOf("@"));
                String price = personArrayList.getEmail().substring(personArrayList.getEmail().lastIndexOf("@") + 1);

                boolean bool = true;
                for (int j = 0; j < seletedCityList.size(); j++) {
                    if (id.equalsIgnoreCase(seletedCityList.get(j).getValue())) {
                        bool = false;
                        break;
                    } else {
                        bool = true;
                    }
                }
                if (bool) {

                    webCallCheckCity(CM.getSp(thisActivity, CV.PrefID, "").toString(), id, "" + month, id, lable, price);

                }

            }
        });


        promoteCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (promoteCity.getObjects().size() < seletedCityList.size()) {
                    for (int j = 0; j < seletedCityList.size(); j++) {
                        boolean bool = false;
                        for (int k = 0; k < promoteCity.getObjects().size(); k++) {
                            String id = promoteCity.getObjects().get(k).getEmail().substring(0, promoteCity.getObjects().get(k).getEmail().indexOf("@"));
                            if (!seletedCityList.get(j).getValue().equalsIgnoreCase(id)) {
                                bool = false;
                            } else {
                                bool = true;
                                break;
                            }
                        }

                        if (!bool) {

                            totalAmt = totalAmt - Integer.parseInt(seletedCityList.get(j).getPrice());
                            textTotal.setText("Total :" + totalAmt);
                            charge = month * totalAmt;
                            editCharge.setText("" + charge);
                            seletedCityList.remove(j);
                        }

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        editUploadPhoto.setOnClickListener(this);

        editDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (!editDuration.getText().toString().equals("")) {
                    month = Integer.parseInt(editDuration.getText().toString());
                    charge = month * totalAmt;

                    editCharge.setText("" + charge);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnPriview:
                getPreview();
                break;
            case R.id.edtPromoPic:
                galleryIntent();
                break;


        }

    }


    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.myresponsedetail, menu);
        menu.findItem(R.id.noti).setVisible(false);
        menu.findItem(R.id.sort).setVisible(false);
        menu.findItem(R.id.filter).setVisible(false);
        menu.findItem(R.id.cartMenu).setVisible(false);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //  MenuItem item = menu.findItem(R.id.cartMenu);
        // item.setVisible(false);
        menu.findItem(R.id.noti).setVisible(false);
        menu.findItem(R.id.sort).setVisible(false);
        menu.findItem(R.id.filter).setVisible(false);
        menu.findItem(R.id.cartMenu).setVisible(false);
    }

    public void showPopup(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to unblock this user?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logo3).show();
    }

    public void webCallcities() {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getPromocities(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCities(response);

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


    public void webCallCheckCity(String paramuserId, String paramcityId, String paramduration, final String id, final String lable, final String price) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getCheckCity(v, paramuserId, paramcityId, paramduration, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCheckCities(response, id, lable, price);

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


    private void getResponseForCheckCities(String response, String id, String lable, String price) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":

                    if (jsonObject.optString("response_object").equals("Success")) {
                        CheckCityResponse = "Success";
                        pojoPromoCities pojo = new pojoPromoCities();
                        pojo.setLable(lable);
                        pojo.setValue(id);
                        pojo.setPrice(price);

                        totalAmt = totalAmt + Integer.parseInt(price);
                        charge = month * totalAmt;
                        editCharge.setText("" + charge);
                        textTotal.setText("Total :" + totalAmt);
                        seletedCityList.add(pojo);


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

    private void getResponseForCities(String response) {
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
                        JSONArray jsonArray = new JSONArray(jsonObject.optString("response_object"));
                        totalAmt = 0;
                        for (int i = 0; i < jsonArray.length(); i++) {

                            String lable, id;
                            lable = jsonArray.getJSONObject(i).optString("label").toString() + " (" + jsonArray.getJSONObject(i).optString("state_name").toString() + ") (" + jsonArray.getJSONObject(i).optString("usercount").toString() + ")";
                            id = jsonArray.getJSONObject(i).optString("value") + "@" + jsonArray.getJSONObject(i).optString("price");
                            Person pojo = new Person(lable, id);
                            pojoStateArrayList.add(pojo);
                        }
                    }

                    adapter = new ArrayAdapter<Person>(thisActivity, android.R.layout.simple_list_item_1, pojoStateArrayList) {

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

                    Typeface face = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_regular));
                    promoteCity.setTypeface(face);
                    promoteCity.setAdapter(adapter);
                    promoteCity.invalidate();
                    //promoteCity.setTokenLimit(5);
                    promoteCity.allowDuplicates(false);


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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                }
                break;
        }
    }


    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);

        }
    }

    private void onSelectFromGalleryResult(Intent data) {

        if (data != null) {
            try {

                PromoPic = MediaStore.Images.Media.getBitmap(thisActivity.getApplicationContext().getContentResolver(), data.getData());
                final Uri photoUri = data.getData();
                editUploadPhoto.setText(photoUri.getPath());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                PromoPic.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                long lengthbmp = imageInByte.length;

                if (lengthbmp / (1024 * 1024) > 2) {
                    CM.showToast("Large Image Size", thisActivity);
                    PromoPic = null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    void getPreview() {


        userName = editUserName.getText().toString();
        hotelName = editHotelName.getText().toString();
        hotelType = spinnerHotelType.getSelectedItem().toString();
        hotelcheaproom = editTariffCheapestRoom.getText().toString();
        hotelexpensiveroom = editTariffExpensiveRoom.getText().toString();
        hotelwebsite = editHotelWebsite.getText().toString();
        location = autotxtState.getText().toString();
        duration = editDuration.getText().toString();
        chargeString = editCharge.getText().toString();
        if (!userName.matches("")) {
            if (!hotelName.matches("")) {
                if (!hotelType.matches("")) {
                    if (!hotelcheaproom.matches("")) {
                        if (!hotelexpensiveroom.matches("")) {
                            if (!hotelwebsite.matches("")) {
                                if (!location.matches("")) {
                                    if (seletedCityList.size() > 0) { //change with array city id price
                                        cityId = "";
                                        cityprices = "";
                                        cityName = "";
                                        for (int i = 0; i < seletedCityList.size(); i++) {
                                            if (cityId.equals("")) {
                                                cityId = seletedCityList.get(i).getValue();
                                                cityprices = seletedCityList.get(i).getPrice();
                                                cityName = seletedCityList.get(i).getLable();
                                            } else {
                                                cityId = cityId + "," + seletedCityList.get(i).getValue();
                                                cityprices = cityprices + "," + seletedCityList.get(i).getPrice();
                                                cityName = cityName + "," + seletedCityList.get(i).getLable();
                                            }
                                        }


                                        if (!duration.matches("")) {
                                            //Check Image Selected
                                            if (PromoPic != null) {


                                                thisActivity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        new TestAsync().execute();
                                                    }
                                                });

                                            } else {
                                                CM.showToast("Select Image", thisActivity);
                                            }

                                        } else {
                                            CM.showToast("Enter Duration", thisActivity);
                                        }
                                    } else {
                                        CM.showToast("Select City", thisActivity);
                                    }
                                } else {
                                    CM.showToast("Enter location", thisActivity);
                                }
                            } else {
                                CM.showToast("Enter Hotel Website", thisActivity);
                            }
                        } else {
                            CM.showToast("Enter Espensive Room Tarrif", thisActivity);
                        }
                    } else {
                        CM.showToast("Enter Cheap Room Tarrif", thisActivity);
                    }
                } else {
                    CM.showToast("Select Hotel Type", thisActivity);
                }
            } else {
                CM.showToast("Enter Hotel Name", thisActivity);
            }
        } else {
            CM.showToast("Enter User Name", thisActivity);
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


    private void sendRequest() {

        StringRequest stringRequest = new StringRequest(URLS.PROMOTEHOTEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        Log.i("", "onResponse: " + response);


                        thisActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response.toString());
                                    if (jsonObject.getString("description").toString() != null && !jsonObject.getString("description").toString().equals("null")) {
                                        webView.setText(CM.fromHtml(jsonObject.getString("description").toString()));
                                        webView.setMovementMethod(ScrollingMovementMethod.getInstance());
                                    } else {
                                        webView.setVisibility(View.GONE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(thisActivity, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(thisActivity);
        requestQueue.add(stringRequest);
    }


    class TestAsync extends AsyncTask<Void, Integer, String> {
        String TAG = getClass().getSimpleName();
        protected ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
           /* progressDialog = new ProgressDialog(thisActivity);
            progressDialog.setMessage("loading");
            progressDialog.show();*/
            Log.d(TAG + " PreExceute", "On pre Exceute......");
            showMtplDialog(thisActivity);
        }

        protected String doInBackground(Void... arg0) {
            Log.d(TAG + " DoINBackGround", "On doInBackground...");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PromoPic.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            return encodedImage;
        }

        protected void onProgressUpdate(Integer... a) {
            super.onProgressUpdate(a);
            Log.d(TAG + " onProgressUpdate", "You are in progress update ... " + a[0]);
        }

        protected void onPostExecute(String encodedImage) {
            super.onPostExecute(encodedImage);
            Log.d(TAG + " onPostExecute", "" + encodedImage);

            dismissMtplDialog(thisActivity);
            if (encodedImage != null && !encodedImage.equals("")) {
                CM.setSp(thisActivity, "img", encodedImage);
                Intent intent = new Intent(thisActivity, Popup_Hotel_Promote_preview.class);
                Bundle bundle = new Bundle();

                bundle.putString("username", userName);
                bundle.putString("userid", CM.getSp(thisActivity, CV.PrefID, "").toString());
                bundle.putString("promppic", "");
                bundle.putString("hname", hotelName);
                bundle.putString("htype", hotelType);
                bundle.putString("ctarrifroom", hotelcheaproom);
                bundle.putString("etarrifroom", hotelexpensiveroom);
                bundle.putString("hwebsite", hotelwebsite);
                bundle.putString("hlocation", location);
                //change city id price
                bundle.putString("hcity", cityName);
                bundle.putString("hcityprice", cityprices);
                bundle.putString("hcityid", cityId);
                bundle.putString("hduration", duration);
                bundle.putString("hcharge", chargeString);
                bundle.putString("hcateId", hotelCateId);

                intent.putExtras(bundle);
                //  progressDoalog.dismiss();
                CM.startActivity(intent, thisActivity);
            }
        }
    }

    public void showMtplDialog(Activity mActivity) {
        if (mActivity.isFinishing()) {
            return;
        }
        if (mtplDialog == null)
            mtplDialog = new MtplProgressDialog(mActivity, "", false);
        if (!mtplDialog.isShowing())
            mtplDialog.show();
    }


    private void dismissMtplDialog(Activity activity) {

        if (mtplDialog != null && mtplDialog.isShowing())
            mtplDialog.dismiss();
    }

}
