package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.PagerAdapter;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;

import static android.content.ContentValues.TAG;

/**
 * Created by NetSupport on 01-06-2017.
 */

public class FragPaceRequest extends Fragment {

    private OnFragmentInteractionListener mListener;

    TabLayout tabLayout;
    private ViewPager viewPager;
    Activity thisActivity;
    PagerAdapter adapter;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.placeReq));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_placerequest, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.placeReq));

        Log.i(TAG, "onTabSelected: ");


        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("PACKAGE"));
        tabLayout.addTab(tabLayout.newTab().setText("HOTEL"));
        tabLayout.addTab(tabLayout.newTab().setText("TRANSPORT"));

        changeTabsFont();
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        adapter = new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setCurrentItem(0);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(true);

    }

    @Override
    public void onPause() {
        super.onPause();


    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    Typeface font = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
                    ((TextView) tabViewChild).setTypeface(font);
                }
            }
        }
    }
}
