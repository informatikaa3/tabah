package com.faeddah.tabah.ui.About;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class AboutFragment extends Fragment{

    public AboutFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = new RecyclerView(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Dev> itemDev = new ArrayList<>();
        itemDev.addAll(dummyData.getListData());
        AdapterListDev listDeveloper = new AdapterListDev(itemDev);

        //jeder
        rv.setAdapter(listDeveloper);
        return rv;
    }
}