package com.faeddah.tabah.ui.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.journeyapps.barcodescanner.CaptureActivity;

public class ProfileFragment extends BaseFragment {

    public static final String TAG = ProfileFragment.class.getSimpleName();
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseUser user;
    private TextView tvProfilnama, tvprofilsaldo;
    private ImageView imgProfil,changetypeuser;
    private ImageButton btnTopup;
    private LinearLayout linearLayout;


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
        linearLayout = view.findViewById(R.id.frame_profile_user);
        tvProfilnama = view.findViewById(R.id.tv_profilnama);
        tvprofilsaldo = view.findViewById(R.id.tv_saldo);
        changetypeuser = view.findViewById(R.id.change_type_user);
        imgProfil = view.findViewById(R.id.img_profil);
        btnTopup = view.findViewById(R.id.btn_topup);
    }

    @Override
    public void initViews(View view) {
        auth = FirebaseAuth.getInstance();
        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // status user login
                    tvProfilnama.setText(user.getDisplayName());
                    tvprofilsaldo.setText("Rp.10.000");
                    Glide.with(getContext())
                            .load(user.getPhotoUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(new RequestOptions().centerCrop())
                            .into(imgProfil);
                } else {
                    // status User  logout
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void initListeners(final View view) {
        linearLayout.setVisibility(view.VISIBLE);
        btnTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topupDialog(getContext());
            }
        });
        changetypeuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(view.GONE);
                Scanner scanner = new Scanner();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_profile, scanner).commit();
            }
        });

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