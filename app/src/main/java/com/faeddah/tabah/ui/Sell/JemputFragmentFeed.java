package com.faeddah.tabah.ui.Sell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.faeddah.tabah.R;
import com.faeddah.tabah.adapter.AdapterSellJemput;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class JemputFragmentFeed extends Fragment {
    String list_nama[]={
            "Yahya Sukses",
            "Widi Sutresno",
            "Rizki Supirman",
            "Lefri Sukijem",
            "Elgi Sukiman"
    };
    String deskripsi[]={
            "Menerima sampah Plastik, Kayu, Besi",
            "Menerima sampah Elektronik",
            "Menerima sampah alat elektronik(mati atau hidup)",
            "Menerima sampah Plastik",
            "Menerima sampah Besi, Logam, Baja"
    };
    String harga_perkg[]={
            "Rp. 2000/kg",
            "Rp. 5000/kg",
            "Sesuai keadaan",
            "Rp. 1500/kg",
            "Rp. 8000/kg"
    };
    String Alamat[]={
            "Jalan Karapitan No.116 , kec. Regol, Kota Bandugn",
            "Jalan Karapitan No.117 , kec. Regol, Kota Bandugn",
            "Jalan Karapitan No.118 , kec. Regol, Kota Bandugn",
            "Jalan Karapitan No.119 , kec. Regol, Kota Bandugn",
            "Jalan Karapitan No.120 , kec. Regol, Kota Bandugn"
    };
    String Kontak[]={
            "+628877665544",
            "+628866554433",
            "+628855443322",
            "+628844332211",
            "+628833221199"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_jemput,container,false);

        final ListView listView= view.findViewById(R.id.lv_sell_jemput);

        AdapterSellJemput adapterSellJemput = new AdapterSellJemput(getActivity(), list_nama,deskripsi,harga_perkg, Alamat, Kontak);
        listView.setAdapter(adapterSellJemput);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent a = new Intent(getContext().getApplicationContext(), JemputFragmentDetail.class);

                a.putExtra("Nama",list_nama[position]);
                a.putExtra("Deskripsi",deskripsi[position]);
                a.putExtra("harga_Perkg",harga_perkg[position]);
                a.putExtra("Alamat",Alamat[position]);
                a.putExtra("Kontak",Kontak[position]);

//                BUG ON ACTIVITY mush be fix, lier lah aing
                view.getContext().startActivity(a);
                getActivity().finish();
            }
        });
        return view;
    }
}
