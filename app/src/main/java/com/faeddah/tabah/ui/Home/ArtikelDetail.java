package com.faeddah.tabah.ui.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.android.material.snackbar.Snackbar;

public class ArtikelDetail extends BaseFragment {

    private String judul,artikel,imgurl;
    private TextView tv_jdl, tv_artikel;
    private ImageView imgview;

    public ArtikelDetail() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_artikel_detail,container, false);
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
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void findViews(View view) {
        tv_jdl = view.findViewById(R.id.tv_jdlArtikel_detail);
        tv_artikel = view.findViewById(R.id.tv_artikel_detail);
        imgview = view.findViewById(R.id.img_artikel_detail);
    }

    @Override
    public void initViews(View view) {
        Bundle arg = getArguments();
        if (!arg.isEmpty()){
            judul = arg.getString("judul");
            artikel = arg.getString("artikel");
            imgurl = arg.getString("imgurl");
        } else {
            Snackbar.make(
            getActivity().findViewById(R.id.ke_detail_artikel), "Data Tidak Ada ...", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
        tv_jdl.setText(judul);
        tv_artikel.setText(artikel);
        Glide.with(view)
                .load(imgurl)
                .onlyRetrieveFromCache(true)
                .into(imgview);
    }

    @Override
    public void initListeners(View view) {

    }

}
