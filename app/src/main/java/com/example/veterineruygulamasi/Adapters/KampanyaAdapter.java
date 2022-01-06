package com.example.veterineruygulamasi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineruygulamasi.Models.AnswersModel;
import com.example.veterineruygulamasi.Models.DeleteAnswerModel;
import com.example.veterineruygulamasi.Models.KampanyaModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.ViewHolder> {

    List<KampanyaModel> list;
    Context context;

    public KampanyaAdapter(List<KampanyaModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kampanya_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.kampanyaLayoutBaslik.setText( list.get(position).getBaslik().toString());
        holder.kampanyaLayoutText.setText(list.get(position).getText().toString());
        Picasso.get().load(list.get(position).getResim().toString()).into(holder.kampanyaLayoutpetimage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kampanyaLayoutBaslik,kampanyaLayoutText;
        CircleImageView kampanyaLayoutpetimage;

        public ViewHolder(View itemView) {
            super(itemView);
            kampanyaLayoutBaslik = (TextView)itemView.findViewById(R.id.kampanyaLayoutBaslik);
            kampanyaLayoutText =(TextView)itemView.findViewById(R.id.kampanyaLayoutText);
            kampanyaLayoutpetimage =(CircleImageView)itemView.findViewById(R.id.kampanyaLayoutpetimage);

        }
    }
}
