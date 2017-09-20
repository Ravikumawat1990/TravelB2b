package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptPromoReports;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoPromoReport;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 26-Jul-2017.
 */

public class FragPromotionReports extends Fragment {

    private static final String TAG = "FragPromoReport";
    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    private RecyclerView mRecyclerView;
    adptPromoReports mAdapter;
    ArrayList<pojoPromoReport> pojoReports;
    FloatingActionButton myFab;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle("Promotion Report");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragpromoreport, container, false);
        ((ActionBarTitleSetter) thisActivity).setTitle("Promotion Reports");
        thisActivity = getActivity();
        setHasOptionsMenu(true);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));
        pojoReports = new ArrayList<>();

        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        myFab.setVisibility(View.GONE);


        mAdapter = new adptPromoReports(thisActivity, pojoReports);


        if (CM.isInternetAvailable(thisActivity)) {
            webPromoReport(CM.getSp(thisActivity, CV.PrefID, "").toString());
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
        }

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
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // MenuItem item = menu.findItem(R.id.cartMenu);
        // MenuItem item1 = menu.findItem(R.id.filter);
        menu.findItem(R.id.noti).setVisible(false);
        menu.findItem(R.id.sort).setVisible(false);
        menu.findItem(R.id.filter).setVisible(false);
        menu.findItem(R.id.cartMenu).setVisible(false);
        //  MenuItem item2 = menu.findItem(R.id.noti).setVisible(false);
        // item.setVisible(false);
        //item1.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                CM.showToast("Pressed", thisActivity);
                //showFilterPopup();
                return true;
        }
        return false;
    }

    public void webPromoReport(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getPromoReport(v, userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getPromoReprot(response);

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

    private void getPromoReprot(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONArray jsonArray = new JSONArray(jsonObject.optString("response_object").toString());
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("citystate").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        pojoPromoReport pojoreports = new pojoPromoReport();
                        pojoreports.setHotelname(jsonArray.getJSONObject(i).get("hotel_name").toString());
                        pojoreports.setStatus(jsonArray.getJSONObject(i).get("status").toString());
                        pojoreports.setViewer_count(jsonArray.getJSONObject(i).get("count").toString());
                        pojoreports.setDateOfPromo(jsonArray.getJSONObject(i).get("created_at").toString());
                        pojoreports.setDuration(jsonArray.getJSONObject(i).get("duration").toString());
                        // pojoreports.setCities(jsonArray.getJSONObject(i).get("cities").toString());
                        pojoreports.setCities(jsonObject1.optString(jsonArray.getJSONObject(i).get("id").toString()));
                        pojoReports.add(pojoreports);

                    }
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.invalidate();
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
