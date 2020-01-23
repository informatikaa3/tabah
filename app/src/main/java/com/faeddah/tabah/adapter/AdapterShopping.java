package com.faeddah.tabah.adapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.R;
import com.faeddah.tabah.model.Shopping;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdapterShopping extends FirestoreRecyclerAdapter<Shopping, AdapterShopping.SellViewHolder> {

    private String judulBarang, deskripsiBarang, uidOwner,uidBarang, imgUrl, hargaBarang, stokBarang, tanggalPosting;
    private DateFormat dateFormat;

    public AdapterShopping(@Nullable FirestoreRecyclerOptions<Shopping> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final SellViewHolder holder, int position, @NonNull final Shopping model) {
        hargaBarang = String.valueOf(model.getHargaBarang());
        stokBarang = String.valueOf(model.getStokBarang());
        dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        tanggalPosting = dateFormat.format(model.getTanggalPosting());
        uidOwner = model.getUidOwner();
        judulBarang = model.getJudulBarang();
        deskripsiBarang = model.getDeskripsiBarang();
        imgUrl = model.getImgUrl();
        uidBarang = model.getUidBarang();
        uidOwner = model.getUidOwner();

        holder.judul_barang.setText(judulBarang);
        holder.hargaBarang.setText("Rp. "+hargaBarang);
        Glide.with(holder.itemView.getContext())
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().centerCrop())
                .into(holder.img_barang);

        Bundle data = new Bundle();
        data.putString("judul_barang", judulBarang);
        data.putString("deskripsi_barang", deskripsiBarang);
        data.putString("harga_barang", hargaBarang);
        data.putString("img_url", imgUrl);
        data.putString("uid_owner",uidOwner);
        data.putString("tanggal_posting", tanggalPosting);



        holder.card.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_shopping_detail,data));

    }

    @NonNull
    @Override
    public SellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_shopping_feed, parent, false);

        return new SellViewHolder(view);
    }


    public class SellViewHolder extends RecyclerView.ViewHolder{

        TextView judul_barang, deskripsi_barang, hargaBarang;
        CardView card;
        ImageView img_barang;

        public SellViewHolder(@NonNull View itemView) {
            super(itemView);
            card =  itemView.findViewById(R.id.card_item_sell);
            img_barang = itemView.findViewById(R.id.img_sell);
            judul_barang = itemView.findViewById(R.id.tv_jdlBarang);
            hargaBarang = itemView.findViewById(R.id.tv_hargaBarang);
        }
    }

}
