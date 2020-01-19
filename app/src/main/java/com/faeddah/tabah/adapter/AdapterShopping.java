package com.faeddah.tabah.adapter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.R;
import com.faeddah.tabah.model.Shopping;
import com.faeddah.tabah.ui.Shopping.ShoppingDetail;
import com.faeddah.tabah.ui.Shopping.ShoppingFeed;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterShopping extends FirestoreRecyclerAdapter<Shopping, AdapterShopping.SellViewHolder> {

    public AdapterShopping(@Nullable FirestoreRecyclerOptions<Shopping> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final SellViewHolder holder, int position, @NonNull final Shopping model) {
        holder.judul_barang.setText(model.getJudul_barang());
        holder.deskripsi_barang.setText(model.getDeskripsi_barang());
        holder.hargaBarang.setText("Rp "+String.valueOf(model.getHarga_barang()));
        Glide.with(holder.itemView.getContext())
                .load(model.getImgUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().centerCrop())
                .into(holder.img_barang);

        holder.card.setOnClickListener(new View.OnClickListener() {

            private long terakhirklik = 0;
            private String judul_barang = model.getJudul_barang();
            private String deskripsi_barang = model.getDeskripsi_barang();
            private String harga_barang = String.valueOf(model.getHarga_barang());
            private String imgurl_sell = model.getImgUrl();

            @Override
            public void onClick(View v) {
                // fix klik buburudulan
                if (SystemClock.elapsedRealtime() - terakhirklik < 1000) {
                    return;
                }
                terakhirklik = SystemClock.elapsedRealtime();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Toast.makeText(activity.getApplicationContext(),model.getJudul_barang(), Toast.LENGTH_SHORT).show();


                Bundle oper = new Bundle();
                oper.putString("judul_barang", judul_barang);
                oper.putString("deskripsi_barang", deskripsi_barang);
                oper.putString("harga_barang", harga_barang);
                oper.putString("img_sell", imgurl_sell);

                ShoppingDetail sellDetail = new ShoppingDetail();
                sellDetail.setArguments(oper);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.feed_shopping, sellDetail).addToBackStack(ShoppingFeed.TAG).commit();
            }
        });

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
            judul_barang = itemView.findViewById(R.id.tv_sell_title);
            deskripsi_barang = itemView.findViewById(R.id.tv_sell_detail);
            hargaBarang = itemView.findViewById(R.id.tv_sell_price);
        }
    }
}
