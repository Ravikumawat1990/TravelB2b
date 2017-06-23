package com.app.elixir.TravelB2B.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.elixir.TravelB2B.fragment.TabHotel;
import com.app.elixir.TravelB2B.fragment.TabPackage;
import com.app.elixir.TravelB2B.fragment.TabTransport;


/**
 * Created by NetSupport on 02-06-2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabPackage tab1 = new TabPackage();
                return tab1;
            case 1:
                TabHotel tab2 = new TabHotel();
                return tab2;
            case 2:
                TabTransport tab3 = new TabTransport();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
