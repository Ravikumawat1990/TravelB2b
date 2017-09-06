package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
}
