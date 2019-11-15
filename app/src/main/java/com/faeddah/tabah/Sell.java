package com.faeddah.tabah;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toolbar;

import com.faeddah.tabah.adapter.AdapterSell;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class Sell extends BaseActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterSell adapterSell;
    TabItem tabAntar;
    TabItem tabJemput;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_sell);
//        initViews();
//
//
//
////        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
////            @Override
////            public void onTabSelected(TabLayout.Tab tab) {
////                viewPager.setCurrentItem(tab.getPosition());
////                if (tab.getPosition() == 1) {
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                        getWindow().setStatusBarColor(ContextCompat.getColor(Sell.this,
////                                R.color.colorAccent));
////                    }
////                } else if (tab.getPosition() == 2) {
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                        getWindow().setStatusBarColor(ContextCompat.getColor(Sell.this,
////                                android.R.color.darker_gray));
////                    }
////                }
////            }
////
////            @Override
////            public void onTabUnselected(TabLayout.Tab tab) {
////
////            }
////
////            @Override
////            public void onTabReselected(TabLayout.Tab tab) {
////
////            }
////        });
//
//    }

    @Override
    public void findViews() {

    }

    @Override
    public void initViews() {
        AdapterSell adapterSell = new AdapterSell(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapterSell);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initListeners() {

    }
}
