package com.faeddah.tabah.ui.Shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.adapter.AdapterShopping;
import com.faeddah.tabah.helper.ConnectivityHelper;
import com.faeddah.tabah.model.Shopping;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShoppingFeed extends BaseFragment {
    public static final String TAG = "root_shopping";
    private RecyclerView rv;
    private FirebaseFirestore db;
    private CollectionReference reference;
    private AdapterShopping adapter;
    private Query query;
    private FloatingActionButton fab_additem, fab_cart , fab_expand;
    private Animation bukafab, tutupfab, putarfab, putarbalikfab;
    private TextView tvNotif;
    private View view;

    private boolean kebuka = false;

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
        initListeners(view);
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
        rv = view.findViewById(R.id.rv_shopping);
        fab_cart = view.findViewById(R.id.fab_cart);
        fab_additem = view.findViewById(R.id.fab_add_cart);
        fab_expand = view.findViewById(R.id.fab_expand);
        bukafab = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        tutupfab = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        putarfab = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_rotate_180);
        putarbalikfab = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_rotate_0);


    }

    @Override
    public void initViews(View view) {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(),2));
        rv.setAdapter(adapter);



    }

    @Override
    public void initListeners(View view) {

        fab_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kebuka) {
                    fab_cart.startAnimation(tutupfab);
                    fab_additem.startAnimation(tutupfab);
                    fab_expand.startAnimation(putarbalikfab);
                    fab_cart.setClickable(false);
                    fab_additem.setClickable(false);
                    kebuka = false;
                } else{
                    fab_cart.startAnimation(bukafab);
                    fab_additem.startAnimation(bukafab);
                    fab_expand.startAnimation(putarfab);
                    fab_cart.setClickable(true);
                    fab_additem.setClickable(true);
                    kebuka = true;
                }
            }
        });

        fab_additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : view add item, backend update data firebase
                Toast.makeText(getContext(), "ke tambah item", Toast.LENGTH_SHORT).show();
            }
        });

        fab_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : view keranjang, ambil data di firebase
                Toast.makeText(getContext(), "ke keranjang", Toast.LENGTH_SHORT).show();
            }
        });

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