package com.faeddah.tabah.ui.Sell;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.adapter.AdapterSellJemput;
import com.faeddah.tabah.model.Jemput;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class JemputFragmentFeed extends BaseFragment {
    public static final String TAG = JemputFragmentFeed.class.getSimpleName();
    private ListView listView;
    private FirebaseFirestore db;

//    private String list_nama[]={
//            "Yahya Sukses",
//            "Widi Sutresno",
//            "Rizki Supirman",
//            "Lefri Sukijem",
//            "Elgi Sukiman"
//    };
//    private String deskripsi[]={
//            "Menerima sampah Plastik, Kayu, Besi",
//            "Menerima sampah Elektronik",
//            "Menerima sampah alat elektronik(mati atau hidup)",
//            "Menerima sampah Plastik",
//            "Menerima sampah Besi, Logam, Baja"
//    };
//    private String harga_perkg[]={
//            "Rp. 2000/kg",
//            "Rp. 5000/kg",
//            "Sesuai keadaan",
//            "Rp. 1500/kg",
//            "Rp. 8000/kg"
//    };
//    private String Alamat[]={
//            "Jalan Karapitan No.116 , kec. Regol, Kota Bandugn",
//            "Jalan Karapitan No.117 , kec. Regol, Kota Bandugn",
//            "Jalan Karapitan No.118 , kec. Regol, Kota Bandugn",
//            "Jalan Karapitan No.119 , kec. Regol, Kota Bandugn",
//            "Jalan Karapitan No.120 , kec. Regol, Kota Bandugn"
//    };
//    private String Kontak[]={
//            "+628877665544",
//            "+628866554433",
//            "+628855443322",
//            "+628844332211",
//            "+628833221199"
//    };



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
//                oper.putString("Nama", String.valueOf(list_nama.indexOf(position)));
////                oper.putString("Deskripsi", String.valueOf(deskripsi.indexOf(position)));
////                oper.putString("harga_Perkg", String.valueOf(harga_perkg.indexOf(position)));
////                oper.putString("Alamat", String.valueOf(Alamat.indexOf(position)));
////                oper.putString("Kontak", String.valueOf(Kontak.indexOf(position)));
////                oper.putString("Kontak", String.valueOf(uid.indexOf(position)));
                jemputFragmentDetail.setArguments(oper);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_sellreplace, jemputFragmentDetail).addToBackStack(jemputFragmentDetail.TAG).commit();
            }
        });
    }

    public void showListView(){

        db = FirebaseFirestore.getInstance();
        db.collection("users_detail")
                .whereEqualTo("hakAkses","super_user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Jemput> jemputList = new ArrayList<>();
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String cekcik = document.getId();
                                Log.d(TAG, "Ecek ID " + document.getData());
                                Jemput jemput = document.toObject(Jemput.class);
                                jemputList.add(jemput);
                            }
                            AdapterSellJemput adapterSellJemput = new AdapterSellJemput(getActivity(), jemputList);
                            listView.setAdapter(adapterSellJemput);
                        }else{
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

//        AdapterSellJemput adapterSellJemput = new AdapterSellJemput(getActivity(), list_nama,deskripsi,harga_perkg, Alamat, Kontak);
//        listView.setAdapter(adapterSellJemput);
    }
}
