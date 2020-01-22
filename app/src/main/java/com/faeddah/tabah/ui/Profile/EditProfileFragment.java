package com.faeddah.tabah.ui.Profile;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfileFragment extends BaseFragment {

    public static final String TAG = "profile_edit";
    private String uid,nama,email,notlp, imgurl;
    private TextInputEditText edtemail, edtnama, edtnotlp, edtalamat, edtpassword;
    private ImageView imgProfil;
    private Button upgrade, gantipw;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_editprofile, container,false);
        findViews(view);
        initViews(view);
        initListeners(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void findViews(View view) {
        edtemail = view.findViewById(R.id.edt_email);
        edtnama =  view.findViewById(R.id.edt_nama);
//        edtnotlp = view.findViewById(R.id.edt)
        edtalamat = view.findViewById(R.id.edt_alamat);
        edtpassword = view.findViewById(R.id.edt_password);
        imgProfil = view.findViewById(R.id.img_profil);
        upgrade = view.findViewById(R.id.btn_upgrade);
        gantipw = view.findViewById(R.id.btn_gantipw);


    }

    @Override
    public void initViews(View view) {
        Bundle data = getArguments();
        if (!data.isEmpty()){
            uid = data.getString("uid");
            nama = data.getString("nama");
            email= data.getString("email");
            notlp = data.getString("telp");
            imgurl = data.getString("imgurl");

            edtemail.setText(email);
            edtnama.setText(nama);
            edtalamat.setText("tes");
            Glide.with(getContext())
                    .load(imgurl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().centerCrop())
                    .into(imgProfil);
        }

        Toast.makeText(getContext(), uid, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void initListeners(View view) {

        gantipw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragmentnya = null;
                fragmentnya = activity.getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);
                final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.detach(fragmentnya);
                ft.attach(fragmentnya);
                ft.commit();
            }
        });

    }
}
