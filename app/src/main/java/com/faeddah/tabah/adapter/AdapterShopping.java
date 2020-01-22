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
import com.google.type.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterShopping extends FirestoreRecyclerAdapter<Shopping, AdapterShopping.SellViewHolder> {

    private String judulBarang, deskripsiBarang, uidOwner,uidBarang, imgUrl;
//    private int hargaBarang, stokBarang;
//    private Date tanggalPosting;

    public AdapterShopping(@Nullable FirestoreRecyclerOptions<Shopping> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final SellViewHolder holder, int position, @NonNull final Shopping model) {
//        hargaBarang = String.valueOf(hargaBarang);
//        stokBarang = stokBarang.toString();
////        tanggalPosting = tanggalPosting;


        judulBarang = model.getJudulBarang();
        deskripsiBarang = model.getDeskripsiBarang();
        imgUrl = model.getImgUrl();
        uidBarang = model.getUidBarang();
        uidOwner = model.getUidOwner();


        holder.judul_barang.setText(judulBarang);
//        holder.deskripsi_barang.setText(deskripsiBarang);
        holder.hargaBarang.setText("sgadajskhdghdjasgdh");
        Glide.with(holder.itemView.getContext())
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().centerCrop())
                .into(holder.img_barang);

        Bundle data = new Bundle();
        data.putString("judul_barang", judulBarang);
        data.putString("deskripsi_barang", deskripsiBarang);
        data.putString("harga_barang", "");
        data.putString("img_sell", imgUrl);

        holder.card.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_shopping_detail,data));

//        holder.card.setOnClickListener(new View.OnClickListener() {
//
//            private long terakhirklik = 0;
//            private String judul_barang = model.getJudul_barang();
//            private String deskripsi_barang = model.getDeskripsi_barang();
//            private String harga_barang = String.valueOf(model.getHarga_barang());
//            private String imgurl_sell = model.getImgUrl();
//
//            @Override
//            public void onClick(View v) {
//                // fix klik buburudulan
//                if (SystemClock.elapsedRealtime() - terakhirklik < 1000) {
//                    return;
//                }
//                terakhirklik = SystemClock.elapsedRealtime();
//
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                Toast.makeText(activity.getApplicationContext(),model.getJudul_barang(), Toast.LENGTH_SHORT).show();
//
//
//                Bundle oper = new Bundle();
//                oper.putString("judul_barang", judul_barang);
//                oper.putString("deskripsi_barang", deskripsi_barang);
//                oper.putString("harga_barang", harga_barang);
//                oper.putString("img_sell", imgurl_sell);
//
//                ShoppingDetail sellDetail = new ShoppingDetail();
//                sellDetail.setArguments(oper);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.feed_shopping, sellDetail).addToBackStack(ShoppingFeed.TAG).commit();
//            }
//        });

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
//            deskripsi_barang = itemView.findViewById(R.id.tv_sell_detail);
            hargaBarang = itemView.findViewById(R.id.tv_hargaBarang);
        }
    }
}
