package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptfinalizedRequest;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoFinalizeReq;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.view.ViewChat;
import com.app.elixir.TravelB2B.view.ViewFinalizedRequestDetailView;
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
 * Created by NetSupport on 05-06-2017.
 */

public class FragFinalizedRequest extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    private RecyclerView mRecyclerView;
    adptfinalizedRequest mAdapter;
    private PopupWindow mPopupWindow;
    CoordinatorLayout layoutrootView;
    ArrayList<pojoFinalizeReq> pojoFinalizeReqArrayList;
    FloatingActionButton myFab;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.finalized_requests));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragfinlizerequest, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.finalized_requests));
        setHasOptionsMenu(true);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        layoutrootView = (CoordinatorLayout) rootView.findViewById(R.id.root);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));
        pojoFinalizeReqArrayList = new ArrayList<>();

        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showFilter();
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

        mAdapter = new adptfinalizedRequest(thisActivity, pojoFinalizeReqArrayList);


        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value, String value1) {

                if (value.equals("detail")) {


                    Intent intent = new Intent(thisActivity, ViewFinalizedRequestDetailView.class);
                    intent.putExtra("refId", value1);
                    CM.startActivity(intent, thisActivity);


                } else if (value.equals("chat")) {

                    Intent intent = new Intent(thisActivity, ViewChat.class);
                    intent.putExtra("refId", value1);
                    CM.startActivity(intent, thisActivity);


                } else {
                    showPopupForTestimonial();

                }


            }
        });

        if (CM.isInternetAvailable(thisActivity)) {
            webFinalizeRequest(CM.getSp(thisActivity, CV.PrefID, "").toString(), CM.getSp(thisActivity, CV.PrefRole_Id, "").toString());
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.cartMenu);
        item.setVisible(false);
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


    public void showPopupForTestimonial() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(thisActivity);
        // dialog.setTitle(getString(R.string.app_name));
        //dialog.setIcon(R.drawable.logo3);
        dialog.setCancelable(false);
        dialog.setView(R.layout.custom_layout);
        dialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    public void webFinalizeRequest(String userId, String userRole) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getFinalizeReq(v, userId, userRole, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getFinalizeRequest(response);

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

    private void getFinalizeRequest(String response) {
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

                        pojoFinalizeReq pojoMyResponse = new pojoFinalizeReq();
                        pojoMyResponse.setAdult(jsonArray.getJSONObject(i).optString("adult"));
                        pojoMyResponse.setCategory_id(jsonArray.getJSONObject(i).optString("category_id"));
                        pojoMyResponse.setChildren(jsonArray.getJSONObject(i).optString("children"));
                        pojoMyResponse.setReference_id(jsonArray.getJSONObject(i).optString("reference_id"));
                        pojoMyResponse.setTotal_budget(jsonArray.getJSONObject(i).optString("total_budget"));
                        pojoMyResponse.setRequest_id(jsonArray.getJSONObject(i).optString("id"));
                        pojoFinalizeReqArrayList.add(pojoMyResponse);

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
