package com.faeddah.tabah.ui.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.adapter.AdapterArtikel;
import com.faeddah.tabah.helper.ConnectivityHelper;
import com.faeddah.tabah.model.Artikel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ArtikelFeed extends BaseFragment {

    private RecyclerView rv;
    private FirebaseFirestore db;
    private CollectionReference reference;
    private AdapterArtikel adapter;
    private Query query;
    private TextView tvNotif;
    private View view;

    public ArtikelFeed() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artikel, container, false);
        showRecyclerView();
        findViews(view);
        initViews(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (ConnectivityHelper.isConnectedToNetwork(getContext())){
//            Snackbar.make(getView(), "Mengambil data artikel ....", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
        rv = view.findViewById(R.id.rv_artikel);

    }

    @Override
    public void initViews(View view) {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

    }

    @Override
    public void initListeners(View view) {

    }


    private void showRecyclerView() {
        //ambil data di firebase
        db = FirebaseFirestore.getInstance();
        reference = db.collection("artikel");
        query = reference.orderBy("terbit", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Artikel> options = new FirestoreRecyclerOptions
                .Builder<Artikel>()
                .setQuery(query, Artikel.class)
                .build();
        // set adapter recycler view
        adapter = new AdapterArtikel(options);
    }

}
