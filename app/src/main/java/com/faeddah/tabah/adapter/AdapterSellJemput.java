package com.faeddah.tabah.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.faeddah.tabah.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class AdapterSellJemput extends ArrayAdapter {

    String list_nama[];
    String deslripsi[];
    String harga_perkg[];
    String alamat[];
    String kontak[];

    Activity fragment;
    public AdapterSellJemput(Activity context, String[] list_nama, String[] deskripsi, String[] harga_perkg, String[] alamat, String[] kontak) {
        super(context, R.layout.fragment_sell_jemput_feed, list_nama );
        this.list_nama = list_nama;
        fragment = context;
        this.deslripsi = deskripsi;
        this.harga_perkg = harga_perkg;
        this.alamat = alamat;
        this.kontak = kontak;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =fragment.getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_sell_jemput_feed, null);

        TextView nama, alama, harga;

        nama = view.findViewById(R.id.tv_nama_jemput);
        alama = view.findViewById(R.id.tv_alamat_jemput);
        harga = view.findViewById(R.id.tv_harga_jemput);

        nama.setText(list_nama[position]);
        alama.setText(alamat[position]);
        harga.setText(harga_perkg[position]);

        return view;
    }
}
