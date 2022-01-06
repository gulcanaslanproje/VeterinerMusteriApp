package com.example.veterineruygulamasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineruygulamasi.Fragments.AsiDetayFragment;
import com.example.veterineruygulamasi.Models.AsiModel;
import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.Utils.ChangeFragments;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarneGecmisAdapter extends RecyclerView.Adapter<SanalKarneGecmisAdapter.ViewHolder>{

    List<AsiModel> list;
    Context context;

    public SanalKarneGecmisAdapter(List<AsiModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.sanal_karne_gecmis_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.sanalKarneGecmisAsiIsmi.setText(list.get(position).getAsiisim().toString() + " Aşısı Yapılmıştır.");
        holder.sanalKarneGecmisAsiBilgiText.setText(list.get(position).getPetisim().toString() + " isimli  petinize " +
                list.get(position).getAsitarih().toString() +" tarihinde "+ list.get(position).getAsiisim().toString() +" aşısı yapılmıştır");
        Picasso.get().load(list.get(position).getPetresim().toString()).into(holder.sanalKarneGecmisAsiImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView sanalKarneGecmisAsiIsmi,sanalKarneGecmisAsiBilgiText;
        CircleImageView sanalKarneGecmisAsiImage;

        public ViewHolder(View itemView) {
            super(itemView);
            sanalKarneGecmisAsiIsmi = (TextView)itemView.findViewById(R.id.sanalKarneGecmisAsiIsmi);
            sanalKarneGecmisAsiBilgiText =(TextView)itemView.findViewById(R.id.sanalKarneGecmisAsiBilgiText);
            sanalKarneGecmisAsiImage =(CircleImageView)itemView.findViewById(R.id.sanalKarneGecmisAsiImage);
        }
    }
}
