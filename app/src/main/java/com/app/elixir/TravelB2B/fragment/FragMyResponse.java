package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptMyResponse;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.interfaceimpl.OnItemClickListener;
import com.app.elixir.TravelB2B.model.PojoMyResponse;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.view.ViewResponseDetailView;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by NetSupport on 01-06-2017.
 */

public class FragMyResponse extends Fragment {

    private OnFragmentInteractionListener mListener;
    private AHBottomNavigation bottomNavigation;
    private RecyclerView mRecyclerView;
    private Activity thisActivity;
    private adptMyResponse mAdapter;


    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.MyResponse));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_myresponse, container, false);
        Log.i(TAG, "onTabSelected: ");
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.MyResponse));
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));
        ArrayList<PojoMyResponse> pojoMyResponses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PojoMyResponse pojoMyResponse = new PojoMyResponse();
            pojoMyResponse.setRequestType("Package");
            pojoMyResponse.setRefId("123");
            pojoMyResponse.setStartDate("30/05/2017");
            pojoMyResponse.setEndDate("31/05/2017");
            pojoMyResponse.setTotBudget("2000/-");
            pojoMyResponse.setAdult("1");
            pojoMyResponses.add(pojoMyResponse);


        }
        mAdapter = new adptMyResponse(thisActivity, pojoMyResponses);
        try {
            mRecyclerView.setAdapter(mAdapter);

        } catch (Exception e) {
            e.getMessage();

        }


        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String value) {

                CM.startActivity(thisActivity, ViewResponseDetailView.class);

            }
        });

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {


    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(true);
    }


}
