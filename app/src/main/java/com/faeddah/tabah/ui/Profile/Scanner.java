package com.faeddah.tabah.ui.Profile;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.journeyapps.barcodescanner.CaptureActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.app.Activity.RESULT_OK;
import static android.app.Activity.RESULT_CANCELED;

public class Scanner extends BaseFragment {

    public static final String TAG = Scanner.class.getSimpleName();

    private FirebaseFirestore db;
    private CollectionReference cr;
    private Button btnscanner,btnscannersave;
    private TextView resultscanner;
    private LinearLayout llmid;
    private String idresultscanner="";
    private String contents;
    private String pembayaran;
    private TextView tvuser,tvketerangan,tvkota,tvjenis,tvpengepul;
    private String gtvuser, gtvketerangan, gtvkota, gtvjenis, gtvpengepul;
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
        btnscannersave = view.findViewById(R.id.btn_scanner_save);
        resultscanner = view.findViewById(R.id.result_scanner);
        llmid = view.findViewById(R.id.ll_keterangan);

        tvketerangan = view.findViewById(R.id.tv_scanner_keterangan);
        tvpengepul = view.findViewById(R.id.tv_scanner_pengepul);
        tvjenis = view.findViewById(R.id.tv_scanner_jenis);
        tvkota = view.findViewById(R.id.tv_scanner_kota);
        tvuser = view.findViewById(R.id.tv_scanner_uid_user);
    }

    @Override
    public void initViews(final View view) {
        llmid.setVisibility(View.GONE);
        btnscanner.setVisibility(View.VISIBLE);
        resultscanner.setVisibility(View.VISIBLE);
        btnscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String notfinalid = idresultscanner;
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

                btnscanner.setVisibility(View.GONE);
                resultscanner.setVisibility(View.GONE);
                llmid.setVisibility(View.VISIBLE);
            }
        });
        btnscannersave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gtvketerangan.equals("bayar")) {
                    setdata();
                    btnscanner.setVisibility(View.VISIBLE);
                    resultscanner.setVisibility(View.VISIBLE);
                    llmid.setVisibility(View.GONE);
                }else{
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Warning")
                            .setMessage("pembayaran telah Selesai")
                            .setPositiveButton("Scan", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    btnscanner.setVisibility(View.VISIBLE);
                                    resultscanner.setVisibility(View.VISIBLE);
                                    llmid.setVisibility(View.GONE);
                                }
                            })
                            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    btnscanner.setVisibility(View.VISIBLE);
                                    resultscanner.setVisibility(View.VISIBLE);
                                    llmid.setVisibility(View.GONE);
                                    tvuser.setText("");
                                    tvjenis.setText("");
                                    tvkota.setText("");
                                    tvpengepul.setText("");
                                    tvketerangan.setText("");
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    dialog.show();
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
                contents = data.getStringExtra("SCAN_RESULT"); //this is the result
                getdata();
//                resultscanner.setText(contents);
//                idresultscanner = contents;

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
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("transaksi_jbs").document(contents);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        resultscanner.setText(document.getString("jenis_sampah"));
                        gtvuser = document.getString("uid_user");
                        gtvjenis = document.getString("jenis_sampah");
                        gtvkota = document.getString("kota");
                        gtvpengepul = document.getString("uid_pengepul");
                        gtvketerangan = document.getString("keterangan");
                        getuserdetail();
                        tvjenis.setText(gtvjenis);
                        tvkota.setText(gtvkota);
                        tvpengepul.setText(gtvpengepul);
                        tvketerangan.setText(document.getString("keterangan"));
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    public void getuserdetail(){

        db.collection("users_detail")
                .whereEqualTo("uid", gtvuser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String nama = document.getString("nama");
                                tvuser.setText(nama);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
