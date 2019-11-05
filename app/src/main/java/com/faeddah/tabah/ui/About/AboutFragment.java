package com.faeddah.tabah.ui.About;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.adapter.AdapterListDev;
import com.faeddah.tabah.dummydata.DataDev;
import com.faeddah.tabah.model.Dev;

import java.util.ArrayList;


public class AboutFragment extends BaseFragment {


    public AboutFragment() {}

    public static AboutFragment newInstance(){
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = new RecyclerView(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Dev> itemDev = new ArrayList<>();
        itemDev.addAll(DataDev.getListData());
        AdapterListDev listDeveloper = new AdapterListDev(itemDev);

        //jeder
        rv.setAdapter(listDeveloper);
        return rv;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void findViews(View view) {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initListeners(View view) {

    }

}