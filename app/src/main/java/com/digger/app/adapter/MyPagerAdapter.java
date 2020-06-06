package com.digger.app.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.digger.app.ui.dashboard.CryptoFragment;
import com.digger.app.ui.dashboard.RawMaterialsFragment;
import com.digger.app.ui.dashboard.StockMarketIndicesFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override

    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch (i) {
            case 0:
                fragment = new CryptoFragment();
                break;
            case 1:
                fragment = new StockMarketIndicesFragment();

                break;
            case 2:
                fragment = new RawMaterialsFragment();

                break;
        }


        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Crypto";
            case 1:
                return "Indexes";
            case 2:
                return "Raw Material";
        }

        return "";
    }

}
