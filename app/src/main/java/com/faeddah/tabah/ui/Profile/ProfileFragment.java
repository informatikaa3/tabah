package com.faeddah.tabah.ui.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileFragment extends BaseFragment {

    public static final String TAG = ProfileFragment.class.getSimpleName();
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private TextView tvProfilnama, tvprofilsaldo;
    private ImageView imgProfil;
    private ImageButton btnTopup,btnEditProfil,btnpengepul;
    private String uid, nama, email, tlp, imgurl, alamat,saldo;
    private static final String namaKoleksi = "users_detail";
    private Bundle data = new Bundle();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        db = FirebaseFirestore.getInstance();
        findViews(view);
        initViews(view);
        initListeners(view);
        return view;
    }

    @Override
    public void onStart() {
        auth.addAuthStateListener(stateListener);
        super.onStart();
    }

    @Override
    public void onStop() {
        auth.addAuthStateListener(stateListener);
        super.onStop();
    }

    @Override
    public void findViews(View view) {
        tvProfilnama = view.findViewById(R.id.tv_profilnama);
        tvprofilsaldo = view.findViewById(R.id.tv_saldo);
        imgProfil = view.findViewById(R.id.img_profil);
        btnTopup = view.findViewById(R.id.btn_topup);
        btnEditProfil = view.findViewById(R.id.btn_editprofile);
        btnpengepul = view.findViewById(R.id.btn_pengepul);
    }

    @Override
    public void initViews(View view) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null) {
                    db.collection(namaKoleksi)
                            .whereEqualTo("uid", user.getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            uid = user.getUid();
                                            nama = document.get("nama").toString();
                                            email = user.getEmail();
                                            tlp = document.get("telp").toString();
                                            imgurl = document.get("imgUrl").toString();
                                            alamat = document.get("alamat").toString();
                                            saldo = document.get("saldo").toString();

                                            data.putString("uid", uid);
                                            data.putString("nama", nama);
                                            data.putString("imgurl", imgurl);
                                            data.putString("email", email);
                                            data.putString("telp", tlp);
                                            data.putString("alamat", alamat);
                                        }
                                        if (isAdded()){
                                            tvProfilnama.setText(nama);
                                            tvprofilsaldo.setText(saldo);
                                            Glide.with(getContext())
                                                    .load(imgurl)
                                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                    .apply(new RequestOptions().centerCrop())
                                                    .into(imgProfil);

                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
            }
        };
    }

    @Override
    public void initListeners(View view) {
            btnTopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    topupDialog(getContext());
                }
            });
            btnpengepul.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_profile_scanner));
            btnEditProfil.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_profile_editprofile, data));

    }


    public void topupDialog(Context ctx){
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setTitle("Topup Saldo")
                .setMessage("Masukan Kode Token")
                .setView(inflater.inflate(R.layout.fragment_profile_topup,null))
                .setPositiveButton("Proses", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO : show progress bar,   validasi token, rubah saldo user terkait, error handling,
                        Toast.makeText(getContext(), "Proses Top UP....", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

}