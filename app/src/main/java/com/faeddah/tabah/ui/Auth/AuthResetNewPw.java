package com.faeddah.tabah.ui.Auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;

public class AuthResetNewPw extends BaseFragment {

    public static final String TAG = AuthResetNewPw.class.getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_resetpw_newpw, container,false);
        findViews(view);
        initViews(view);
        initListeners(view);
//        AppCompatActivity activity = (AppCompatActivity) view.getContext();
//        Toast.makeText(getContext(), String.valueOf(activity.getSupportFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT).show();
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
