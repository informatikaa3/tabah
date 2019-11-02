package com.faeddah.tabah.ui.About;
import com.faeddah.tabah.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdapterListDev extends RecyclerView.Adapter<AdapterListDev.ListViewHolder> {


    private ArrayList<Dev> listDev;
    public AdapterListDev(ArrayList<Dev> list){
        this.listDev = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_item_about, viewGroup, false);
        return new ListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Dev developer = listDev.get(position);
        Glide.with(holder.itemView.getContext())
                .load(developer.getFoto())
                .apply(new RequestOptions().override(55,55))
                .into(holder.imgFoto);
        holder.tvNama.setText(developer.getName());
        holder.tvDetail.setText(developer.getDetail());

    }

    @Override
    public int getItemCount() {
        return listDev.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFoto;
        TextView tvNama, tvDetail;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.img_dev_photo);
            tvNama = itemView.findViewById(R.id.tv_dev_name);
            tvDetail = itemView.findViewById(R.id.tv_dev_detail);


        }
    }
}

