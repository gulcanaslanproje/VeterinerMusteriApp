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
import com.example.veterineruygulamasi.Models.PetModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    List<AnswersModel> list;
    Context context;

    public AnswersAdapter(List<AnswersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cevap_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.soruCevapText.setText("Soru : " + list.get(position).getSoru().toString());
        holder.cevapCevapText.setText("Cevap : " + list.get(position).getCevap().toString());
        holder.cevapSilbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteToServis(list.get(position).getCevapid().toString(), list.get(position).getSoruid().toString(),position);
            }
        });
    }

    private void deleteToServis(String cevapid, String soruid, final int position) {
        Call<DeleteAnswerModel> req = ManagerAll.getInstance().deleteAnswer(cevapid, soruid);
        req.enqueue(new Callback<DeleteAnswerModel>() {
            @Override
            public void onResponse(Call<DeleteAnswerModel> call, Response<DeleteAnswerModel> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(context,response.body().getText(), Toast.LENGTH_LONG).show();
                    deleteToList(position);
                }
                else
                {
                    Toast.makeText(context,response.body().getText(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<DeleteAnswerModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText , Toast.LENGTH_LONG).show();

            }
        });
    }

    public  void deleteToList(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView soruCevapText, cevapCevapText;
        Button cevapSilbutton;

        public ViewHolder(View itemView) {
            super(itemView);
            soruCevapText = (TextView) itemView.findViewById(R.id.soruCevapText);
            cevapCevapText = (TextView) itemView.findViewById(R.id.cevapCevapText);
            cevapSilbutton = (Button) itemView.findViewById(R.id.cevapSilbutton);
        }
    }
}
