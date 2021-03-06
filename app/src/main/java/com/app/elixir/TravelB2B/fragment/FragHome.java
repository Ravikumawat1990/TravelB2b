package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptAdvt;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnApiDataChange;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.model.PojoMyResponse;
import com.app.elixir.TravelB2B.model.pojoAdvert;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoCity;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.view.ViewCustomWebView;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by NetSupport on 05-06-2017.
 */

public class FragHome extends Fragment {

    private static final String TAG = "FragHome";
    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    private RecyclerView mRecyclerView;
    adptAdvt mAdapter;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    ArrayList<pojoAdvert> pojoAdvertArrayList;
    private OnApiDataChange listener;
    ArrayList<pojoCity> pojoCities;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle("Home");
            this.listener = (OnApiDataChange) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        thisActivity = getActivity();
        initView(rootView);


        return rootView;
    }

    private void initView(View rootView) {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        pojoAdvertArrayList = new ArrayList<>();
        mAdapter = new adptAdvt(thisActivity, pojoAdvertArrayList);

        listener.onItemClick(true);
        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String item, String item1) {
                /*try {
                    if (!item.startsWith("http://") && !item.startsWith("https://"))
                        item = "http://" + item;

                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(thisActivity, "No application can handle this request."
                            + "Please install a webbrowser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }*/

                if (CM.isInternetAvailable(thisActivity)) {

                    if (!item.startsWith("http://") && !item.startsWith("https://"))
                        item = "http://" + item;
                    Intent myIntent = new Intent(thisActivity, ViewCustomWebView.class);
                    myIntent.putExtra("url", item);
                    myIntent.putExtra("pid", item1);
                    startActivity(myIntent);

                } else {
                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
                }
            }
        });

        if (CM.isInternetAvailable(thisActivity)) {
            pojoCities = new ArrayList<>();
            webCallCity();
            webTestimonial(CM.getSp(thisActivity, CV.PrefID, "").toString());
            webcallHotelCate();
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //  getActivity().getMenuInflater().inflate(R.menu.myresponsedetail, menu);
        menu.findItem(R.id.filter).setVisible(false);
        menu.findItem(R.id.noti).setVisible(false);
        menu.findItem(R.id.sort).setVisible(false);
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

    public void showPopup(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage(R.string.r_u_sure_u_to_exit)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logo3).show();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.cartMenu);
        item.setVisible(false);
    }


    public void webTestimonial(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getTestimonial(v, userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForTestimonial(response);

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

    private void getResponseForTestimonial(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    // JSONArray jsonArray = new JSONArray(jsonObject.optString("response_object").toString());
                    JSONArray jsonArray1 = new JSONArray(jsonObject.optString("advertisement").toString());
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        pojoAdvert pojoAdverts = new pojoAdvert();
                        pojoAdverts.setId(jsonArray1.getJSONObject(i).getString("id"));
                        pojoAdverts.setUser_id(jsonArray1.getJSONObject(i).getString("user_id"));
                        pojoAdverts.setHotel_name(jsonArray1.getJSONObject(i).getString("hotel_name"));
                        pojoAdverts.setHotel_type(jsonArray1.getJSONObject(i).getString("hotel_type"));
                        pojoAdverts.setCheap_tariff(jsonArray1.getJSONObject(i).getString("cheap_tariff"));
                        pojoAdverts.setExpensive_tariff(jsonArray1.getJSONObject(i).getString("expensive_tariff"));
                        pojoAdverts.setWebsite(jsonArray1.getJSONObject(i).getString("website"));
                        pojoAdverts.setCities(jsonArray1.getJSONObject(i).getString("cities"));
                        pojoAdverts.setCharges(jsonArray1.getJSONObject(i).getString("charges"));
                        pojoAdverts.setDuration(jsonArray1.getJSONObject(i).getString("duration"));
                        pojoAdverts.setStatus(jsonArray1.getJSONObject(i).getString("status"));
                        pojoAdverts.setHotel_pic(jsonArray1.getJSONObject(i).getString("hotel_pic"));
                        pojoAdverts.setPayment_status(jsonArray1.getJSONObject(i).getString("payment_status"));
                        pojoAdverts.setCitycharge(jsonArray1.getJSONObject(i).getString("citycharge"));
                        pojoAdverts.setExpiry_date(jsonArray1.getJSONObject(i).optString("expiry_date"));
                        pojoAdverts.setCount(jsonArray1.getJSONObject(i).getString("count"));
                        pojoAdverts.setHotel_location(jsonArray1.getJSONObject(i).getString("hotel_location"));
                        pojoAdvertArrayList.add(pojoAdverts);
                    }
                    pojoAdvertArrayList.size();
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

                        JSONObject object = new JSONObject(jsonObject.optString("ResponseObject"));
                        JSONObject object1 = new JSONObject(object.optString("citystatefi"));
                        int count = jsonObject.optInt("TotalRecord");

                        for (int i = 1; i <= count; i++) {
                            pojoCity country = new pojoCity();
                            JSONObject jsonArray1 = new JSONObject(object1.optString("" + i));
                            country.setId(jsonArray1.optString("cityid"));
                            country.setName(jsonArray1.optString("name"));
                            country.setState_id(jsonArray1.optString("stateid"));
                            pojoCities.add(country);
                        }
                    }

                    Gson gson = new Gson();
                    String json = gson.toJson(pojoCities);
                    CM.setSp(thisActivity, "cities", "");
                    CM.setSp(thisActivity, "cities", json);

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

    public void webcallHotelCate() {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getHotelCate(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForHotelCate(response);

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

    private void getResponseForHotelCate(String response) {
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

                        //jsonObject.optString("ResponseObject")
                        String json = jsonObject.optString("response_object").toString();
                        CM.setSp(thisActivity, "hotelCate", "");
                        CM.setSp(thisActivity, "hotelCate", json);

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


}
