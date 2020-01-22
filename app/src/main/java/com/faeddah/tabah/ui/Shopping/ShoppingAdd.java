package com.faeddah.tabah.ui.Shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;

public class ShoppingAdd extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_add_produk, container, false);
        findViews(view);
        initViews(view);
        initListeners(view);

        return view;
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
