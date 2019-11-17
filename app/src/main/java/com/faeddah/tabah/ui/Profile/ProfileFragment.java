package com.faeddah.tabah.ui.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
    public void initListeners(View view) {

    }
}