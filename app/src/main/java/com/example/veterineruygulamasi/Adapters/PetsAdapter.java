package com.example.veterineruygulamasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder>{

    List<PetModel> list;
    Context context;

    public PetsAdapter(List<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.pet_list_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.petlerimLayoutcinsname.setText("Pet Cins = "+list.get(position).getPetcins().toString());
        holder.petlerimLayoutpetname.setText("Pet İsim = "+list.get(position).getPetisim().toString());
        holder.petlerimLayoutturname.setText("Pet Tür = "+list.get(position).getPettur().toString());
        Picasso.get().load(list.get(position).getPetresim().toString()).into(holder.petlerimLayoutpetimage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView petlerimLayoutpetname,petlerimLayoutcinsname,petlerimLayoutturname;
        CircleImageView petlerimLayoutpetimage;

        public ViewHolder(View itemView) {
            super(itemView);
             petlerimLayoutpetname = (TextView)itemView.findViewById(R.id.petlerimLayoutpetname);
             petlerimLayoutcinsname =(TextView)itemView.findViewById(R.id.petlerimLayoutcinsname);
             petlerimLayoutturname =(TextView)itemView.findViewById(R.id.petlerimLayoutturname);
             petlerimLayoutpetimage =(CircleImageView)itemView.findViewById(R.id.petlerimLayoutpetimage);

        }
    }
}
