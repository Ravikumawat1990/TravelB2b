package com.app.elixir.TravelB2B.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.elixir.TravelB2B.fragment.TabHotel;
import com.app.elixir.TravelB2B.fragment.TabPackage;
import com.app.elixir.TravelB2B.fragment.TabTransport;


/**
 * Created by NetSupport on 02-06-2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TabPackage();
                break;
            case 1:
                fragment = new TabHotel();
                break;
            case 2:
                fragment = new TabTransport();
                break;
            default:
                fragment = new TabPackage();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
