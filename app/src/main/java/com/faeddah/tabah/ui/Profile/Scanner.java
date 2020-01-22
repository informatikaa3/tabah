package com.faeddah.tabah.ui.Profile;


import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

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
        resultscanneraddress = view.findViewById(R.id.result_scanner_address);
    }

    @Override
    public void initViews(View view) {
        btnscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    IntentIntegrator integrator = new IntentIntegrator(getActivity());
                    integrator.setOrientationLocked(false);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    integrator.setPrompt("Scan a barcode");
                    integrator.setCameraId(0);  // Use a specific camera of the device
                    integrator.setBeepEnabled(false);
                    integrator.setBarcodeImageEnabled(true);
                    integrator.initiateScan();
                } catch (Exception e) {
                    resultscanner.setText("PUSING AJING");

                }
            }
        });
    }

    @Override
    public void initListeners(View view) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    resultscanner.setText(obj.getString("message"));
//                    resultscanneraddress.setText(obj.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            resultscanner.setText("ANJING");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
