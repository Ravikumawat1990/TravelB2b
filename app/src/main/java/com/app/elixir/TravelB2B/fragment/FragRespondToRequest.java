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
import com.app.elixir.TravelB2B.model.PojoMyResponse;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.view.ViewRespondToRequestDetailView;

import java.util.ArrayList;

/**
 * Created by mtpl on 12/15/2015.
 */
public class FragRespondToRequest extends Fragment {
    Activity thisActivity;
    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    adptRespondToRequest mAdapter;

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
        mAdapter = new adptRespondToRequest(thisActivity, pojoMyResponses);
        try {
            mRecyclerView.setAdapter(mAdapter);

        } catch (Exception e) {
            e.getMessage();

        }


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
}
