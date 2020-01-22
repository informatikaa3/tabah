package com.faeddah.tabah.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavArgs;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.R;
import com.faeddah.tabah.model.Artikel;
import com.faeddah.tabah.ui.Home.ArtikelDetail;
import com.faeddah.tabah.ui.Home.ArtikelFeed;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation;


public class AdapterArtikel extends FirestoreRecyclerAdapter<Artikel, AdapterArtikel.ArtikelViewHolder> {


    private long teakhirklik = 0 ;
    private String juduls, artikels, imgurls;

    public AdapterArtikel(@Nullable FirestoreRecyclerOptions<Artikel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ArtikelViewHolder holder, int position, @NonNull final Artikel model) {
        juduls = model.getJudul();
        artikels = model.getArtikel();
        imgurls = model.getImgUrl();

        holder.judul.setText(juduls);
        holder.artikel.setText(artikels);

        Glide.with(holder.itemView.getContext())
                .load(imgurls)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgArtikel);

        Bundle oper = new Bundle();
        oper.putString("judul", juduls);
        oper.putString("artikel", artikels);
        oper.putString("imgurl", imgurls);
        holder.card.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_home_artikel_detail,oper));
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

        public ArtikelViewHolder(@NonNull final View itemView) {
            super(itemView);
            card =  itemView.findViewById(R.id.card_item);
            imgArtikel = itemView.findViewById(R.id.img_artikel);
            judul = itemView.findViewById(R.id.tv_jdlArtikel);
            artikel = itemView.findViewById(R.id.tv_artikel);
        }



    }

}
