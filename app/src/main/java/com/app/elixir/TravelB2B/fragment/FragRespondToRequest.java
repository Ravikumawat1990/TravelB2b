package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

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
    FloatingActionButton myFab;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_resposndtorequest, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.respondToReq));
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));

        pojoMyResposneArrayList = new ArrayList<>();
        mAdapter = new adptRespondToRequest(thisActivity, pojoMyResposneArrayList);


        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value, String value1) {

                if (value1.equals("detail")) {

                    Intent intent = new Intent(thisActivity, ViewRespondToRequestDetailView.class);
                    intent.putExtra("refId", value);
                    CM.startActivity(intent, thisActivity);
                } else {
                    ShowInterest();
                }
            }
        });
        webResponseToReq(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString());


        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showFilter();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    myFab.hide();
                } else {
                    myFab.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });

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
                        myResposne.setUserId(jsonArray.getJSONObject(i).get("id").toString());
                        myResposne.setCategory_id(jsonArray.getJSONObject(i).get("category_id").toString());
                        myResposne.setReference_id(jsonArray.getJSONObject(i).get("reference_id").toString());
                        myResposne.setTotal_budget(jsonArray.getJSONObject(i).get("total_budget").toString());
                        myResposne.setChildren(jsonArray.getJSONObject(i).get("children").toString());
                        myResposne.setAdult(jsonArray.getJSONObject(i).get("adult").toString());
                        myResposne.setRoom1(jsonArray.getJSONObject(i).get("room1").toString());
                        myResposne.setRoom2(jsonArray.getJSONObject(i).get("room2").toString());
                        myResposne.setRoom3(jsonArray.getJSONObject(i).get("room3").toString());
                        myResposne.setChild_with_bed(jsonArray.getJSONObject(i).get("child_with_bed").toString());
                        myResposne.setChild_without_bed(jsonArray.getJSONObject(i).get("child_without_bed").toString());
                        myResposne.setHotel_rating(jsonArray.getJSONObject(i).get("hotel_rating").toString());
                        myResposne.setHotel_category(jsonArray.getJSONObject(i).get("hotel_category").toString());
                        myResposne.setMeal_plan(jsonArray.getJSONObject(i).get("meal_plan").toString());
                        myResposne.setDestination_city(jsonArray.getJSONObject(i).get("destination_city").toString());
                        myResposne.setCheck_in(jsonArray.getJSONObject(i).get("check_in").toString());
                        myResposne.setCheck_out(jsonArray.getJSONObject(i).get("check_out").toString());
                        myResposne.setTransport_requirement(jsonArray.getJSONObject(i).get("transport_requirement").toString());
                        myResposne.setPickup_city(jsonArray.getJSONObject(i).get("pickup_city").toString());
                        myResposne.setPickup_state(jsonArray.getJSONObject(i).get("pickup_state").toString());
                        myResposne.setPickup_country(jsonArray.getJSONObject(i).get("pickup_country").toString());
                        myResposne.setPickup_locality(jsonArray.getJSONObject(i).get("pickup_locality").toString());
                        myResposne.setCity_id(jsonArray.getJSONObject(i).get("city_id").toString());
                        myResposne.setState_id(jsonArray.getJSONObject(i).get("state_id").toString());
                        myResposne.setFinal_city(jsonArray.getJSONObject(i).get("final_city").toString());
                        myResposne.setFinal_state(jsonArray.getJSONObject(i).get("final_state").toString());
                        myResposne.setFinal_country(jsonArray.getJSONObject(i).get("final_country").toString());
                        myResposne.setUserComment(jsonArray.getJSONObject(i).get("comment").toString());
                        myResposne.setStart_date(jsonArray.getJSONObject(i).get("start_date").toString());
                        myResposne.setEnd_date(jsonArray.getJSONObject(i).get("end_date").toString());
                        JSONObject jsonObjectUser = new JSONObject(jsonArray.getJSONObject(i).get("user").toString());
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

    public void showFilter() {
        LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.filter, (ViewGroup) thisActivity.findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity)
                .setView(layout);
        builder.setTitle("Filter By:");
        SearchView searchView = (SearchView) layout.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by name, email, mobile");
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) searchView.findViewById(id);
        Typeface myCustomFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
        searchText.setTypeface(myCustomFont);
        builder.setIcon(R.drawable.logo3);

        Spinner spinner = (Spinner) layout.findViewById(R.id.spinner);
        final String[] cat = getResources().getStringArray(R.array.catArray);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(thisActivity, R.layout.support_simple_spinner_dropdown_item, cat) {

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
        spinner.setAdapter(langAdapter);

        Spinner spinner1 = (Spinner) layout.findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> langAdapter1 = new ArrayAdapter<CharSequence>(thisActivity, R.layout.support_simple_spinner_dropdown_item, cat) {

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
        spinner1.setAdapter(langAdapter1);


        Spinner spinner2 = (Spinner) layout.findViewById(R.id.spinner3);

        ArrayAdapter<CharSequence> langAdapter2 = new ArrayAdapter<CharSequence>(thisActivity, R.layout.support_simple_spinner_dropdown_item, cat) {

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
        spinner2.setAdapter(langAdapter2);


        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
