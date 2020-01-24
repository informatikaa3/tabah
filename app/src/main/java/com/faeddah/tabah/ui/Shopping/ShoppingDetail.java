package com.faeddah.tabah.ui.Shopping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ShoppingDetail extends BaseFragment {

    private String judul_barang,deskripsi_barang,harga_barang,imgUrl, uidOwner, namaOwner, imgUrlOwner, tanggalPosting;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private TextView tv_jdlsell, tv_deskripsi, tv_price, tvOwnername, tvTglPosting;
    private ImageView imgview, imgOwnernya;
    private static final String namaKoleksi = "users_detail";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping_detail,container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        findViews(view);
        initViews(view);
        initListeners(view);

        return view;
    }

    @Override
    public void onStart() {
        auth.addAuthStateListener(stateListener);
        super.onStart();
    }

    @Override
    public void onStop() {
        auth.addAuthStateListener(stateListener);
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
            stateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (user != null) {
                        db.collection(namaKoleksi)
                                .whereEqualTo("uid", uidOwner)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                imgUrlOwner = document.get("imgUrl").toString();
                                                namaOwner = document.get("nama").toString();
                                            }
                                            if (isAdded()){
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
                    }
                }
            };
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