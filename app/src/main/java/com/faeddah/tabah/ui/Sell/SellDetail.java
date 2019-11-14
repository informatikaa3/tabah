package com.faeddah.tabah.ui.Sell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellDetail extends BaseFragment {

    private String judul_barang,deskripsi_barang,harga_barang,img_sell;
    private TextView tv_jdlsell, tv_deskripsi, tv_price;
    private ImageView imgview;

    public SellDetail() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sell_feed,container, false);
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
        tv_jdlsell = view.findViewById(R.id.tv_jdlsell_detail);
        tv_deskripsi = view.findViewById(R.id.tv_isisell_detail);
        tv_price = view.findViewById(R.id.tv_isisell_price);
        imgview = view.findViewById(R.id.img_sell_detail);
    }

    @Override
    public void initViews(View view) {
        Bundle arg = getArguments();
        if (!arg.isEmpty()){
            judul_barang = arg.getString("judul_barang");
            deskripsi_barang = arg.getString("deskripsi_barang");
            harga_barang = arg.getString("harga_barang");
            img_sell = arg.getString("img_sell");
        } else {
            Snackbar.make(
                    getActivity().findViewById(R.id.ke_detail_artikel), "Data Tidak Ada ...", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
        tv_jdlsell.setText(judul_barang);
        tv_deskripsi.setText(deskripsi_barang);
        tv_price.setText(harga_barang);
        Glide.with(view)
                .load(img_sell)
                .onlyRetrieveFromCache(true)
                .into(imgview);
    }

    @Override
    public void initListeners(View view) {

    }

    public void ShowRvSell(){

    }
}