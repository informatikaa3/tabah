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
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.model.Antar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
    private Button btnscanner,btnscannersave,btnpenjemputan,btnjemput;
    private TextView resultscanner;
    private LinearLayout llmid;
    private String contents;
    private TextView tvuser,tvketerangan,tvkota,tvjenis,tvpengepul,tvalamat,tvtotber,tvtothar;
    private String gtvuser, gtvketerangan, gtvkota, gtvjenis, gtvpengepul; //scanner
    private Integer gtvtotber,gtvtothar;
    private String puser,pketerangan,pkota,pjenis, ppengepul, pdocument; //penjemputan



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
        btnpenjemputan = view.findViewById(R.id.btn_pejemputan);
//        btnjemput = view.findViewById(R.id.btn_scanner_jemput);
        llmid = view.findViewById(R.id.ll_keterangan);

        tvketerangan = view.findViewById(R.id.tv_scanner_keterangan);
        tvpengepul = view.findViewById(R.id.tv_scanner_pengepul);
        tvjenis = view.findViewById(R.id.tv_scanner_jenis);
        tvkota = view.findViewById(R.id.tv_scanner_kota);
        tvuser = view.findViewById(R.id.tv_scanner_uid_user);
        tvalamat = view.findViewById(R.id.tv_scanner_alamat);
        tvtotber = view.findViewById(R.id.tv_scanner_total_berat);
        tvtothar = view.findViewById(R.id.tv_scanner_total_harga);
    }

    @Override
    public void initViews(final View view) {
//        llmid.setVisibility(View.GONE);
        btnscannersave.setVisibility(view.GONE);
        btnpenjemputan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifjemput();
                if(pketerangan != null){
                    btnscannersave.setVisibility(view.VISIBLE);
                }
            }
        });
        btnscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnscannersave.setVisibility(view.VISIBLE);
//                String notfinalid = idresultscanner;
                try {
                    pketerangan ="";
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
        });
        btnscannersave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pketerangan.equals("penjemputan")){
                    prosesjemput();
                    nextpenjemputan();
                    btnscannersave.setVisibility(view.VISIBLE);
                } else if(pketerangan.equals("Dalam Perjalanan")){
                    perjalananselesai();
                    tvuser.setText("");
                    tvjenis.setText("");
                    tvkota.setText("");
                    tvpengepul.setText("");
                    tvketerangan.setText("");
                    tvalamat.setText("");
                    btnscannersave.setVisibility(view.GONE);

                } else if (gtvketerangan.equals("bayar")) {
                    setdata();
                    tvuser.setText("");
                    tvjenis.setText("");
                    tvkota.setText("");
                    tvpengepul.setText("");
                    tvketerangan.setText("");
                    tvalamat.setText("");
                    btnscannersave.setVisibility(view.GONE);
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Warning")
                            .setMessage("pembayaran telah Selesai")
                            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        tvuser.setText("");
                                        tvjenis.setText("");
                                        tvkota.setText("");
                                        tvpengepul.setText("");
                                        tvketerangan.setText("");
                                        tvalamat.setText("");
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        dialog.show();
                    }
                btnscannersave.setVisibility(view.VISIBLE);

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uidpengepul= user.getUid();

        db.collection("transaksi_jbs").document(contents).update(
                "keterangan", "lunas","uid_pengepul",uidpengepul
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
                        gtvuser = document.getString("uid_user");
                        gtvjenis = document.getString("jenis_sampah");
                        gtvkota = document.getString("kota");
                        gtvpengepul = document.getString("uid_pengepul");
                        gtvketerangan = document.getString("keterangan");
                        gtvtotber = document.getLong("total_berat").intValue();
                        getuserdetail(gtvuser);

                        Log.d(TAG, "TATAMBAHANaa "+gtvtothar+" "+gtvtotber);

                        Integer totbersementara,totharsementara,result;
                        totbersementara = gtvtotber;
                        totharsementara = gtvtothar;

                        Log.d(TAG, "TATAMBAHAN "+totbersementara+" "+totharsementara);

                        result = totbersementara*totharsementara;
                        gtvtothar = result;

                        tvtothar.setText(gtvtothar);
                        tvtotber.setText(gtvtotber);
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
    public void getuserdetail(String uid){

        db.collection("users_detail")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String nama = document.getString("nama");
                                gtvtothar = document.getLong("harga").intValue();
                                tvuser.setText(nama);
                                tvalamat.setText(document.getString("alamat"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }public void getpengepuldetail(String uid){

        db.collection("users_detail")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String nama = document.getString("nama");
                                tvpengepul.setText(nama);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void notifjemput(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String pengepulid = user.getUid();

        db = FirebaseFirestore.getInstance();
        db.collection("transaksi_jbs")
                .whereEqualTo("uid_pengepul", pengepulid)
                .whereEqualTo("keterangan", "penjemputan")
                .get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Tidak Ada Antrian Jemput Sampah", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                puser = document.getString("uid_user");
                                pjenis = document.getString("jenis_sampah");
                                pkota = document.getString("kota");
                                ppengepul = document.getString("uid_pengepul");
                                pketerangan = document.getString("keterangan");
                                pdocument = document.getId();
                                getuserdetail(puser);
                                getpengepuldetail(ppengepul);
                                tvjenis.setText(pjenis);
                                tvkota.setText(pkota);
                                tvketerangan.setText(pketerangan);
                                Log.d(TAG, "PLS JALAN"+document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void prosesjemput(){
//        tvketerangan.setText("Dalam ");
        db.collection("transaksi_jbs")
                .document(pdocument)
                .update(
                "keterangan", "Dalam Perjalanan"
        );
    }
    public void nextpenjemputan(){
        db = FirebaseFirestore.getInstance();
        db.collection("transaksi_jbs")
                .whereEqualTo("uid_pengepul", ppengepul)
                .whereEqualTo("keterangan", "Dalam Perjalanan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                puser = document.getString("uid_user");
                                pjenis = document.getString("jenis_sampah");
                                pkota = document.getString("kota");
                                ppengepul = document.getString("uid_pengepul");
                                pketerangan = document.getString("keterangan");
                                pdocument = document.getId();
                                getuserdetail(puser);
                                getpengepuldetail(ppengepul);
                                tvjenis.setText(pjenis);
                                tvkota.setText(pkota);
                                tvketerangan.setText(pketerangan);
                                Log.d(TAG, "PLS JALAN"+document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void perjalananselesai(){
        db.collection("transaksi_jbs")
                .document(pdocument)
                .update(
                        "keterangan", "bayar TH"
                );
    }
}
