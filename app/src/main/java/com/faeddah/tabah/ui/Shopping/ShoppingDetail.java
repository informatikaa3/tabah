package com.faeddah.tabah.ui.Shopping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.adapter.AdapterShopping;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShoppingDetail extends BaseFragment {

    private String judul_barang,deskripsi_barang,harga_barang,imgUrl, uidOwner, namaOwner, imgUrlOwner, tanggalPosting;
    private TextView tv_jdlsell, tv_deskripsi, tv_price, tvOwnername, tvTglPosting;
    private ImageView imgview, imgOwnernya;
    private FirebaseFirestore db;

    public ShoppingDetail() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping_detail,container, false);
        db = FirebaseFirestore.getInstance();

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
        tv_deskripsi = view.findViewById(R.id.tv_inisell_detail);
        tv_price = view.findViewById(R.id.tv_inisell_price);
        imgview = view.findViewById(R.id.img_sell_detail);
        imgOwnernya = view.findViewById(R.id.img_owner);
        tvOwnername = view.findViewById(R.id.tv_ownername);
        tvTglPosting = view.findViewById(R.id.tv_tanggalposting);
    }

    @Override
    public void initViews(View view) {
        Bundle arg = getArguments();
        if (!arg.isEmpty()){
            judul_barang = arg.getString("judul_barang");
            deskripsi_barang = arg.getString("deskripsi_barang");
            harga_barang = arg.getString("harga_barang");
            imgUrl = arg.getString("img_url");
            tanggalPosting = arg.getString("tanggal_posting");
            uidOwner = arg.getString("uid_owner");
            db.collection("users_detail")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    imgUrlOwner = document.get("imgUrl").toString();
                                    namaOwner = document.get("nama").toString();
                                    tvOwnername.setText(namaOwner);
                                    tvTglPosting.setText(tanggalPosting);
                                    Glide.with(getView())
                                            .load(imgUrlOwner)
                                            .onlyRetrieveFromCache(true)
                                            .into(imgOwnernya);
                                }
                            } else {
                                Log.d("Shooping detail :  ", "Error getting documents: ", task.getException());
                            }
                        }
                    });

            tv_jdlsell.setText(judul_barang);
            tv_deskripsi.setText(deskripsi_barang);
            tv_price.setText("Rp "+harga_barang);
            Glide.with(view)
                    .load(imgUrl)
                    .onlyRetrieveFromCache(true)
                    .into(imgview);


        }
    }

    @Override
    public void initListeners(View view) {

    }

}