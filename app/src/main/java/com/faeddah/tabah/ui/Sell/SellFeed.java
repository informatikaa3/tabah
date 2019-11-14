package com.faeddah.tabah.ui.Sell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.adapter.AdapterSell;
import com.faeddah.tabah.helper.ConnectivityHelper;
import com.faeddah.tabah.model.Sell;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SellFeed extends BaseFragment {

    private RecyclerView rv;
    private FirebaseFirestore db;
    private CollectionReference reference;
    private AdapterSell adapter;
    private Query query;
    private TextView tvNotif;
    private View view;

    public SellFeed() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, container, false);


        showRecyclerView();
        findViews(view);
        initViews(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (ConnectivityHelper.isConnectedToNetwork(getContext())){
            Snackbar.make(getView(), "Mengambil data Sell ....", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            adapter.startListening();
        } else {
            Snackbar.make(getView(), "Tidak Ada Koneksi Internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void findViews(View view) {
        rv = view.findViewById(R.id.rv_sell);

    }

    @Override
    public void initViews(View view) {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(),2));
        rv.setAdapter(adapter);

    }

    @Override
    public void initListeners(View view) {

    }


    private void showRecyclerView() {
        //ambil data di firebase
        db = FirebaseFirestore.getInstance();
        reference = db.collection("item_jualbarang");
        query = reference.orderBy("tanggal_input", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Sell> options = new FirestoreRecyclerOptions
                .Builder<Sell>()
                .setQuery(query, Sell.class)
                .build();
        // set adapter recycler view
        adapter = new AdapterSell(options);
    }
}