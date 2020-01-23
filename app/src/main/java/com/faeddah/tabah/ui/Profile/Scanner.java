package com.faeddah.tabah.ui.Profile;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.model.Antar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.journeyapps.barcodescanner.CaptureActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.app.Activity.RESULT_OK;
import static android.app.Activity.RESULT_CANCELED;

public class Scanner extends BaseFragment {

    public static final String TAG = Scanner.class.getSimpleName();

    private String gtvuser, gtvketerangan, gtvkota, gtvjenis, gtvpengepul;
    private Map<String, Object> datatransaksi = new HashMap<>();

    private FirebaseFirestore db;
    private DocumentReference dr;
    private Button btnscanner,btnscannersave;
    private TextView resultscanner;
    private String idresultscanner="";
    private String contents;
    private TextView tvuser,tvketerangan,tvkota,tvjenis,tvpengepul;
    private ZXingScannerView mScannerView;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_scanner,container,false);
        db = FirebaseFirestore.getInstance();

        findViews(view);
        initViews(view);
        initListeners(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        btnscanner = view.findViewById(R.id.btn_scanner);
        btnscannersave = view.findViewById(R.id.btn_scanner_save);
        resultscanner = view.findViewById(R.id.result_scanner);

        tvketerangan = view.findViewById(R.id.tv_scanner_keterangan);
        tvpengepul = view.findViewById(R.id.tv_scanner_pengepul);
        tvjenis = view.findViewById(R.id.tv_scanner_jenis);
        tvkota = view.findViewById(R.id.tv_scanner_keterangan);
        tvuser = view.findViewById(R.id.tv_scanner_uid_user);
    }

    @Override
    public void initViews(View view) {
        btnscanner.setVisibility(View.VISIBLE);
        resultscanner.setVisibility(View.VISIBLE);
        btnscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notfinalid = idresultscanner;
                if (!idresultscanner.isEmpty()) {
                    btnscanner.setVisibility(View.GONE);
                    resultscanner.setVisibility(View.GONE);
                }else{
                    try {
                        Intent intent = new Intent(getContext(), CaptureActivity.class);
                        intent.setAction("com.google.zxing.client.android.SCAN");
                        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                        intent.putExtra("SAVE_HISTORY", false);
                        startActivityForResult(intent, 0);
                    } catch (Exception e) {
                        Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                        startActivity(marketIntent);
                    }
                }
            }
        });
        btnscannersave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdata();
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
                contents = data.getStringExtra("SCAN_RESULT"); //this is the result
                getdata();
                resultscanner.setText(contents);
                idresultscanner = contents;

            } else
            if (resultCode == RESULT_CANCELED) {
                resultscanner.setText("RESULT TIDAK ADA");
            }
        }
    }

    public void setdata(){

        db.collection("transaksi_jbs").document(contents).update(
                "keterangan", "lunas"
        );
        Toast.makeText(getContext(),"Proses....!!",Toast.LENGTH_LONG).show();
    }

    public void getdata(){
        db.collection("transaksi_jbs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId() == contents){
                                    resultscanner.setText(document.get("jenis_sampah").toString());
                                    gtvuser = document.get("uid_user").toString();
                                    gtvjenis = document.get("jenis_sampah").toString();
                                    gtvkota = document.get("kota").toString();
                                    gtvpengepul = document.get("uid_pengepul").toString();
                                    gtvketerangan = "lunas";
                                    tvuser.setText(gtvuser);
                                    tvjenis.setText(gtvjenis);
                                    tvkota.setText(gtvuser);
                                    tvpengepul.setText(gtvpengepul);
                                    tvketerangan.setText(document.get("keterangan").toString());
                                }
                                Log.d("bismillah", document.getId() + "=>" + document.getData() + "ini UID dokumen : " + "=>"+contents);
                            }
                        } else {
                            Log.d("anjing", "Error getting documents: ", task.getException());
                        }
                    }
                });

//        this.db = FirebaseFirestore.getInstance();
//        dr = db.collection("tarnsaksi_jbs").document(idresultscanner);
//        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                final Antar antar = documentSnapshot.toObject(Antar.class);
//                tvjenis.setText(antar.getjenis_sampah());
//            }
//        });
////        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
////            @Override
////            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
////                if(task.isSuccessful()){
////                    DocumentSnapshot documentSnapshot = task.getResult();
////                    if (documentSnapshot.exists()){
////                        Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
////                        String cek = documentSnapshot.getData("jenis_sampah");
////
////                    }else {
////                        Log.d(TAG, "No such document");
////                    }
////                }else{
////                    Log.d(TAG, "get failed with ", task.getException());
////                }
////            }
////        });
    }
}