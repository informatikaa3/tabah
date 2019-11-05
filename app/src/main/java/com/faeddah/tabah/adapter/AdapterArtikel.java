package com.faeddah.tabah.adapter;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.R;
import com.faeddah.tabah.model.Artikel;
import com.faeddah.tabah.ui.Home.ArtikelDetail;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;



public class AdapterArtikel extends FirestoreRecyclerAdapter<Artikel, AdapterArtikel.ArtikelViewHolder> {


    private long teakhirklik = 0 ;

    public AdapterArtikel(@Nullable FirestoreRecyclerOptions<Artikel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ArtikelViewHolder holder, int position, @NonNull final Artikel model) {
        holder.judul.setText(model.getJudul());
        holder.artikel.setText(model.getArtikel());

        Glide.with(holder.itemView.getContext())
                .load(model.getImgUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgArtikel);

        //TODO : Pindah ke fragment detail artikel + ngoper data.
        holder.card.setOnClickListener(new View.OnClickListener() {

            private long terakhirklik = 0;
            private String judul = model.getJudul();
            private String artikel = model.getArtikel();
            private String imgurl = model.getImgUrl();

            @Override
            public void onClick(View v) {
                // fix klik buburudulan
                if (SystemClock.elapsedRealtime() - terakhirklik < 1000) {
                    return;
                }
                terakhirklik = SystemClock.elapsedRealtime();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle oper = new Bundle();
                oper.putString("judul", judul);
                oper.putString("artikel", artikel);
                oper.putString("imgurl", imgurl);

                ArtikelDetail artikelDetail = new ArtikelDetail();

                //jeder
                artikelDetail.setArguments(oper);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.ke_detail_artikel, artikelDetail).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ArtikelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_artikel_feed, parent, false);

        return new ArtikelViewHolder(view);
    }


    public class ArtikelViewHolder extends RecyclerView.ViewHolder{

        TextView judul, artikel, jdl2;
        CardView card;
        ImageView imgArtikel;

        public ArtikelViewHolder(@NonNull View itemView) {
            super(itemView);
            card =  itemView.findViewById(R.id.card_item);
            imgArtikel = itemView.findViewById(R.id.img_artikel);
            judul = itemView.findViewById(R.id.tv_jdlArtikel);
            artikel = itemView.findViewById(R.id.tv_artikel);
        }
    }

}
