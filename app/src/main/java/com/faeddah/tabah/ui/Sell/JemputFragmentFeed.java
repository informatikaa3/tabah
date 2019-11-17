package com.faeddah.tabah.ui.Sell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.adapter.AdapterSellJemput;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class JemputFragmentFeed extends BaseFragment {
    public static final String TAG = JemputFragmentFeed.class.getSimpleName();
    private ListView listView;

    private String list_nama[]={
            "Yahya Sukses",
            "Widi Sutresno",
            "Rizki Supirman",
            "Lefri Sukijem",
            "Elgi Sukiman"
    };
    private String deskripsi[]={
            "Menerima sampah Plastik, Kayu, Besi",
            "Menerima sampah Elektronik",
            "Menerima sampah alat elektronik(mati atau hidup)",
            "Menerima sampah Plastik",
            "Menerima sampah Besi, Logam, Baja"
    };
    private String harga_perkg[]={
            "Rp. 2000/kg",
            "Rp. 5000/kg",
            "Sesuai keadaan",
            "Rp. 1500/kg",
            "Rp. 8000/kg"
    };
    private String Alamat[]={
            "Jalan Karapitan No.116 , kec. Regol, Kota Bandugn",
            "Jalan Karapitan No.117 , kec. Regol, Kota Bandugn",
            "Jalan Karapitan No.118 , kec. Regol, Kota Bandugn",
            "Jalan Karapitan No.119 , kec. Regol, Kota Bandugn",
            "Jalan Karapitan No.120 , kec. Regol, Kota Bandugn"
    };
    private String Kontak[]={
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
        findViews(view);
        initViews(view);
        initListeners(view);
        showListView();

        return view;
    }

    @Override
    public void findViews(View view) {
        listView = view.findViewById(R.id.lv_sell_jemput);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initListeners(View view) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JemputFragmentDetail jemputFragmentDetail = new JemputFragmentDetail();
                Bundle oper = new Bundle();
                oper.putString("Nama",list_nama[position]);
                oper.putString("Deskripsi",deskripsi[position]);
                oper.putString("harga_Perkg",harga_perkg[position]);
                oper.putString("Alamat",Alamat[position]);
                oper.putString("Kontak",Kontak[position]);
                jemputFragmentDetail.setArguments(oper);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_sellreplace, jemputFragmentDetail).addToBackStack(jemputFragmentDetail.TAG).commit();
            }
        });
    }

    public void showListView(){
        AdapterSellJemput adapterSellJemput = new AdapterSellJemput(getActivity(), list_nama,deskripsi,harga_perkg, Alamat, Kontak);
        listView.setAdapter(adapterSellJemput);
    }
}
