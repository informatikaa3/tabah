package com.faeddah.tabah.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.model.Jemput;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;


public class AdapterSellJemput extends ArrayAdapter<Jemput> {

    public static final String TAG = AdapterSellJemput.class.getSimpleName();

    public AdapterSellJemput(FragmentActivity activity, List<Jemput> jemputList) {
        super(activity,0, jemputList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.fragment_sell_jemput_feed,null);
        }

        TextView nama = convertView.findViewById(R.id.tv_nama_jemput);
        TextView alamat = convertView.findViewById(R.id.tv_alamat_jemput);
        TextView harga = convertView.findViewById(R.id.tv_harga_jemput);
        CardView cardView = convertView.findViewById(R.id.card_item_sell_jemput);

        Jemput jemput = getItem(position);

        nama.setText(jemput.getNama());
        alamat.setText(jemput.getAlamat());
        harga.setText("Rp. "+jemput.getHarga()+"/KG");

        Bundle oper = new Bundle();
        oper.putString("nama",jemput.getNama());
        oper.putString("alamat",jemput.getAlamat());
        oper.putString("harga",jemput.getHarga());
        oper.putString("kategori",jemput.getKategori_sampah());
        oper.putString("kota",jemput.getKota());
        oper.putString("telp",jemput.getTelp());
        oper.putString("uid",jemput.getUid());
        cardView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_sell_jemput_detail,oper));




        return convertView;
    }

    //    FirebaseFirestore db;
//    QueryDocumentSnapshot document;
//
//    String alamat;
//    String harga;
//    String[] kategori;
//    String plastik;
//    String kota;
//    String uid;
//
//    public AdapterSellJemput(@NonNull Context context, List<Jemput> object) {
//        super(context, 0, object);
//    }

//        Activity fragment;
//    public AdapterSellJemput(Activity context, String[] list_nama, String[] deskripsi, String[] harga_perkg, String[] alamat, String[] kontak) {
//        super(context, R.layout.fragment_sell_jemput_feed, list_nama );
//        this.list_nama = list_nama;
//        fragment = context;
//        this.deslripsi = deskripsi;
//        this.harga_perkg = harga_perkg;
//        this.alamat = alamat;
//        this.kontak = kontak;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater =fragment.getLayoutInflater();
//        View view = inflater.inflate(R.layout.fragment_sell_jemput_feed, null);
//
//        TextView nama, alama, harga;
//
//        nama = view.findViewById(R.id.tv_nama_jemput);
//        alama = view.findViewById(R.id.tv_alamat_jemput);
//        harga = view.findViewById(R.id.tv_harga_jemput);
//
//        nama.setText(list_nama[position]);
//        alama.setText(alamat[position]);
//        harga.setText(harga_perkg[position]);
//
//        return view;
//    }
}
