package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptRespondToRequest;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoMyResposne;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.view.ViewRespondToRequestDetailView;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mtpl on 12/15/2015.
 */
public class FragRespondToRequest extends Fragment {
    private static final String TAG = "FragRespondToRequest";
    Activity thisActivity;
    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private adptRespondToRequest mAdapter;
    ArrayList<pojoMyResposne> pojoMyResposneArrayList;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.respondToReq));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_resposndtorequest, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.respondToReq));
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));

        pojoMyResposneArrayList = new ArrayList<>();
        mAdapter = new adptRespondToRequest(thisActivity, pojoMyResposneArrayList);


        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value) {

                if (value.equals("detail")) {
                    CM.startActivity(thisActivity, ViewRespondToRequestDetailView.class);
                } else {
                    ShowInterest();
                }
            }
        });
        webResponseToReq(CM.getSp(thisActivity, CV.PrefID, "").toString(), "2");

        return rootView;
    }


    public void ShowInterest() {

        LayoutInflater factory = LayoutInflater.from(thisActivity);
        final View textEntryView = factory.inflate(R.layout.showinterest, null);
        final EditText input1 = (EditText) textEntryView.findViewById(R.id.quote_price);
        final EditText input2 = (EditText) textEntryView.findViewById(R.id.comment);
        final AlertDialog.Builder alert = new AlertDialog.Builder(thisActivity);

        alert.setView(textEntryView)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.i("AlertDialog", "TextEntry 1 Entered " + input1.getText().toString());
                                Log.i("AlertDialog", "TextEntry 2 Entered " + input2.getText().toString());
                    /* User clicked OK so do some stuff */
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        });
        alert.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(true);

    }


    public void webResponseToReq(String userId, String userRole) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getMyResponseToReq(v, userId, userRole, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseToReq(response);

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

    private void getResponseToReq(String response) {
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

                        pojoMyResposne myResposne = new pojoMyResposne();
                        myResposne.setComment(jsonArray.getJSONObject(i).get("comment").toString());
                        JSONObject jsonObjectReq = new JSONObject(jsonArray.getJSONObject(i).get("request").toString());
                        myResposne.setCategory_id(jsonObjectReq.optString("category_id").toString());
                        myResposne.setReference_id(jsonObjectReq.optString("reference_id").toString());
                        myResposne.setTotal_budget(jsonObjectReq.optString("total_budget").toString());
                        myResposne.setChildren(jsonObjectReq.optString("children").toString());
                        myResposne.setAdult(jsonObjectReq.optString("adult").toString());
                        myResposne.setRoom1(jsonObjectReq.optString("room1").toString());
                        myResposne.setRoom2(jsonObjectReq.optString("room2").toString());
                        myResposne.setRoom3(jsonObjectReq.optString("room3").toString());
                        myResposne.setChild_with_bed(jsonObjectReq.optString("child_with_bed").toString());
                        myResposne.setChild_without_bed(jsonObjectReq.optString("child_without_bed").toString());
                        myResposne.setHotel_rating(jsonObjectReq.optString("hotel_rating").toString());
                        myResposne.setHotel_category(jsonObjectReq.optString("hotel_category").toString());
                        myResposne.setMeal_plan(jsonObjectReq.optString("meal_plan").toString());
                        myResposne.setDestination_city(jsonObjectReq.optString("destination_city").toString());
                        myResposne.setCheck_in(jsonObjectReq.optString("check_in").toString());
                        myResposne.setCheck_out(jsonObjectReq.optString("check_out").toString());
                        myResposne.setTransport_requirement(jsonObjectReq.optString("transport_requirement").toString());
                        myResposne.setPickup_city(jsonObjectReq.optString("pickup_city").toString());
                        myResposne.setPickup_state(jsonObjectReq.optString("pickup_state").toString());
                        myResposne.setPickup_country(jsonObjectReq.optString("pickup_country").toString());
                        myResposne.setPickup_locality(jsonObjectReq.optString("pickup_locality").toString());
                        myResposne.setCity_id(jsonObjectReq.optString("city_id").toString());
                        myResposne.setState_id(jsonObjectReq.optString("state_id").toString());

                        myResposne.setFinal_city(jsonObjectReq.optString("final_city").toString());
                        myResposne.setFinal_state(jsonObjectReq.optString("final_state").toString());
                        myResposne.setFinal_country(jsonObjectReq.optString("final_country").toString());
                        myResposne.setUserComment(jsonObjectReq.optString("comment").toString());
                        myResposne.setStart_date(jsonObjectReq.optString("start_date").toString());
                        myResposne.setEnd_date(jsonObjectReq.optString("end_date").toString());
                        JSONObject jsonObjectUser = new JSONObject(jsonObjectReq.get("user").toString());
                        myResposne.setFirst_name(jsonObjectUser.optString("first_name").toString());
                        myResposne.setLast_name(jsonObjectUser.optString("last_name").toString());
                        myResposne.setMobile_number(jsonObjectUser.optString("mobile_number").toString());
                        myResposne.setP_contact(jsonObjectUser.optString("p_contact").toString());
                        pojoMyResposneArrayList.add(myResposne);
                    }

                    pojoMyResposneArrayList.size();
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
}
