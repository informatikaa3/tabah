package com.faeddah.tabah.ui.Profile;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.app.Activity.RESULT_OK;
import static android.app.Activity.RESULT_CANCELED;

public class Scanner extends BaseFragment {
    private Button btnscanner;
    private TextView resultscanner,resultscanneraddress;
    private ZXingScannerView mScannerView;

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
                try {
                    Intent intent = new Intent(getContext(), CaptureActivity.class);
                    intent.setAction("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    intent.putExtra("SAVE_HISTORY", false);
                    startActivityForResult(intent,0);
                }catch (Exception e){
                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);
                }
            }
        });
    }

    @Override
    public void initListeners(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT"); //this is the result
                resultscanner.setText(contents);
            } else
            if (resultCode == RESULT_CANCELED) {
                resultscanner.setText("RESULT TIDAK ADA");
            }
        }
    }

}
