package com.faeddah.tabah.ui.Sell;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.model.Antar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class JemputFragmentDetail extends BaseFragment {
    public static final String TAG = JemputFragmentDetail.class.getSimpleName();
    private TextView nam,des,har,ala,kon, btnkm;
    private String nama;
    private String deskrip;
    private String harga;
    private String alama;
    private String kont;
    private String kota;
    private String uid;


    private FirebaseFirestore db;
    private String userUid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_jemput_detail, container, false);
        findViews(view);
        initViews(view);
        initListeners(view);

        return view;
    }

    @Override
    public void findViews(View view) {
        nam = view.findViewById(R.id.tv_nama_j_detail);
        des = view.findViewById(R.id.tv_deskripsi_j_detail);
        har = view.findViewById(R.id.tv_harga_j_detial);
        ala = view.findViewById(R.id.tv_alamat_j_detail);
        kon = view.findViewById(R.id.tv_kontak_j_detail);
        btnkm = view.findViewById(R.id.btn_sell_detail_jemput);


    }

    @Override
    public void initViews(View view) {
        Bundle arg = getArguments();
        if (arg != null) {
            nama = arg.getString("nama");
            deskrip = arg.getString("kategori");
            harga = arg.getString("harga");
            alama = arg.getString("alamat");
            kont = arg.getString("telp");
            kota = arg.getString("kota");
            uid = arg.getString("uid");
        }

        nam.setText(nama);
        des.setText(deskrip);
        har.setText("Rp. "+harga+"/KG");
        ala.setText(alama);
        kon.setText(kont);
    }

    @Override
    public void initListeners(View view) {

        btnkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getdbdata().equals("")){
                    notloginDialog(getContext());
                }else{
                    topupDialog(getContext());
                }
            }
        });

    }
    public void notloginDialog(Context ctx){
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setTitle("Konfirmasi")
                .setMessage("Silahkan Login Terlebih Dahulu")
//                .setView(inflater.inflate(R.layout.fragment_sell_jemput_detail_conf,null))
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO : show progress bar,
                        dialog.dismiss();

                    }
                })
                .create();
        dialog.show();
    }

    public void topupDialog(Context ctx){
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setTitle("Konfirmasi")
                .setMessage("Penjemputan Samoah")
//                .setView(inflater.inflate(R.layout.fragment_sell_jemput_detail_conf,null))
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO : show progress bar,
                        getdbdata();
                        String keterangan = "penjemputan";
                        senddatadb(new Antar(deskrip,keterangan,kota,uid,userUid));
                        Toast.makeText(getContext(), "Sedang di Proses...", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }
    public void senddatadb(Antar antar){
        this.db = FirebaseFirestore.getInstance();
        db.collection("transaksi_jbs").add(antar).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
                DocumentReference docRef = task.getResult();
//                message = docRef.getId();
//                Log.v("KEY", message);
//                qrdialog(getContext());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing document", e);
            }
        });
    }
    public String getdbdata(){
        String result = new String();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            userUid = user.getUid();
            result = user.getUid();
        }else{
            result = "";
        }
        return result;
    }

}
