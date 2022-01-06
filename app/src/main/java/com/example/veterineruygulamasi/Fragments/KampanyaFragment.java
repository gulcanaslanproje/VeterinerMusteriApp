package com.example.veterineruygulamasi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.veterineruygulamasi.Adapters.KampanyaAdapter;
import com.example.veterineruygulamasi.Models.KampanyaModel;
import com.example.veterineruygulamasi.R;
import com.example.veterineruygulamasi.RestApi.ManagerAll;
import com.example.veterineruygulamasi.Utils.ChangeFragments;
import com.example.veterineruygulamasi.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampanyaFragment extends Fragment {

    View view;
    RecyclerView kampanyaRecyclerView;
    private ChangeFragments changeFragments;
    private KampanyaAdapter kampanyaAdapter;
    private  List<KampanyaModel> kampanyaList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kampanya, container, false);
        tanimla();
        getKampanya();
        return view;
    }

    private void tanimla() {
        kampanyaRecyclerView = (RecyclerView) view.findViewById(R.id.kampanyaRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        kampanyaRecyclerView.setLayoutManager(layoutManager);
        changeFragments = new ChangeFragments(getContext());
        kampanyaList =new ArrayList<>();
    }

    private void getKampanya()
    {
        Call<List<KampanyaModel>> req = ManagerAll.getInstance().getKampanya();

        req.enqueue(new Callback<List<KampanyaModel>>() {
            @Override
            public void onResponse(Call<List<KampanyaModel>> call, Response<List<KampanyaModel>> response) {
                if(response.body().get(0).isTf())
                {
                    if(response.isSuccessful())
                    {
                        kampanyaList =response.body();
                        kampanyaAdapter = new KampanyaAdapter(kampanyaList,getContext());
                        kampanyaRecyclerView.setAdapter(kampanyaAdapter);
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Kampanya yok...",Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<KampanyaModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText , Toast.LENGTH_LONG).show();

            }
        });

    }
}
