package com.faeddah.tabah.adapter;

import android.content.Context;

import com.faeddah.tabah.Sell;
import com.faeddah.tabah.ui.Sell.AntarFragment;
import com.faeddah.tabah.ui.Sell.JemputFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterSell extends FragmentPagerAdapter {


    public AdapterSell(Context context, FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AntarFragment();
            case 1:
                return new JemputFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
