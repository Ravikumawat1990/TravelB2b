package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptMyRequest;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoMyRequest;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.view.ViewCheckResponse;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tokenautocomplete.TokenCompleteTextView.TAG;

/**
 * Created by NetSupport on 01-06-2017.
 */

public class FragMyRequest extends Fragment {

    private OnFragmentInteractionListener mListener;
    Activity thisActivity;

    adptMyRequest mAdapter;

    private RecyclerView mRecyclerView;

    ArrayList<pojoMyRequest> pojoMyResposneArrayList;
    FloatingActionButton myFab;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.MyReq));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_myrequest, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.MyReq));
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));

        pojoMyResposneArrayList = new ArrayList<>();
        mAdapter = new adptMyRequest(thisActivity, pojoMyResposneArrayList);

        webMyRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString());
        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value, String value1) {

                if (value.equals("remove")) {
                    showPopup(thisActivity);
                } else {
                    Intent intent = new Intent(thisActivity, ViewCheckResponse.class);
                    intent.putExtra("refId", value1);
                    CM.startActivity(intent, thisActivity);
                }
            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                return true;
        }
        return false;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.filter);
    }


    public void showPopup(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure want to remove this request?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logo1).show();
    }


    public void webMyRequest(String userId, String userRole) {
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
                    getMyRequest(response);

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

    private void getMyRequest(String response) {
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

                        pojoMyRequest myResposne = new pojoMyRequest();
                        myResposne.setCategory_id(jsonArray.getJSONObject(i).get("category_id").toString());
                        myResposne.setRequest_id(jsonArray.getJSONObject(i).get("id").toString());
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
