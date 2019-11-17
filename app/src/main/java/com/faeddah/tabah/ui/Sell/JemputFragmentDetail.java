package com.faeddah.tabah.ui.Sell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class JemputFragmentDetail extends BaseFragment {
    public static final String TAG = JemputFragmentDetail.class.getSimpleName();
    private TextView nam,des,har,ala,kon;
    private String nama;
    private String deskrip;
    private String harga;
    private String alama;
    private String kont;

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

    }

    @Override
    public void initViews(View view) {
        Bundle arg = getArguments();
        if (arg != null) {
            nama = arg.getString("Nama");
            deskrip = arg.getString("Deskripsi");
            harga = arg.getString("harga_Perkg");
            alama = arg.getString("Alamat");
            kont = arg.getString("Kontak");
        }

        nam.setText(nama);
        des.setText(deskrip);
        har.setText(harga);
        ala.setText(alama);
        kon.setText(kont);
    }

    @Override
    public void initListeners(View view) {

    }


}
