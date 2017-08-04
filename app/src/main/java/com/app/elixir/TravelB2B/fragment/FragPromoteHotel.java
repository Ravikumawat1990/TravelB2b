package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.AutoCompleteStateAdapter;
import com.app.elixir.TravelB2B.adapter.adptPromortioncitySelected;
import com.app.elixir.TravelB2B.adapter.adptPromotionCityAll;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplEditText;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoPromotionCitys;
import com.app.elixir.TravelB2B.pojos.pojoState;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.utils.URLS;
import com.app.elixir.TravelB2B.utils.Utility;
import com.app.elixir.TravelB2B.view.Popup_Hotel_Promote_preview;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Created by NetSupport on 05-06-2017.
 */

//mohit
public class FragPromoteHotel extends Fragment implements View.OnClickListener {

    public String TAG = "Promote Hotel";

    private int SELECT_FILE = 1;
    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    MtplEditText editUserName, editHotelName, editTariffCheapestRoom, editTariffExpensiveRoom, editHotelWebsite, editDuration, editCharge;
    Spinner spinnerHotelType;
    Bitmap PromoPic = null;
    MtplTextView textTotal, editUploadPhoto;
    MtplButton btnCart, btnPreview;
    AutoCompleteTextView autotxtState;
    RecyclerView allCityListview, selectedCityListview;

    adptPromotionCityAll allCityAdapter;
    adptPromortioncitySelected selectedCityAdapter;
    ArrayList<pojoState> pojoStateArrayList;

    int totalAmt = 0, charge = 0, month = 1;
    String stateId;
    ArrayList<pojoPromotionCitys> promoAllCity = new ArrayList<pojoPromotionCitys>();
    ArrayList<pojoPromotionCitys> promoSelectedCity = new ArrayList<pojoPromotionCitys>();
    MtplTextView webView;
    LinearLayout linearLayout;

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
        //   scroll.scrollTo(0, scroll.getTop());
        //scroll.fullScroll(View.FOCUS_UP);


        //scrollview.pageScroll(View.FOCUS_UP);
        setHasOptionsMenu(true);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        webView = (MtplTextView) rootView.findViewById(R.id.webView);
        sendRequest();
        editUserName = (MtplEditText) rootView.findViewById(R.id.edtUserName);
        editHotelName = (MtplEditText) rootView.findViewById(R.id.edtHotelName);
        editTariffCheapestRoom = (MtplEditText) rootView.findViewById(R.id.edtCheapRoom);
        editTariffExpensiveRoom = (MtplEditText) rootView.findViewById(R.id.edtExpenciveRoom);
        editHotelWebsite = (MtplEditText) rootView.findViewById(R.id.edtHoelWebsite);
        editDuration = (MtplEditText) rootView.findViewById(R.id.edtDuration);
        editCharge = (MtplEditText) rootView.findViewById(R.id.edtCharge);
        editUploadPhoto = (MtplTextView) rootView.findViewById(R.id.edtPromoPic);
        spinnerHotelType = (Spinner) rootView.findViewById(R.id.spinnerHotel);
        textTotal = (MtplTextView) rootView.findViewById(R.id.txtTotal);
        btnCart = (MtplButton) rootView.findViewById(R.id.btnCart);
        btnPreview = (MtplButton) rootView.findViewById(R.id.btnPriview);
        autotxtState = (AutoCompleteTextView) rootView.findViewById(R.id.autotextState);
        allCityListview = (RecyclerView) rootView.findViewById(R.id.allCityList);
        selectedCityListview = (RecyclerView) rootView.findViewById(R.id.selectedCityList);

        linearLayout = (LinearLayout) rootView.findViewById(R.id.layoutTop);


        editUserName.setText(CM.getSp(thisActivity, CV.Preffirst_name, "").toString() + " " + CM.getSp(thisActivity, CV.Preflast_name, "").toString());
        editUserName.setEnabled(false);
        editDuration.setText("" + month);
        editCharge.setEnabled(false);

        btnPreview.setOnClickListener(this);
        pojoStateArrayList = new ArrayList<>();

        thisActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webCallState();

            }
        });

        //All citys
        allCityAdapter = new adptPromotionCityAll(promoAllCity);
        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        allCityListview.setLayoutManager(mStaggeredLayoutManager);
        allCityListview.setItemAnimator(new DefaultItemAnimator());
        allCityListview.setAdapter(allCityAdapter);

        //selected citys
        //All citys
        selectedCityAdapter = new adptPromortioncitySelected(promoSelectedCity);
        StaggeredGridLayoutManager mStaggeredLayoutManager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        selectedCityListview.setLayoutManager(mStaggeredLayoutManager1);
        selectedCityListview.setItemAnimator(new DefaultItemAnimator());
        selectedCityListview.setAdapter(selectedCityAdapter);

        allCityAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value, String value1) {

                int position = Integer.parseInt(value1);

                totalAmt += Integer.parseInt(promoAllCity.get(position).getPrice());
                textTotal.setText("Total " + totalAmt);
                charge = month * totalAmt;
                editCharge.setText("" + charge);

                promoSelectedCity.add(promoAllCity.get(position));
                promoAllCity.remove(position);

                Collections.sort(promoAllCity, new Comparator<pojoPromotionCitys>() {
                    public int compare(pojoPromotionCitys list1, pojoPromotionCitys list2) {
                        return list1.getLabel().compareToIgnoreCase(list2.getLabel());
                    }
                });
                Collections.sort(promoSelectedCity, new Comparator<pojoPromotionCitys>() {
                    public int compare(pojoPromotionCitys list1, pojoPromotionCitys list2) {
                        return list1.getLabel().compareToIgnoreCase(list2.getLabel());
                    }
                });
                allCityAdapter.notifyDataSetChanged();
                selectedCityAdapter.notifyDataSetChanged();

            }
        });

        selectedCityAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value, String value1) {

                int position = Integer.parseInt(value1);

                totalAmt -= Integer.parseInt(promoSelectedCity.get(position).getPrice());
                textTotal.setText("Total " + totalAmt);
                charge = month * totalAmt;
                editCharge.setText("" + charge);

                promoAllCity.add(promoSelectedCity.get(position));
                promoSelectedCity.remove(position);

                Collections.sort(promoAllCity, new Comparator<pojoPromotionCitys>() {
                    public int compare(pojoPromotionCitys list1, pojoPromotionCitys list2) {
                        return list1.getLabel().compareToIgnoreCase(list2.getLabel());
                    }
                });

                Collections.sort(promoSelectedCity, new Comparator<pojoPromotionCitys>() {
                    public int compare(pojoPromotionCitys list1, pojoPromotionCitys list2) {
                        return list1.getLabel().compareToIgnoreCase(list2.getLabel());
                    }
                });
                selectedCityAdapter.notifyDataSetChanged();
                allCityAdapter.notifyDataSetChanged();

            }
        });


        autotxtState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                stateId = pojoStateArrayList.get(i).getId();
                webCallCity(stateId);
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
        MenuItem item = menu.findItem(R.id.cartMenu);
        item.setVisible(false);
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
                        promoSelectedCity.clear();
                        promoAllCity.clear();
                        totalAmt = 0;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            pojoState pojoState = new pojoState();
                            pojoState.setId(jsonArray.getJSONObject(i).optString("id"));
                            pojoState.setCountry_id(jsonArray.getJSONObject(i).optString("country_id"));
                            pojoState.setState_name(jsonArray.getJSONObject(i).optString("state_name"));
                            pojoStateArrayList.add(pojoState);
                        }
                    }

                    AutoCompleteStateAdapter adptState1 = new AutoCompleteStateAdapter(thisActivity, R.layout.conntylayout, R.id.textViewSpinner, pojoStateArrayList);
                    autotxtState.setThreshold(3);
                    autotxtState.setAdapter(adptState1);

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

    public void webCallCity(String stateId) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getPromotionCity(v, stateId, new OnVolleyHandler() {
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

                    //Got citys
                    if (jsonObject.optString("ResponseObject") != null) {
                        JSONArray jsonArray = new JSONArray(jsonObject.optString("ResponseObject"));
                        promoAllCity.clear();
                        promoSelectedCity.clear();
                        totalAmt = 0;
                        textTotal.setText("Total " + totalAmt);
                        charge = month * totalAmt;
                        editCharge.setText("" + charge);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            pojoPromotionCitys pojocity = new pojoPromotionCitys();
                            pojocity.setLabel(jsonArray.getJSONObject(i).optString("label"));
                            pojocity.setUsercount(jsonArray.getJSONObject(i).optString("usercount"));
                            pojocity.setValue(jsonArray.getJSONObject(i).optString("value"));
                            pojocity.setPrice(jsonArray.getJSONObject(i).optString("price"));
                            pojocity.setState_id(jsonArray.getJSONObject(i).optString("state_id"));
                            pojocity.setState_name(jsonArray.getJSONObject(i).optString("state_name"));
                            pojocity.setCountry_id(jsonArray.getJSONObject(i).optString("country_id"));
                            pojocity.setCountry_name(jsonArray.getJSONObject(i).optString("country_name"));
                            promoAllCity.add(pojocity);
                        }
                        allCityAdapter.notifyDataSetChanged();

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
        String userName, hotelName, hotelType, hotelcheaproom, hotelexpensiveroom, hotelwebsite, state, city, duration, charge;

        userName = editUserName.getText().toString();
        hotelName = editHotelName.getText().toString();
        hotelType = spinnerHotelType.getSelectedItem().toString();
        hotelcheaproom = editTariffCheapestRoom.getText().toString();
        hotelexpensiveroom = editTariffExpensiveRoom.getText().toString();
        hotelwebsite = editHotelWebsite.getText().toString();
        state = autotxtState.getText().toString();
        duration = editDuration.getText().toString();
        charge = editCharge.getText().toString();
        if (!userName.matches("")) {
            if (!hotelName.matches("")) {
                if (!hotelType.matches("")) {
                    if (!hotelcheaproom.matches("")) {
                        if (!hotelexpensiveroom.matches("")) {
                            if (!hotelwebsite.matches("")) {
                                if (!state.matches("")) {
                                    if (promoSelectedCity.size() > 0) {
                                        if (!duration.matches("")) {

                                            //Check Image Selected
                                            if (PromoPic != null) {
                                                //encode Image to byteArray
                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                PromoPic.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                byte[] byteArray = stream.toByteArray();
                                                String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

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
                                                bundle.putString("hsatate", state);
                                                String citys = "";
                                                String cityids = "";
                                                String cityprices = "";
                                                for (int i = 0; i < promoSelectedCity.size(); i++) {
                                                    if (citys.equals("")) {
                                                        citys = promoSelectedCity.get(i).getLabel();
                                                        //cityids=promoSelectedCity.get(i).
                                                        cityprices = promoSelectedCity.get(i).getPrice();
                                                    } else {
                                                        citys = citys + "," + promoSelectedCity.get(i).getLabel();
                                                        cityprices = cityprices + "," + promoSelectedCity.get(i).getPrice();
                                                    }
                                                }
                                                bundle.putString("hcity", citys);
                                                bundle.putString("hcityprice", cityprices);
                                                bundle.putString("hcityid", cityids);
                                                bundle.putString("hduration", duration);
                                                bundle.putString("hcharge", charge);
                                                intent.putExtras(bundle);


                                                CM.startActivity(intent, thisActivity);
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
                                    CM.showToast("Enter State", thisActivity);
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
                                    webView.setText(CM.fromHtml(jsonObject.getString("description").toString()));
                                    webView.setMovementMethod(ScrollingMovementMethod.getInstance());
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
}
