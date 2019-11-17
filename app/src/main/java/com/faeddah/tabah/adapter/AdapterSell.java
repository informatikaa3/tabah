package com.faeddah.tabah.adapter;

import com.faeddah.tabah.ui.Sell.AntarFragment;
import com.faeddah.tabah.ui.Sell.JemputFragmentFeed;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterSell extends FragmentPagerAdapter {

    private String[] tabTitle = new String[]{"Antar","Jemput"};

    int mNumOfTabs;
    public AdapterSell(FragmentManager fm, int NumOfTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = NumOfTabs;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AntarFragment();
            case 1:
                return new JemputFragmentFeed();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }
}
