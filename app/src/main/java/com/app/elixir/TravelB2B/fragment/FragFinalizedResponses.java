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
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptFinalizeResponse;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnAdapterItemClickListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoFinalizeResposne;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.view.ViewAgentProfile;
import com.app.elixir.TravelB2B.view.ViewCommonDeatilView;
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
    FloatingActionButton myFab;
    MtplTextView userName, phoneNumber, email, discription;

    //Filter Variable
    RelativeLayout edtStartDate;
    RelativeLayout edtEndDate;
    RelativeLayout priceRoot;
    //  EditText edtRefId;
    EditText edtPrice;
    RelativeLayout spinnerRefType;
    Spinner spinnerBudget, spinnerPriceQuot;

    CharSequence[] values = {"Total Budget (High To Low)", "Total Budget (Low To High)", "Quotation Price (Low To High)", "Quotation Price (High To Low)", "Agent Name (A To Z)", "Agent Name (Z To A)"};
    AlertDialog levelDialog;
    Boolean wantToCloseDialog = false;


    //Filter Variable
    // RelativeLayout edtStartDate;
    //  RelativeLayout edtEndDate;
    // RelativeLayout priceRoot;
    // EditText edtPrice;
    // RelativeLayout spinnerRefType;
    // Spinner spinnerBudget, spinnerPriceQuot;

    //new filter fields
    AutoCompleteTextView spinnerPCity, spinnerDCity, spinnerChatWid;
    RelativeLayout edtRefId;
    RelativeLayout edtMembers;
    CheckBox checkboxFollow, checkboxShareDetail;
    private String sortItemPos = "";


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


        myFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showFilter();
            }
        });
        myFab.setVisibility(View.GONE);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    //  myFab.hide();
                } else {
                    // myFab.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });
        myFab.hide();
        mAdapter = new adptFinalizeResponse(thisActivity, finalizeResposneArrayList);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new OnAdapterItemClickListener() {
            @Override
            public void onItemClick(String value, String value1, String value2) {

                if (value.equals("detail")) {

                    Intent intent = new Intent(thisActivity, ViewCommonDeatilView.class);
                    intent.putExtra("refId", value1);
                    intent.putExtra("reqtype", value2);
                    intent.putExtra("title", getString(R.string.finalized_response));
                    CM.startActivity(intent, thisActivity);

                } else {

                    Intent intent = new Intent(thisActivity, ViewAgentProfile.class);
                    intent.putExtra("userId", value1);
                    CM.startActivity(intent, thisActivity);

                }
            }
        });

        if (CM.isInternetAvailable(thisActivity)) {
            webFinalizeResponse(CM.getSp(thisActivity, CV.PrefID, "").toString(), "", "", "", "", "");
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
        menu.findItem(R.id.noti).setVisible(false);
        menu.findItem(R.id.cartMenu).setVisible(false);
        menu.findItem(R.id.sort).setVisible(true);
        menu.findItem(R.id.filter).setVisible(true);
    }


    /*public void showPopup(Context context) {
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
        }).setIcon(R.drawable.logonewnew).show();
    }*/

   /* @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.cartMenu);
        item.setVisible(false);
    }*/

    public void webFinalizeResponse(String userId, String reqId, String budget, String quotPrice, String name, String sort) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getFinalizeResponse(v, userId, reqId, budget, quotPrice, name, sort, new OnVolleyHandler() {
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
                    finalizeResposneArrayList.clear();
                    if (jsonArray.length() > 0) {
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
                            pojoMyResponse.setCategory_id(jsonObject1.optString("category_id"));

                            JSONObject jsonObject2 = new JSONObject(jsonObject1.optString("user").toString());
                            pojoMyResponse.setId(jsonObject2.optString("id"));
                            pojoMyResponse.setFirst_name(jsonObject2.optString("first_name"));
                            pojoMyResponse.setLast_name(jsonObject2.optString("last_name"));


                            finalizeResposneArrayList.add(pojoMyResponse);

                        }
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.invalidate();
                    } else {
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.invalidate();
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

    public void showFilter() {
        LayoutInflater inflater = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.filter, (ViewGroup) thisActivity.findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity)
                .setView(layout);
        builder.setTitle("Filter By:");
        SearchView searchView = (SearchView) layout.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by name, email, mobile");
        searchView.setVisibility(View.VISIBLE);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) searchView.findViewById(id);
        Typeface myCustomFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
        searchText.setTypeface(myCustomFont);
        builder.setIcon(R.drawable.logonewnew);

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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.cartMenu);
        MenuItem item1 = menu.findItem(R.id.filter);
        menu.findItem(R.id.noti).setVisible(false);
        item.setVisible(false);
        item1.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                showFilterPopup();
                break;
            case R.id.sort:
                showDialogSort();
                break;
        }
        return false;
    }

    public void showFilterPopup() {
        LayoutInflater inflater1 = (LayoutInflater) thisActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout1 = inflater1.inflate(R.layout.popup_filter, (ViewGroup) thisActivity.findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity).setView(layout1);
        builder.setTitle("Filter By:");

        final SearchView searchView = (SearchView) layout1.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by Agent Name");
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final TextView searchText = (TextView) searchView.findViewById(id);
        Typeface myCustomFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
        searchText.setTypeface(myCustomFont);
        builder.setIcon(R.drawable.logonewnew);

        spinnerBudget = (Spinner) layout1.findViewById(R.id.spinnerbudget);
        spinnerPriceQuot = (Spinner) layout1.findViewById(R.id.spinnerPriceQuot);
        edtStartDate = (RelativeLayout) layout1.findViewById(R.id.startdateroot);
        edtStartDate.setVisibility(View.GONE);
        edtEndDate = (RelativeLayout) layout1.findViewById(R.id.enddateroot);
        edtEndDate.setVisibility(View.GONE);
        spinnerRefType = (RelativeLayout) layout1.findViewById(R.id.typeroot);
        spinnerRefType.setVisibility(View.GONE);

        //new

        spinnerPCity = (AutoCompleteTextView) layout1.findViewById(R.id.pcityspinner);
        spinnerDCity = (AutoCompleteTextView) layout1.findViewById(R.id.dcityspinner);
        edtMembers = (RelativeLayout) layout1.findViewById(R.id.memberroot);
        checkboxFollow = (CheckBox) layout1.findViewById(R.id.followimgcheckbox);
        checkboxShareDetail = (CheckBox) layout1.findViewById(R.id.sdetailcheckbox);
        spinnerChatWid = (AutoCompleteTextView) layout1.findViewById(R.id.chatwidspinner);
        edtRefId = (RelativeLayout) layout1.findViewById(R.id.refroot);

        //hide show new fields
        searchView.setVisibility(View.VISIBLE);
        spinnerPCity.setVisibility(View.GONE);
        spinnerDCity.setVisibility(View.GONE);
        edtMembers.setVisibility(View.GONE);
        spinnerChatWid.setVisibility(View.GONE);
        checkboxFollow.setVisibility(View.GONE);
        checkboxShareDetail.setVisibility(View.GONE);
        edtRefId.setVisibility(View.GONE);


        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String quotPrice, budgetv, name;


                name = searchView.getQuery().toString();
                quotPrice = spinnerPriceQuot.getSelectedItem().toString();
                budgetv = spinnerBudget.getSelectedItem().toString();


                if (budgetv.equals("Select Budget")) {
                    budgetv = "";
                }
                if (quotPrice.equals("Select Quoted Price")) {
                    quotPrice = "";
                }

                if (CM.isInternetAvailable(thisActivity)) {
                    webFinalizeResponse(CM.getSp(thisActivity, CV.PrefID, "").toString(), "", budgetv, quotPrice, name, "");
                } else {
                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
                }
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

    public void showDialogSort() {


        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity,
                R.style.MyDialogTheme);
        builder.setTitle("Sorting");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch (item) {
                    case 0:
                        sortItemPos = "0";
                        wantToCloseDialog = true;
                        break;
                    case 1:
                        sortItemPos = "1";
                        wantToCloseDialog = true;
                        break;
                    case 2:
                        sortItemPos = "2";
                        wantToCloseDialog = true;

                        break;
                    case 3:
                        sortItemPos = "3";
                        wantToCloseDialog = true;
                        break;
                    case 4:
                        sortItemPos = "4";
                        wantToCloseDialog = true;
                        break;
                    case 5:
                        sortItemPos = "5";
                        wantToCloseDialog = true;
                        break;
                    default:
                        wantToCloseDialog = false;
                        break;


                }
                //levelDialog.dismiss();

            }
        });


        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        levelDialog = builder.create();
        levelDialog.show();
        levelDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Do stuff, possibly set wantToCloseDialog to true then...
                if (wantToCloseDialog)
                    levelDialog.dismiss();
                wantToCloseDialog = false;
                webFinalizeResponse(CM.getSp(thisActivity, CV.PrefID, "").toString(), "", "", "", "", setSort(sortItemPos));

                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });


    }

   /* totalbudgetlh
            totalbudgethl
    quotationlh
            quotationhl
    agentaz
            agentza*/
    // CharSequence[] values = {"Total Budget (High To Low)", "Total Budget (Low To High)", "Quotation Price (Low To High)", "Quotation Price (High To Low)", "Agent Name (A To Z)", "Agent Name (Z To A)"};

    public String setSort(String pos) {
        String sortItem = "";
        switch (pos) {
            case "0":
                sortItem = "totalbudgetlh";
                break;
            case "1":
                sortItem = "totalbudgethl";
                break;
            case "2":
                sortItem = "quotationlh";
                break;
            case "3":
                sortItem = "quotationhl";
                break;
            case "4":
                sortItem = "agentaz";
            case "5":
                sortItem = "agentza";
                break;
        }

        return sortItem;
    }

}
