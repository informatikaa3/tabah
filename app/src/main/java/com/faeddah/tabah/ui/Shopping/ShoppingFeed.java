package com.faeddah.tabah.ui.Shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.adapter.AdapterShopping;
import com.faeddah.tabah.helper.ConnectivityHelper;
import com.faeddah.tabah.model.Shopping;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShoppingFeed extends BaseFragment {

    private RecyclerView rv;
    private FirebaseFirestore db;
    private CollectionReference reference;
    private AdapterShopping adapter;
    private Query query;
    private TextView tvNotif;
    private View view;

    public ShoppingFeed() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);


        showRecyclerView();
        findViews(view);
        initViews(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (ConnectivityHelper.isConnectedToNetwork(getContext())){
            Snackbar.make(getView(), "Mengambil data Shopping ....", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
        FirestoreRecyclerOptions<Shopping> options = new FirestoreRecyclerOptions
                .Builder<Shopping>()
                .setQuery(query, Shopping.class)
                .build();
        // set adapter recycler view
        adapter = new AdapterShopping(options);
    }
}