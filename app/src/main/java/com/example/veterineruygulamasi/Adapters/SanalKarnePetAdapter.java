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
import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.Utils.ChangeFragments;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarnePetAdapter extends RecyclerView.Adapter<SanalKarnePetAdapter.ViewHolder>{

    List<PetModel> list;
    Context context;

    public SanalKarnePetAdapter(List<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.sanal_karne_pet_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.sanalkarnepettext.setText(list.get(position).getPetisim().toString());
        holder.sanalKarneBilgiText.setText(list.get(position).getPetisim().toString() + " isismli " +
                list.get(position).getPettur().toString() +" türüne "+ list.get(position).getPetcins().toString() +" cinsine ait petlerinizin" +
                "geçmiş aşılarını görmek için tıklayınız..."   );
        Picasso.get().load(list.get(position).getPetresim().toString()).into(holder.sanaKarneImage);

        holder.sanaKarneCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeFragments changeFragments = new ChangeFragments(context);
                changeFragments.changeWithParameters(new AsiDetayFragment(), list.get(position).getPetid().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView sanalkarnepettext,sanalKarneBilgiText;
        CircleImageView sanaKarneImage;
        CardView sanaKarneCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            sanalkarnepettext = (TextView)itemView.findViewById(R.id.sanalkarnepettext);
            sanalKarneBilgiText =(TextView)itemView.findViewById(R.id.sanalKarneBilgiText);
            sanaKarneImage =(CircleImageView)itemView.findViewById(R.id.sanaKarneImage);
            sanaKarneCardView =(CardView)itemView.findViewById(R.id.sanaKarneCardView);
        }
    }
}
