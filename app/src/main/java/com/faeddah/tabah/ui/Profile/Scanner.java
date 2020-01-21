package com.faeddah.tabah.ui.Profile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends BaseFragment {
    private Button btnscanner;
    private TextView resultscanner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_scanner,container,false);
        findViews(view);
        initViews(view);
        initListeners(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        btnscanner = view.findViewById(R.id.btn_scanner);
        resultscanner = view.findViewById(R.id.result_scanner);
    }

    @Override
    public void initViews(View view) {
        btnscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.initiateScan();
            }
        });
    }

    @Override
    public void initListeners(View view) {

    }



}
