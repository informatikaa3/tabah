package com.faeddah.tabah.ui.Sell;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class JemputFragmentDetail extends BaseFragment {
    public static final String TAG = JemputFragmentDetail.class.getSimpleName();
    private TextView nam,des,har,ala,kon, btnkm;
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
        btnkm = view.findViewById(R.id.btn_sell_detail_jemput);


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

        btnkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topupDialog(getContext());
            }
        });

    }

    public void topupDialog(Context ctx){
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setTitle("Konfirmasi")
                .setMessage("Penjemputan Samoah")
                .setView(inflater.inflate(R.layout.fragment_sell_jemput_detail_conf,null))
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO : show progress bar,   validasi token, rubah saldo user terkait, error handling,
                        Toast.makeText(getContext(), "Sedang di Proses...", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }


}
