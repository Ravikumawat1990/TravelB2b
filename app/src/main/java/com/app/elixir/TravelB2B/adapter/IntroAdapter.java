package com.app.elixir.TravelB2B.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.elixir.TravelB2B.fragment.IntroFragment;


public class IntroAdapter extends FragmentPagerAdapter {

    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position);
            case 1:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position);
            case 2:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position);
            case 3:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position);
            case 4:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position);
            case 5:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position);
            case 6:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position);

            default:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position);
        }
    }

    @Override
    public int getCount() {

        return 7;
    }

}