package com.faeddah.tabah.adapter;

import android.os.SystemClock;
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
import androidx.recyclerview.widget.RecyclerView;

public class AdapterShopping extends FirestoreRecyclerAdapter<Shopping, AdapterShopping.SellViewHolder> {

    private long teakhirklik = 0 ;

    public AdapterShopping(@Nullable FirestoreRecyclerOptions<Shopping> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final SellViewHolder holder, int position, @NonNull final Shopping model) {
        holder.judul_barang.setText(model.getJudul_barang());
        holder.deskripsi_barang.setText(model.getDeskripsi_barang());
        holder.hargaBarang.setText("99999");
//        holder.hargaBarang.setText((int) model.getHarga_barang());

//        holder.hargaBarang.setText(model.getHargaBarang());

        Glide.with(holder.itemView.getContext())
                .load(model.getImgUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().centerCrop())
                .into(holder.img_barang);

        //TODO : Pindah ke fragment detail artikel + ngoper data.
        holder.card.setOnClickListener(new View.OnClickListener() {

            private long terakhirklik = 0;
            private String judul_barang = model.getJudul_barang();
            private String deskripsi_barang = model.getDeskripsi_barang();
//            private String harga_barang = String.valueOf(model.getHargaBarang());
            private String imgurl_sell = model.getImgUrl();

            @Override
            public void onClick(View v) {
                // fix klik buburudulan
                if (SystemClock.elapsedRealtime() - terakhirklik < 1000) {
                    return;
                }
                terakhirklik = SystemClock.elapsedRealtime();

//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                Bundle oper = new Bundle();
//                oper.putString("judul_barang", judul_barang);
//                oper.putString("deskripsi_barang", deskripsi_barang);
//                oper.putString("harga_barang", harga_barang);
//                oper.putString("img_sell", imgurl_sell);
//
//                ShoppingDetail sellDetail = new ShoppingDetail();
//
//                //jeder
//                sellDetail.setArguments(oper);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.ke_detail_sell, sellDetail).addToBackStack(null).commit();
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
