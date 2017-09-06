package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
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
    private FragmentTabHost mTabHost;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.placeReq));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    //Mandatory Constructor
    public FragPaceRequest() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.frag_placerequest, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.placeReq));
        setHasOptionsMenu(true);
        Log.i(TAG, "onTabSelected: ");

        mTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("PACKAGE"),
                TabPackage.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("HOTEL"),
                TabHotel.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentd").setIndicator("TRANSPORT"),
                TabTransport.class, null);
        setTabColor(mTabHost);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                setTabColor(mTabHost);

                for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                    // mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF0000")); // unselected
                    TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#ffffff"));
                }

                // mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#0000FF")); // selected
                TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#ffffff"));

            }
        });



       /* tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
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
        viewPager.setOffscreenPageLimit(3);


        viewPager.setCurrentItem(0);

       *//* final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 100);*//*
*/

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


    public void setTabColor(TabHost tabhost) {

        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
            tabhost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.color.appTextColor); // unselected
            TextView tv = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
            Typeface font = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
            tv.setTypeface(font);
        }
        tabhost.getTabWidget().setCurrentTab(0);
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
                .setBackgroundResource(R.color.orange); // selected
        TextView tv = (TextView) tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
                .findViewById(android.R.id.title); // selected
        tv.setTextColor(Color.parseColor("#ffffff"));
        Typeface font = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_light));
        tv.setTypeface(font);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.filter).setVisible(false);
        menu.findItem(R.id.noti).setVisible(false);
        menu.findItem(R.id.sort).setVisible(false);
    }
}
