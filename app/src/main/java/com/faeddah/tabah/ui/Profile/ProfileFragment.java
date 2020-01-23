package com.faeddah.tabah.ui.Profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends BaseFragment {

    public static final String TAG = ProfileFragment.class.getSimpleName();
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseUser user;
    private TextView tvProfilnama, tvprofilsaldo;
    private ImageView imgProfil;
    private ImageButton btnTopup,btnEditProfil,btnpengepul;
    private String uid, nama, email, tlp, imgurl;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
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
        if (user != null) {
            // status user login
            uid = user.getUid();
            nama = user.getDisplayName();
            email = user.getEmail();
            tlp = user.getPhoneNumber();
            imgurl = String.valueOf(user.getPhotoUrl());

            tvProfilnama.setText(nama);
            tvprofilsaldo.setText("Rp.10.000");
            Glide.with(getContext())
                    .load(imgurl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().centerCrop())
                    .into(imgProfil);

        }
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

        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Bundle data =  new Bundle();
                    data.putString("uid", uid);
                    data.putString("nama", nama);
                    data.putString("imgurl", imgurl);
                    data.putString("email", email);
                    data.putString("telp", tlp);
                    btnEditProfil.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_profile_editprofile, data));
                } else {
                    // status User  logout
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


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