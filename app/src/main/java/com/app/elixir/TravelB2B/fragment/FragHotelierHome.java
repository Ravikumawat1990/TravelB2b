package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptAdvt;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.model.pojoAdvert;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by NetSupport on 05-06-2017.
 */

public class FragHotelierHome extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragHotelierHome";
    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    private RecyclerView mRecyclerView;
    adptAdvt mAdapter;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    MtplButton btnTellMore;
    ArrayList<pojoAdvert> pojoAdvertArrayList;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle("Promote your hotel");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hotelierhome, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle("Promote your hotel");

        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {

        btnTellMore = (MtplButton) rootView.findViewById(R.id.btntellmemore);
        btnTellMore.setOnClickListener(this);

        if (CM.isInternetAvailable(thisActivity)) {

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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btntellmemore:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragPromoteHotel fragFoodItem2 = new FragPromoteHotel();
                transaction.setCustomAnimations(0, R.anim.push_in_from_top);
                transaction.add(R.id.container, fragFoodItem2).addToBackStack("FragHotelierHome");
                transaction.commit();
                break;
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
