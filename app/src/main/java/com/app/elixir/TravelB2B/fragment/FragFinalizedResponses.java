package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.app.elixir.TravelB2B.adapter.adptFinalizeResponse;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoFinalizeResposne;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.view.ViewAgentProfile;
import com.app.elixir.TravelB2B.view.ViewFinalizedResponseDetailView;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by NetSupport on 05-06-2017.
 */

public class FragFinalizedResponses extends Fragment {

    private static final String TAG = "FragFinalizedResponses";
    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    private RecyclerView mRecyclerView;
    adptFinalizeResponse mAdapter;
    ArrayList<pojoFinalizeResposne> finalizeResposneArrayList;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.finalized_response));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragfinlizeresponse, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.finalized_response));
        setHasOptionsMenu(true);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));
        finalizeResposneArrayList = new ArrayList<>();

        mAdapter = new adptFinalizeResponse(thisActivity, finalizeResposneArrayList);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value, String value1) {

                if (value.equals("detail")) {

                    Intent intent = new Intent(thisActivity, ViewFinalizedResponseDetailView.class);
                    intent.putExtra("refId", value1);
                    CM.startActivity(intent, thisActivity);

                } else {
                    CM.startActivity(thisActivity, ViewAgentProfile.class);
                }
            }
        });
        webFinalizeResponse(CM.getSp(thisActivity, CV.PrefID, "").toString());

    }


    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.myresponsedetail, menu);

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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.cartMenu);
        item.setVisible(false);
    }

    public void webFinalizeResponse(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getFinalizeResponse(v, userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getFinalizeResponse(response);

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

    private void getFinalizeResponse(String response) {
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
                    for (int i = 0; i < jsonArray.length(); i++) {

                        pojoFinalizeResposne pojoMyResponse = new pojoFinalizeResposne();
                        pojoMyResponse.setQuotation_price(jsonArray.getJSONObject(i).optString("quotation_price"));
                        pojoMyResponse.setRequest_id(jsonArray.getJSONObject(i).optString("request_id"));
                        pojoMyResponse.setComment(jsonArray.getJSONObject(i).optString("comment"));
                        JSONObject jsonObject1 = new JSONObject(jsonArray.getJSONObject(i).optString("request").toString());


                        pojoMyResponse.setAdult(jsonObject1.optString("adult"));
                        pojoMyResponse.setChildren(jsonObject1.optString("children"));
                        pojoMyResponse.setReference_id(jsonObject1.optString("reference_id"));
                        pojoMyResponse.setTotal_budget(jsonObject1.optString("total_budget"));


                        JSONObject jsonObject2 = new JSONObject(jsonArray.getJSONObject(i).optString("user").toString());
                        pojoMyResponse.setFirst_name(jsonObject2.optString("first_name"));
                        pojoMyResponse.setLast_name(jsonObject2.optString("last_name"));

                        finalizeResposneArrayList.add(pojoMyResponse);

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
